package ru.savinov.pizzaservice.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.savinov.pizzaservice.entities.Ingredient;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach
    void setUp() {
        ingredientRepo.save(Ingredient.of("AAAA", "Flour Tortilla", Ingredient.Type.CHEESE));
    }

    @Test
    public void findById() {
        Optional<Ingredient> flto = ingredientRepo.findById("AAAA");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(Ingredient.of("AAAA", "Flour Tortilla", Ingredient.Type.CHEESE));

        Optional<Ingredient> xxxx = ingredientRepo.findById("ZZZZ");
        assertThat(xxxx.isEmpty()).isTrue();
    }

}