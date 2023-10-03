package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savinov.pizzaservice.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
}