package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.entities.Role;

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

}