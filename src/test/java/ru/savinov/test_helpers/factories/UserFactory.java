package ru.savinov.test_helpers.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;

public class UserFactory {

    public static User of() {
        return build();
    }

    private static User build() {
        return User.builder()
                .username("usernameTest")
                .password(encode("passwordTest"))
                .fullname("fullnameUserTest")
                .street("nameStreetUserTest")
                .role(Role.USER)
                .city(CityFactory.of())
                .build();
    }

    private static String encode(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}