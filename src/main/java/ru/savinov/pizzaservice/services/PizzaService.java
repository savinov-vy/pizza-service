package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.savinov.pizzaservice.entities.Pizza;
import ru.savinov.pizzaservice.repositories.PizzaRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Pizza> findById(Long id) {
        return pizzaRepo.findById(id);
    }

}