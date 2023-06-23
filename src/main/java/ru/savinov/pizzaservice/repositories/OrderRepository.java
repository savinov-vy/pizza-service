package ru.savinov.pizzaservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.savinov.pizzaservice.entities.PizzaOrder;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {
}