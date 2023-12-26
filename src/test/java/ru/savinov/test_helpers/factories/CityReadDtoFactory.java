package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.CityReadDto;
import ru.savinov.pizzaservice.entities.City;

public class CityReadDtoFactory {

    public static CityReadDto of() {
        City city = CityFactory.of();
        return CityReadDto.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }

}