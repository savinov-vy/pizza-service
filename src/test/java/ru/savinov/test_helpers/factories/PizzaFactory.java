package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.Pizza;

public class PizzaFactory {

    public static Pizza of(String namePizza) {
        return Pizza.builder()
                .name(namePizza)
                .ingredients(IngredientFactory.of())
                .build();
    }

}