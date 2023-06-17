package ru.savinov.pizzaservice.repositories;

import ru.savinov.pizzaservice.entities.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}