package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.City;

public class CityFactory {

    public static City of() {
        return City.builder()
                .id(1)
                .name("nameCityTest")
                .build();
    }

}