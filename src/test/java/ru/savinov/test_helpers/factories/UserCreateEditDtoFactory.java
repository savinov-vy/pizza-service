package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.entities.Role;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserCreateEditDtoFactory {

    public static UserCreateEditDto of() {
        return UserCreateEditDto.builder()
                .username("userNameTest")
                .password("userPasswordTest")
                .cityId(1)
                .street("StreetUserTest")
                .fullname("FullNameUserTest")
                .role(Role.USER)
                .build();
    }

    public static List<UserCreateEditDto> listOf5Items() {
        return Stream.of(1, 2, 3, 4, 5)
                .map(suffix -> {
                    UserCreateEditDto user = of();
                    user.setUsername(user.getUsername() + suffix);
                    user.setPassword(user.getPassword() + suffix);
                    return user;
                }).collect(Collectors.toList());
    }

}