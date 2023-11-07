package ru.savinov.pizzaservice.integration.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.integration.IntegrationTestBase;
import ru.savinov.pizzaservice.mapper.UserCreateEditMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;
import ru.savinov.pizzaservice.services.UserService;
import ru.savinov.test_helpers.factories.UserCreateEditDtoFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private Long userId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserCreateEditDtoFactory.listOf5Items().stream()
                .map(userCreateEditMapper::map)
                .map(userRepository::saveAndFlush)
                .forEach(user -> userId = user.getId());
    }

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(userId);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> {
            assertEquals("FullNameUserTest", user.getFullname());
            assertEquals(Role.USER, user.getRole());
        });
    }

    @Test
    void create() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.of();
        UserReadDto actualResult = userService.create(userDto);
        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertSame(userDto.getRole(), actualResult.getRole());
        assertEquals(userDto.getCityId(), actualResult.getCity().getId());
        assertEquals(userDto.getFullname(), actualResult.getFullname());
        assertEquals(userDto.getStreet(), actualResult.getStreet());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.of();
        Optional<UserReadDto> actualResult = userService.update(userId, userDto);
        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(user -> {
            assertEquals(userDto.getUsername(), user.getUsername());
            assertSame(userDto.getRole(), user.getRole());
            assertEquals(userDto.getCityId(), user.getCity().getId());
            assertEquals(userDto.getFullname(), user.getFullname());
            assertEquals(userDto.getStreet(), user.getStreet());
        });
    }

    @Test
    void delete() {
        List<User> allUsers_beforeDelete = userRepository.findAll();
        assertFalse(userService.delete(-5L));
        assertTrue(userService.delete(userId));
        List<User> allUsers_afterDelete = userRepository.findAll();
        assertThat(allUsers_afterDelete).hasSize(allUsers_beforeDelete.size() - 1);
        assertFalse(userService.delete(userId));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}