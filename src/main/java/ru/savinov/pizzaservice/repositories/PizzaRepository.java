package ru.savinov.pizzaservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.savinov.pizzaservice.entities.Pizza;

public interface PizzaRepository extends PagingAndSortingRepository<Pizza, Long> {
    Pizza findByName(String name);
}