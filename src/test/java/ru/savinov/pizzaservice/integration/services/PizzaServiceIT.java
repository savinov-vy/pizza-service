package ru.savinov.pizzaservice.integration.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.integration.annotation.IT;
import ru.savinov.pizzaservice.repositories.IngredientRepository;
import ru.savinov.pizzaservice.repositories.PizzaRepository;
import ru.savinov.pizzaservice.services.PizzaService;
import ru.savinov.test_helpers.factories.IngredientFactory;
import ru.savinov.test_helpers.factories.PizzaFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class PizzaServiceIT {

    private Pizza pizza;

    private final PizzaService pizzaService;
    private final IngredientRepository ingredientRepo;
    private final PizzaRepository pizzaRepo;

    @BeforeEach
    void setUp() {
        ingredientRepo.saveAll(IngredientFactory.of());
        Iterable<Ingredient> all = ingredientRepo.findAll();
        pizza = PizzaFactory.of(1L);
        pizzaRepo.save(pizza);
    }

    @Test
    void findById() {
        Optional<Pizza> actualResult = pizzaService.findById(1L);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> assertEquals(pizza, actual));
    }

}