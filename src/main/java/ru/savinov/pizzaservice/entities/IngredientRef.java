package ru.savinov.pizzaservice.entities;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("INGREDIENT_REF")
public class IngredientRef {

    private final String ingredient;

}