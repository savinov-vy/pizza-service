package ru.savinov.pizzaservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.savinov.pizzaservice.controllers.OrderController;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.repositories.IngredientRepository;
import ru.savinov.pizzaservice.repositories.PizzaRepository;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class PizzaServiceApplication {

    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PizzaServiceApplication.class, args);
		OrderController orderController = context.getBean("orderController", OrderController.class);
		System.out.println(orderController);
	}

    @Bean
    @Profile({"dev", "qa"})
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo, PizzaRepository pizzaRepo) {
        return args -> {
            Ingredient flourTortilla = Ingredient.of("FLTO", "Flour Tortilla", Ingredient.Type.CHEESE);
            Ingredient cheddar = Ingredient.of("CHED", "Cheddar", Ingredient.Type.CHEESE);
            Ingredient cornTortilla = Ingredient.of("COTO", "Corn Tortilla", Ingredient.Type.SAUSAGE);
            Ingredient groundBeef = Ingredient.of("GRBF", "Ground Beef", Ingredient.Type.THICK_CRUST);
            Ingredient carnitas = Ingredient.of("CARN", "Carnitas", Ingredient.Type.THIN_CRUST);
            Ingredient lettuce = Ingredient.of("LETC", "Lettuce", Ingredient.Type.TOMATO);
            Ingredient monterreyJack = Ingredient.of("JACK", "Monterrey Jack", Ingredient.Type.VEGGIES);
            Ingredient salsa = Ingredient.of("SLSA", "Salsa", Ingredient.Type.TOMATO);

            ingredientRepo.deleteAll();
            ingredientRepo.saveAll(Arrays.asList(flourTortilla, cheddar, cornTortilla, groundBeef, carnitas, lettuce,
                    monterreyJack, salsa));

            pizzaRepo.save(Pizza.of(1L, "pizza1", Arrays.asList(flourTortilla, cheddar, salsa), new Date()));
            pizzaRepo.save(Pizza.of(2L, "pizza2", Arrays.asList(carnitas, lettuce, salsa), new Date()));
            pizzaRepo.save(Pizza.of(3L, "pizza3", Arrays.asList(monterreyJack, salsa), new Date()));
        };
    }

}
