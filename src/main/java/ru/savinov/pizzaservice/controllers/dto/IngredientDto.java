package ru.savinov.pizzaservice.controllers.dto;

import lombok.Builder;
import lombok.Data;

import static ru.savinov.pizzaservice.entities.Ingredient.*;

@Data
@Builder
public class IngredientDto {

    private String id;
    private String name;
    private Type type;

}