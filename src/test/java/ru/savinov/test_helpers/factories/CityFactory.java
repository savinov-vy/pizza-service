package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.City;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CityFactory {

    public static City of() {
        return City.builder()
                .id(1)
                .name("nameCityTest")
                .locales(locales())
                .build();
    }

    public static City withId() {
        return City.builder()
                .id(555)
                .name(null)
                .locales(null)
                .build();
    }

    public static City withName() {
        return City.builder()
                .id(null)
                .name("nameCityTest")
                .locales(null)
                .build();
    }

    public static City withLocales() {
        return City.builder()
                .id(null)
                .name(null)
                .locales(locales())
                .build();
    }

    public static City empty() {
        return City.builder()
                .build();
    }

    private static Map<String, String> locales() {
        return Stream.of("en", "ru", "fr")
                .collect(Collectors.toMap(
                        locale -> locale,
                        locale -> "description locale for " + locale));
    }

}