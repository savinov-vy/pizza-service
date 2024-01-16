package ru.savinov.pizzaservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.savinov.pizzaservice.controllers.dto.IngredientDto;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.test_helpers.factories.IngredientDtoFactory;
import ru.savinov.test_helpers.factories.IngredientFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IngredientDtoMapperTest {

    IngredientDtoMapper subject;

    @BeforeEach
    void setUp() {
        subject = new IngredientDtoMapper();
    }

    @ParameterizedTest
    @MethodSource("getArgumentsMapTest")
    void map_fromIngredientToDto(IngredientDto expected, Ingredient ingredient) {
        IngredientDto actual = subject.map(ingredient);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsMapTest")
    void map_fromDtoToIngredient(IngredientDto ingredientDto, Ingredient expected) {
        Ingredient actual = subject.map(ingredientDto);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> getArgumentsMapTest() {
        return Stream.of(
                Arguments.of(IngredientDtoFactory.of(), IngredientFactory.of()),
                Arguments.of(IngredientDtoFactory.empty(), IngredientFactory.empty()),
                Arguments.of(IngredientDtoFactory.withId(), IngredientFactory.withId()),
                Arguments.of(IngredientDtoFactory.withName(), IngredientFactory.withName()),
                Arguments.of(IngredientDtoFactory.withType(), IngredientFactory.withType())
        );
    }

    @ParameterizedTest
    @MethodSource("getArgumentsMapPatchTest")
    void map_patch(IngredientDto fromDto, Ingredient toIngredient, Ingredient expected) {
        Ingredient actual = subject.map(fromDto, toIngredient);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> getArgumentsMapPatchTest() {
        return Stream.of(
                Arguments.of(IngredientDtoFactory.withName(), IngredientFactory.empty(), IngredientFactory.withName()),
                Arguments.of(IngredientDtoFactory.withType(), IngredientFactory.empty(), IngredientFactory.withType()),
                Arguments.of(IngredientDtoFactory.empty(), IngredientFactory.empty(), IngredientFactory.empty())
        );
    }

}