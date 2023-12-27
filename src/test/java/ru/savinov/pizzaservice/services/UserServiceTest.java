package ru.savinov.pizzaservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.mapper.UserCreateEditDtoMapper;
import ru.savinov.pizzaservice.mapper.UserReadMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;
import ru.savinov.test_helpers.factories.UserDtoFactory;
import ru.savinov.test_helpers.factories.UserFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        List<UserReadDto> userReadDtoList = UserDtoFactory.ofUserReadDtoList();
        Map<User, UserReadDto> userToUserReadDto = IntStream.range(0, users.size())
                .boxed()
                .collect(Collectors.toMap(users::get, userReadDtoList::get));

        when(userRepo.findAll()).thenReturn(users);
        userToUserReadDto.forEach((key, value) -> when(userReadMapper.map(key)).thenReturn(value));

        List<UserReadDto> userReadDtoListResult = subject.findAll();
        assertThat(userReadDtoListResult).hasSize(5);
    }

    @Test
    void findById() {
        User user = UserFactory.of();
        UserReadDto expected = UserDtoFactory.ofUserReadDto();

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

}