package ru.savinov.pizzaservice.repositories;

import ru.savinov.pizzaservice.entities.PizzaOrder;

public interface OrderRepository {

    PizzaOrder save(PizzaOrder order);

}