package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.repositories.PizzaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepo;

    public List<Pizza> findAll(PageRequest page) {
        return pizzaRepo.findAll(page).getContent();
    }

    public Pizza save(Pizza pizza) {
        return pizzaRepo.save(pizza);
    }

    @Transactional(readOnly = true)
    public Optional<Pizza> findById(Long id) {
        Optional<Pizza> optional = pizzaRepo.findById(id);
        Pizza byId = pizzaRepo.findById(id).orElse(null);
        log.info("load pizza with name: {}, and ingredients: {}", byId.getName(), byId.getIngredients());
        return optional;
    }

}