package ru.savinov.pizzaservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.entities.PizzaOrder;
import ru.savinov.pizzaservice.services.IngredientService;

import javax.validation.Valid;

import static ru.savinov.pizzaservice.entities.Ingredient.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
@AllArgsConstructor
public class DesignPizzaController {

    private final IngredientService ingredientService;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = ingredientService.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), ingredientService.filterByType(ingredients, type));
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
    public String processPizza(@Valid Pizza pizza, Errors errors, @ModelAttribute PizzaOrder pizzaOrder, Model model) {
        if (errors.hasErrors()) {
            List<String> allErrors = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("allErrors", allErrors);
            return "design";
        }
        pizzaOrder.addPizza(pizza);
        log.info("Processing pizza: {}", pizza);
        return "redirect:/orders/current";
    }

}