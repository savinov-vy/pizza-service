package ru.savinov.pizzaservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.savinov.pizzaservice.entities.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
}