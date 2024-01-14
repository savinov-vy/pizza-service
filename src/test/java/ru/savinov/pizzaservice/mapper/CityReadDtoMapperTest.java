package ru.savinov.pizzaservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.savinov.pizzaservice.controllers.dto.CityReadDto;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.test_helpers.factories.CityFactory;
import ru.savinov.test_helpers.factories.CityReadDtoFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CityReadDtoMapperTest {

    CityReadDtoMapper subject;

    @BeforeEach
    void setUp() {
        subject = new CityReadDtoMapper();
    }

    @ParameterizedTest
    @MethodSource("getArgumentsMapTest")
    void map(CityReadDto expected, City city) {
        CityReadDto actual = subject.map(city);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getArgumentsMapTest() {
        return Stream.of(
                Arguments.of(CityReadDtoFactory.withId(), CityFactory.withId()),
                Arguments.of(CityReadDtoFactory.withName(), CityFactory.withName()),
                Arguments.of(CityReadDtoFactory.withLocales(), CityFactory.withLocales()),
                Arguments.of(CityReadDtoFactory.of(), CityFactory.of()),
                Arguments.of(CityReadDtoFactory.empty(), CityFactory.empty())
        );
    }

}