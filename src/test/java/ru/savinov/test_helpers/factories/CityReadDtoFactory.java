package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.CityReadDto;
import ru.savinov.pizzaservice.entities.City;

public class CityReadDtoFactory {

    public static CityReadDto of() {
        return of(CityFactory.of());
    }

    public static CityReadDto withId() {
        return of(CityFactory.withId());
    }

    public static CityReadDto withName() {
        return of(CityFactory.withName());
    }

    public static CityReadDto withLocales() {
        return of(CityFactory.withLocales());
    }

    public static CityReadDto of(City city) {
        return CityReadDto.builder()
                .id(city.getId())
                .name(city.getName())
                .locales(city.getLocales())
                .build();
    }

    public static CityReadDto empty() {
        return CityReadDto.builder()
                .build();
    }

}