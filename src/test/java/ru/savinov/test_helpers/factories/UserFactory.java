package ru.savinov.test_helpers.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserFactory {

    public static User of() {
        return build("");
    }

    public static List<User> ofUsers() {
        return IntStream.rangeClosed(1, 5)
                .boxed()
                .map(Object::toString)
                .map(UserFactory::build)
                .collect(Collectors.toList());
    }

    private static User build(String postfix) {
        return User.builder()
                .username("usernameTest" + postfix)
                .password(encode("passwordTest"))
                .fullname("fullNameUserTest")
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