package ru.savinov.pizzaservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.CityReadDto;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.User;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CityReadDtoMapper cityReadDtoMapper;

    @Override
    public UserReadDto map(User object) {
        CityReadDto city = Optional.ofNullable(object.getCity())
                .map(cityReadDtoMapper::map)
                .orElse(null);

        return UserReadDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .fullname(object.getFullname())
                .street(object.getStreet())
                .city(city)
                .role(object.getRole())
                .build();
    }

}