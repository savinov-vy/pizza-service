package ru.savinov.pizzaservice.integration.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.integration.annotation.IT;
import ru.savinov.pizzaservice.repositories.PizzaRepository;
import ru.savinov.pizzaservice.services.PizzaService;
import ru.savinov.test_helpers.factories.PizzaFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class PizzaServiceIT {

    private Pizza pizza;

    private final PizzaService pizzaService;
    private final PizzaRepository pizzaRepo;

    @Test
    @Transactional
    @Rollback
    void findById() {
        pizza = PizzaFactory.of("PizzaNameTest");
        pizzaRepo.save(pizza);
        var pizza = pizzaRepo.findByName("PizzaNameTest");

        Optional<Pizza> actualResult = pizzaService.findById(pizza.getId());
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> assertEquals(pizza, actual));
    }

}