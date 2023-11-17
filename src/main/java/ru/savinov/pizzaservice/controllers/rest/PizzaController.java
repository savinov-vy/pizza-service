package ru.savinov.pizzaservice.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.savinov.pizzaservice.config.PizzaPageProps;
import ru.savinov.pizzaservice.controllers.dto.PizzaDto;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.mapper.PizzaDtoMapper;
import ru.savinov.pizzaservice.services.PizzaService;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/pizzas",
        produces = "application/json")
@CrossOrigin(origins = "http://pizzaservice:8080")
public class PizzaController {

    private PizzaDtoMapper pizzaDtoMapper;
    private PizzaPageProps pizzaPageProps;
    private PizzaService pizzaService;

    @GetMapping(params = "recent")
    public Iterable<Pizza> recentPizzas() {
        PageRequest page = PageRequest.of(0, pizzaPageProps.getSizePage(), Sort.by("createdAt").descending());
        return pizzaService.findAll(page);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody PizzaDto dto) {
        Pizza pizza = pizzaDtoMapper.map(dto);
        return pizzaService.save(pizza);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> pizzaById(@PathVariable("id") Long id) {
        Optional<Pizza> found = pizzaService.findById(id);
        return found.map(pizza -> new ResponseEntity<>(pizza, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
