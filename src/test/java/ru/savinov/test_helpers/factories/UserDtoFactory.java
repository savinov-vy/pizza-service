package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.UserReadDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoFactory {

    public static List<UserReadDto> ofUserReadDtoList() {
        return UserFactory.ofUsers().stream()
                .map(user -> UserReadDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .fullname(user.getFullname())
                        .street(user.getStreet())
                        .city(CityReadDtoFactory.of())
                        .role(user.getRole())
                        .build()
                ).collect(Collectors.toList());
    }

}