package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoFactory {

    public static UserReadDto ofUserReadDto() {
        return map(UserFactory.of());
    }

    public static List<UserReadDto> ofUserReadDtoList() {
        return UserFactory.ofUsers().stream()
                .map(UserDtoFactory::map)
                .collect(Collectors.toList());
    }

    private static UserReadDto map(User user) {
        return UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .street(user.getStreet())
                .city(CityReadDtoFactory.of())
                .role(user.getRole())
                .build();
    }

}