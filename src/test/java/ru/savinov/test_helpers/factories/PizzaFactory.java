package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.Pizza;

public class PizzaFactory {

    public static Pizza of(Long id) {
        return Pizza.builder()
                .id(id)
                .name("PizzaNameTest")
                .ingredients(IngredientFactory.of())
                .build();
    }

}