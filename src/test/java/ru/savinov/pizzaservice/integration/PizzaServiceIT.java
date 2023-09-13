package ru.savinov.pizzaservice.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.repositories.IngredientRepository;
import ru.savinov.pizzaservice.repositories.PizzaRepository;
import ru.savinov.pizzaservice.services.PizzaService;
import ru.savinov.test_helpers.factories.IngredientFactory;
import ru.savinov.test_helpers.factories.PizzaFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PizzaServiceIT {

    private Pizza pizza;

    @Autowired
    PizzaService pizzaService;
    @Autowired
    IngredientRepository ingredientRepo;
    @Autowired
    PizzaRepository pizzaRepo;

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