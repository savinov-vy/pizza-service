package ru.savinov.pizzaservice.integration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.savinov.pizzaservice.integration.annotation.IT;
import ru.savinov.pizzaservice.repositories.UserRepository;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("ов", "admin");
        Assertions.assertThat(users).hasSize(1);
    }

}