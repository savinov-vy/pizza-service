package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.Ingredient;

import java.util.Arrays;
import java.util.List;

public class IngredientFactory {

    public static List<Ingredient> of() {
        return Arrays.asList(
                Ingredient.of(1, "Flour Tortilla", Ingredient.Type.CHEESE),
                Ingredient.of(2, "Cheddar", Ingredient.Type.CHEESE),
                Ingredient.of(3, "Salsa", Ingredient.Type.TOMATO)
        );
    }

}