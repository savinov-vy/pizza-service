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
    private Long userId;
    private Long badUserId;
    private User founded;
    private UserCreateEditDto userCreateEditDto;
    private UserReadDto userReadDto;
    private User mapped;

    @BeforeEach
    void setUp() {
        subject = new UserService(userRepo, publisher, userReadMapper, userCreateEditDtoMapper);
        userId = 1L;
        badUserId = 111L;
        founded = UserFactory.of();
        userCreateEditDto = UserCreateEditDtoFactory.of();
        userReadDto = UserDtoFactory.of();
        mapped = UserFactory.of(userCreateEditDto);
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
        UserReadDto expected = UserDtoFactory.of(founded);

        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));
        when(userReadMapper.map(founded)).thenReturn(expected);

        Optional<UserReadDto> maybeUserReadDto = subject.findById(userId);
        assertTrue(maybeUserReadDto.isPresent());
        maybeUserReadDto.ifPresent(actualResult ->
                assertEquals(expected, actualResult, "userReadDto is not expected"));
    }

    @Test
    void findById__bad_Id() {
        when(userRepo.findById(badUserId)).thenReturn(Optional.empty());
        Optional<UserReadDto> maybeUserReadDto = subject.findById(badUserId);
        assertTrue(maybeUserReadDto.isEmpty());
    }

    @Test
    void create() {
        User toSave = UserFactory.of(userCreateEditDto);

        User saved = UserFactory.of(userCreateEditDto);
        saved.setId(userId);

        when(userCreateEditDtoMapper.map(userCreateEditDto)).thenReturn(toSave);
        when(userRepo.save(toSave)).thenReturn(saved);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.empty());
        when(userReadMapper.map(saved)).thenReturn(userReadDto);

        UserReadDto actual = subject.create(userCreateEditDto);
        assertAll(
                () -> assertThat(userReadDto).isEqualTo(actual),
                () -> verify(publisher, times(1))
                        .publishEvent(any(EntityEvent.class))
        );
        verify(userRepo, times(1)).save(toSave);
    }

    @Test
    void create__userExist() {
        User toSave = UserFactory.of(userCreateEditDto);
        User saved = UserFactory.of(userCreateEditDto);

        when(userCreateEditDtoMapper.map(userCreateEditDto)).thenReturn(toSave);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.of(saved));

        assertAll(
                () -> {
                    var exception = assertThrows(UserExistException.class,
                            () -> subject.create(userCreateEditDto));

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
        User toSave = UserFactory.of(userCreateEditDto);

        User saved = UserFactory.of(userCreateEditDto);
        saved.setId(userId);

        when(userCreateEditDtoMapper.map(userCreateEditDto)).thenReturn(toSave);
        when(userRepo.save(toSave)).thenReturn(saved);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.empty());
        when(userReadMapper.map(saved)).thenReturn(userReadDto);

        subject.create(userCreateEditDto);

        verify(publisher, times(1))
                .publishEvent(any(EntityEvent.class));
    }

    @Test
    void create__checkArgumentsEntityEvent() {
        User toSave = UserFactory.of(userCreateEditDto);
        ArgumentCaptor<EntityEvent> eventCaptor = ArgumentCaptor.forClass(EntityEvent.class);

        User saved = UserFactory.of(userCreateEditDto);
        saved.setId(userId);

        when(userCreateEditDtoMapper.map(userCreateEditDto)).thenReturn(toSave);
        when(userRepo.save(toSave)).thenReturn(saved);
        when(userRepo.findByUsername(toSave.getUsername())).thenReturn(Optional.empty());
        when(userReadMapper.map(saved)).thenReturn(userReadDto);

        subject.create(userCreateEditDto);

        verify(publisher, times(1)).publishEvent(eventCaptor.capture());
        EntityEvent captured = eventCaptor.getValue();
        assertThat(captured.getAccessType()).isEqualTo(AccessType.CREATE);
        assertThat(captured.getSource()).isEqualTo(userCreateEditDto);
    }

    @Test
    void update() {
        UserReadDto userReadDto = UserDtoFactory.of(mapped);

        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));
        when(userCreateEditDtoMapper.map(userCreateEditDto, founded)).thenReturn(mapped);
        when(userRepo.saveAndFlush(mapped)).thenReturn(mapped);
        when(userReadMapper.map(mapped)).thenReturn(userReadDto);

        Optional<UserReadDto> actual = subject.update(userId, userCreateEditDto);

        verify(userRepo, times(1)).findById(userId);
        verify(userCreateEditDtoMapper, times(1)).map(userCreateEditDto, founded);
        verify(userRepo, times(1)).saveAndFlush(mapped);
        assertThat(actual).isPresent();
        assertThat(actual).isEqualTo(Optional.of(userReadDto));
        verify(userRepo, times(1)).saveAndFlush(mapped);
    }

    @Test
    void update__checkWorkingPublishEvent() {
        UserReadDto userReadDto = UserDtoFactory.of(mapped);

        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));
        when(userCreateEditDtoMapper.map(userCreateEditDto, founded)).thenReturn(mapped);
        when(userRepo.saveAndFlush(mapped)).thenReturn(mapped);
        when(userReadMapper.map(mapped)).thenReturn(userReadDto);

        subject.update(userId, userCreateEditDto);

        verify(publisher, times(1))
                .publishEvent(any(EntityEvent.class));
    }

    @Test
    void update__checkArgumentsEntityEvent() {
        UserReadDto userReadDto = UserDtoFactory.of(mapped);
        ArgumentCaptor<EntityEvent> eventCaptor = ArgumentCaptor.forClass(EntityEvent.class);

        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));
        when(userCreateEditDtoMapper.map(userCreateEditDto, founded)).thenReturn(mapped);
        when(userRepo.saveAndFlush(mapped)).thenReturn(mapped);
        when(userReadMapper.map(mapped)).thenReturn(userReadDto);

        subject.update(userId, userCreateEditDto);

        verify(publisher, times(1)).publishEvent(eventCaptor.capture());
        EntityEvent captured = eventCaptor.getValue();
        assertThat(captured.getAccessType()).isEqualTo(AccessType.UPDATE);
        assertThat(captured.getSource()).isEqualTo(userCreateEditDto);
    }

    @Test
    void delete() {
        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));

        boolean result = subject.delete(userId);
        assertTrue(result);
        verify(userRepo).delete(founded);
        verify(userRepo, times(1)).flush();
    }

    @Test
    void delete__userNotExist() {
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        boolean result = subject.delete(userId);
        assertFalse(result);
        verify(userRepo, never()).flush();
    }

    @Test
    void delete__checkWorkingPublishEvent() {
        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));
        subject.delete(userId);
        verify(publisher, times(1))
                .publishEvent(any(EntityEvent.class));
    }

    @Test
    void delete__checkArgumentsEntityEvent() {
        ArgumentCaptor<EntityEvent> eventCaptor = ArgumentCaptor.forClass(EntityEvent.class);

        when(userRepo.findById(userId)).thenReturn(Optional.of(founded));

        subject.delete(userId);

        verify(publisher, times(1)).publishEvent(eventCaptor.capture());
        EntityEvent captured = eventCaptor.getValue();
        assertThat(captured.getAccessType()).isEqualTo(AccessType.DELETE);
        assertThat(captured.getSource()).isEqualTo(founded);
    }

}