package ru.savinov.pizzaservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.savinov.pizzaservice.entities.Ingredient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PizzaDto {

    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

}