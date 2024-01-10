package ru.savinov.pizzaservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import ru.savinov.pizzaservice.audit.listener.entities.AccessType;
import ru.savinov.pizzaservice.audit.listener.entities.EntityEvent;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.exceptions.UserExistException;
import ru.savinov.pizzaservice.mapper.UserCreateEditDtoMapper;
import ru.savinov.pizzaservice.mapper.UserReadMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;
import ru.savinov.test_helpers.factories.UserCreateEditDtoFactory;
import ru.savinov.test_helpers.factories.UserDtoFactory;
import ru.savinov.test_helpers.factories.UserFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private UserCreateEditDtoMapper userCreateEditDtoMapper;
    @Mock
    private ApplicationEventPublisher publisher;

    private UserService subject;

    @BeforeEach
    void setUp() {
        subject = new UserService(userRepo, publisher, userReadMapper, userCreateEditDtoMapper);
    }

    @Test
    void findAll() {
        List<User> users = UserFactory.ofUsers();
        List<UserReadDto> userReadDtoList = UserDtoFactory.userReadDtoList();
        Map<User, UserReadDto> userToUserReadDto = IntStream.range(0, users.size())
                .boxed()
                .collect(Collectors.toMap(users::get, userReadDtoList::get));

        when(userRepo.findAll()).thenReturn(users);
        userToUserReadDto.forEach((key, value) -> when(userReadMapper.map(key)).thenReturn(value));

        List<UserReadDto> userReadDtoListResult = subject.findAll();
        assertAll(
                () -> assertThat(userReadDtoListResult).hasSize(5),
                () -> assertThat(userReadDtoListResult).containsAll(userReadDtoList)
        );
    }

    @Test
    void findById() {
        User user = UserFactory.of();
        UserReadDto expected = UserDtoFactory.userReadDto();

        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(userReadMapper.map(user)).thenReturn(expected);

        Optional<UserReadDto> maybeUserReadDto = subject.findById(user.getId());
        assertTrue(maybeUserReadDto.isPresent());
        maybeUserReadDto.ifPresent(actualResult ->
                assertEquals(expected, actualResult, "userReadDto is not expected"));
    }

    @Test
    void findById__bad_Id() {
        Long badId = 111L;
        when(userRepo.findById(badId)).thenReturn(Optional.empty());
        Optional<UserReadDto> maybeUserReadDto = subject.findById(badId);
        assertTrue(maybeUserReadDto.isEmpty());
    }

    @Test
    void create() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.userCreateEditDto();
        User toSave = UserFactory.of(userDto);
        UserReadDto userReadDto = UserDtoFactory.userReadDto();

        User saved = UserFactory.of(userDto);
        saved.setId(1L);

        when(userCreateEditDtoMapper.map(userDto)).thenReturn(toSave);
        when(userRepo.save(toSave)).thenReturn(saved);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.empty());
        when(userReadMapper.map(saved)).thenReturn(userReadDto);

        UserReadDto actual = subject.create(userDto);
        assertAll(
                () -> assertThat(userReadDto).isEqualTo(actual),
                () -> verify(publisher, times(1))
                        .publishEvent(any(EntityEvent.class))
        );
        verify(userRepo, times(1)).save(toSave);
    }

    @Test
    void create__userExist() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.userCreateEditDto();
        User toSave = UserFactory.of(userDto);

        User saved = UserFactory.of(userDto);

        when(userCreateEditDtoMapper.map(userDto)).thenReturn(toSave);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.of(saved));

        assertAll(
                () -> {
                    var exception = assertThrows(UserExistException.class,
                            () -> subject.create(userDto));

                    String message = exception.getMessage();
                    assertThat(message).isEqualTo("A user with login '%s' already exists",
                            saved.getUsername());
                },
                () -> verify(publisher, times(1))
                        .publishEvent(any(EntityEvent.class))
        );
    }

    @Test
    void create__checkWorkingPublishEvent() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.userCreateEditDto();
        User toSave = UserFactory.of(userDto);
        UserReadDto userReadDto = UserDtoFactory.userReadDto();

        User saved = UserFactory.of(userDto);
        saved.setId(1L);

        when(userCreateEditDtoMapper.map(userDto)).thenReturn(toSave);
        when(userRepo.save(toSave)).thenReturn(saved);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.empty());
        when(userReadMapper.map(saved)).thenReturn(userReadDto);

        subject.create(userDto);

        verify(publisher, times(1))
                .publishEvent(any(EntityEvent.class));

    }

    @Test
    void create__checkArgumentsEntityEvent() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.userCreateEditDto();
        User toSave = UserFactory.of(userDto);
        UserReadDto userReadDto = UserDtoFactory.userReadDto();
        ArgumentCaptor<EntityEvent> eventCaptor = ArgumentCaptor.forClass(EntityEvent.class);

        User saved = UserFactory.of(userDto);
        saved.setId(1L);

        when(userCreateEditDtoMapper.map(userDto)).thenReturn(toSave);
        when(userRepo.save(toSave)).thenReturn(saved);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.empty());
        when(userReadMapper.map(saved)).thenReturn(userReadDto);

        subject.create(userDto);

        verify(publisher, times(1)).publishEvent(eventCaptor.capture());
        EntityEvent captured = eventCaptor.getValue();
        assertThat(captured.getAccessType()).isEqualTo(AccessType.CREATE);
        assertThat(captured.getSource()).isEqualTo(userDto);
    }

    @Test
    void update() {
        UserCreateEditDto fromDto = UserCreateEditDtoFactory.of();
        User toUser = UserFactory.of();
        User mapped = UserFactory.of(fromDto);
        UserReadDto userReadDto = UserDtoFactory.userReadDto(mapped);

        when(userRepo.findById(1L)).thenReturn(Optional.of(toUser));
        when(userCreateEditDtoMapper.map(fromDto, toUser)).thenReturn(mapped);
        when(userRepo.saveAndFlush(mapped)).thenReturn(mapped);
        when(userReadMapper.map(mapped)).thenReturn(userReadDto);

        Optional<UserReadDto> actual = subject.update(1L, fromDto);

        verify(userRepo, times(1)).findById(1L);
        verify(userCreateEditDtoMapper, times(1)).map(fromDto, toUser);
        verify(userRepo, times(1)).saveAndFlush(mapped);
        assertThat(actual).isPresent();
        assertThat(actual).isEqualTo(Optional.of(userReadDto));
        verify(userRepo, times(1)).saveAndFlush(mapped);
    }

    @Test
    void update__checkWorkingPublishEvent() {
        UserCreateEditDto fromDto = UserCreateEditDtoFactory.of();
        User toUser = UserFactory.of();
        User mapped = UserFactory.of(fromDto);
        UserReadDto userReadDto = UserDtoFactory.userReadDto(mapped);

        when(userRepo.findById(1L)).thenReturn(Optional.of(toUser));
        when(userCreateEditDtoMapper.map(fromDto, toUser)).thenReturn(mapped);
        when(userRepo.saveAndFlush(mapped)).thenReturn(mapped);
        when(userReadMapper.map(mapped)).thenReturn(userReadDto);

        subject.update(1L, fromDto);

        verify(publisher, times(1))
                .publishEvent(any(EntityEvent.class));
    }

    @Test
    void update__checkArgumentsEntityEvent() {
        UserCreateEditDto fromDto = UserCreateEditDtoFactory.of();
        User toUser = UserFactory.of();
        User mapped = UserFactory.of(fromDto);
        UserReadDto userReadDto = UserDtoFactory.userReadDto(mapped);
        ArgumentCaptor<EntityEvent> eventCaptor = ArgumentCaptor.forClass(EntityEvent.class);

        when(userRepo.findById(1L)).thenReturn(Optional.of(toUser));
        when(userCreateEditDtoMapper.map(fromDto, toUser)).thenReturn(mapped);
        when(userRepo.saveAndFlush(mapped)).thenReturn(mapped);
        when(userReadMapper.map(mapped)).thenReturn(userReadDto);

        subject.update(1L, fromDto);

        verify(publisher, times(1)).publishEvent(eventCaptor.capture());
        EntityEvent captured = eventCaptor.getValue();
        assertThat(captured.getAccessType()).isEqualTo(AccessType.UPDATE);
        assertThat(captured.getSource()).isEqualTo(fromDto);
    }

    @Test
    void delete() {
        User founded = UserFactory.of();

        when(userRepo.findById(1L)).thenReturn(Optional.of(founded));

        boolean result = subject.delete(1L);
        assertTrue(result);
        verify(userRepo).delete(founded);
        verify(userRepo, times(1)).flush();
    }

    @Test
    void delete__userNotExist() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        boolean result = subject.delete(1L);
        assertFalse(result);
        verify(userRepo, never()).flush();
    }

}