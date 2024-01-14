package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.controllers.dto.IngredientDto;
import ru.savinov.pizzaservice.entities.Ingredient;

public class IngredientDtoFactory {

    public static IngredientDto of() {
        return of(IngredientFactory.of());
    }

    public static IngredientDto withId() {
        return of(IngredientFactory.withId());
    }

    public static IngredientDto withName() {
        return of(IngredientFactory.withName());
    }

    public static IngredientDto withType() {
        return of(IngredientFactory.withType());
    }

    public static IngredientDto empty() {
        return of(IngredientFactory.empty());
    }



    public static IngredientDto of(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .type(ingredient.getType())
                .build();
    }

}