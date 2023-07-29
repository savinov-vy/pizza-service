package ru.savinov.pizzaservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.repositories.PizzaRepository;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/pizzas",
        produces = "application/json")
@CrossOrigin(origins = "http://pizzaservice:8080")
public class PizzaController {

    private PizzaRepository pizzaRepo;

    @GetMapping(params = "recent")
    public Iterable<Pizza> recentPizzas() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return pizzaRepo.findAll(page).getContent();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody Pizza pizza) {
        return pizzaRepo.save(pizza);
    }

    @GetMapping("/{id}")
    public Optional<Pizza> pizzaById(@PathVariable("id") Long id) {
        return pizzaRepo.findById(id);
    }

}
