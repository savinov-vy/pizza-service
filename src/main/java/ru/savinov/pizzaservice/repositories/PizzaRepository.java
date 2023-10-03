package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savinov.pizzaservice.entities.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    Pizza findByName(String name);
}