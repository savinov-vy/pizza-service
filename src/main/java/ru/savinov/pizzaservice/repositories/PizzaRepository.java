package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import ru.savinov.pizzaservice.entities.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long>, RevisionRepository<Pizza, Long, Integer> {
    Pizza findByName(String name);
}