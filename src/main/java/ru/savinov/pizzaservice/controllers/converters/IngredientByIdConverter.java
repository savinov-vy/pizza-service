package ru.savinov.pizzaservice.controllers.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.entities.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.CHEESE));
        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("COTO", new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.SAUSAGE));
        ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.THICK_CRUST));
        ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas", Ingredient.Type.THIN_CRUST));
        ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Ingredient.Type.TOMATO));
        ingredientMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.VEGGIES));
        ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Ingredient.Type.TOMATO));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }

}