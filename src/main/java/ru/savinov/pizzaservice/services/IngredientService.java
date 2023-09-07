package ru.savinov.pizzaservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.repositories.IngredientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepo;

    public Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        return (List<Ingredient>) ingredientRepo.findAll();
    }

}