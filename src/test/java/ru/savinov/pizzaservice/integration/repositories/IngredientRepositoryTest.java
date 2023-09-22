package ru.savinov.pizzaservice.integration.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.repositories.IngredientRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @Test
    @Transactional
    @Rollback
    public void findById() {
        ingredientRepo.save(Ingredient.of("TEST", "Flour Tortilla", Ingredient.Type.CHEESE));

        Optional<Ingredient> flto = ingredientRepo.findById("TEST");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(Ingredient.of("TEST", "Flour Tortilla", Ingredient.Type.CHEESE));

        Optional<Ingredient> xxxx = ingredientRepo.findById("ZZZZ");
        assertThat(xxxx.isEmpty()).isTrue();
    }

}