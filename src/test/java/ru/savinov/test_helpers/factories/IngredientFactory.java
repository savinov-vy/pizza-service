package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.Ingredient;

import java.util.Arrays;
import java.util.List;

public class IngredientFactory {

    public static List<Ingredient> ingredients() {
        return Arrays.asList(
                Ingredient.of("FLTO", "Flour Tortilla", Ingredient.Type.CHEESE),
                Ingredient.of("CHED", "Cheddar", Ingredient.Type.CHEESE),
                Ingredient.of("SLSA", "Salsa", Ingredient.Type.TOMATO)
        );
    }

    public static Ingredient of() {
        return Ingredient.of("FLTO", "Flour Tortilla", Ingredient.Type.CHEESE);
    }

    public static Ingredient withId() {
        return Ingredient.builder()
                .id("ID")
                .name(null)
                .type(null)
                .build();
    }

    public static Ingredient withName() {
        return Ingredient.builder()
                .id("ID")
                .name(null)
                .type(null)
                .build();
    }

    public static Ingredient withType() {
        return Ingredient.builder()
                .id("ID")
                .name(null)
                .type(null)
                .build();
    }

    public static Ingredient empty() {
        return Ingredient.builder()
                .id(null)
                .name(null)
                .type(null)
                .build();
    }

}