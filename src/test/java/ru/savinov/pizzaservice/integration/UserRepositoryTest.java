package ru.savinov.pizzaservice.integration;

import lombok.RequiredArgsConstructor;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.integration.annotation.IT;
import ru.savinov.pizzaservice.repositories.UserRepository;
import static org.junit.jupiter.api.Assertions.assertSame;
@IT
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("ов", "admin");
        ListAssert<User> userListAssert = Assertions.assertThat(users);
        userListAssert.hasSize(1);
    }

    @Test
    @Transactional
    void checkUpdate() {
        var ivan = userRepository.getById(4L);
        assertSame(Role.USER, ivan.getRole());

        var resultCount = userRepository.updateRole(Role.ADMIN, 4L, 5L);

        var theSameIvan = userRepository.getById(4L);
        assertSame(Role.ADMIN, theSameIvan.getRole());
    }

}