package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoFactory {

    public static UserReadDto userReadDto() {
        return mapToUserReadDto(UserFactory.of());
    }

    public static UserReadDto userReadDto(User user) {
        return mapToUserReadDto(user);
    }

    public static List<UserReadDto> userReadDtoList() {
        return UserFactory.ofUsers().stream()
                .map(UserDtoFactory::mapToUserReadDto)
                .collect(Collectors.toList());
    }

    private static UserReadDto mapToUserReadDto(User user) {
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