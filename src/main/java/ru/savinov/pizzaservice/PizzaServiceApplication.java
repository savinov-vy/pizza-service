package ru.savinov.pizzaservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.repositories.IngredientRepository;

@SpringBootApplication
public class PizzaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
		return args -> {
			repo.deleteAll();
			repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.CHEESE));
			repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
			repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.SAUSAGE));
			repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.THICK_CRUST));
			repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.THIN_CRUST));
			repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.TOMATO));
			repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.VEGGIES));
			repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.TOMATO));
		};
	}

}
