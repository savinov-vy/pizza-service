package ru.savinov.pizzaservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.savinov.pizzaservice.entities.City;

public interface CityRepository extends CrudRepository<City, Integer> {
}
