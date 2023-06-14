package ru.savinov.pizzaservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.entities.PizzaOrder;

import static ru.savinov.pizzaservice.entities.Ingredient.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
public class DesignPizzaController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.CHEESE),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("COTO", "Corn Tortilla", Type.SAUSAGE),
                new Ingredient("GRBF", "Ground Beef", Type.THICK_CRUST),
                new Ingredient("CARN", "Carnitas", Type.THIN_CRUST),
                new Ingredient("LETC", "Lettuce", Type.TOMATO),
                new Ingredient("JACK", "Monterrey Jack", Type.VEGGIES),
                new Ingredient("SLSA", "Salsa", Type.TOMATO)
        );

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "pizzaOrder")
    public PizzaOrder order() {
        return new PizzaOrder();
    }

    @ModelAttribute(name = "pizza")
    public Pizza pizza() {
        return new Pizza();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processPizza(Pizza pizza, @ModelAttribute PizzaOrder pizzaOrder) {
        pizzaOrder.addPizza(pizza);
        log.info("Processing pizza: {}", pizza);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }

}