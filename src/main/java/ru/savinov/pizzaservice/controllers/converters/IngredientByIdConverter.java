package ru.savinov.pizzaservice.controllers.converters;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.repositories.IngredientRepository;

@Component
@AllArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepo;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }

}