package ru.savinov.pizzaservice.mapper;

import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.IngredientDto;
import ru.savinov.pizzaservice.entities.Ingredient;

@Component
public class IngredientDtoMapper implements Mapper<Ingredient, IngredientDto> {

    @Override
    public IngredientDto map(Ingredient object) {
        return IngredientDto.builder()
                .id(object.getId())
                .name(object.getName())
                .type(object.getType())
                .build();
    }

    public Ingredient map(IngredientDto object) {
        return Ingredient.builder()
                .id(object.getId())
                .name(object.getName())
                .type(object.getType())
                .build();
    }

    public Ingredient map(IngredientDto fromDto, Ingredient toIngredient) {
        toIngredient.setName(fromDto.getName());
        toIngredient.setType(fromDto.getType());
        return toIngredient;
    }

}