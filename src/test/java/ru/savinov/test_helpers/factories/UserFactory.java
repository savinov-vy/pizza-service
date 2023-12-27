package ru.savinov.test_helpers.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UserFactory {

    public static User of() {
        return build(1L);
    }

    public static List<User> ofUsers() {
        return LongStream.rangeClosed(1, 5)
                .boxed()
                .map(UserFactory::build)
                .collect(Collectors.toList());
    }

    private static User build(Long id) {
        return User.builder()
                .id(id)
                .username("usernameTest" + id)
                .password(encode())
                .fullname("fullNameUserTest")
                .street("nameStreetUserTest")
                .role(Role.USER)
                .city(CityFactory.of())
                .build();
    }

    private static String encode() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode("passwordTest");
    }

}