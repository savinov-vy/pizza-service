package ru.savinov.pizzaservice.integration.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.integration.IntegrationTestBase;
import ru.savinov.pizzaservice.services.UserService;
import ru.savinov.test_helpers.factories.UserCreateEditDtoFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(8);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(1L);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> {
            assertEquals("admin", user.getUsername());
            assertEquals(Role.ADMIN, user.getRole());
        });
    }

    @Test
    @Transactional
    void create() {
        UserCreateEditDto userDto = UserCreateEditDtoFactory.of();
        UserReadDto actualResult = userService.create(userDto);
        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertSame(userDto.getRole(), actualResult.getRole());
        assertEquals(userDto.getCityId(), actualResult.getCity().getId());
        assertEquals(userDto.getFullname(), actualResult.getFullname());
        assertEquals(userDto.getStreet(), actualResult.getStreet());
    }

}