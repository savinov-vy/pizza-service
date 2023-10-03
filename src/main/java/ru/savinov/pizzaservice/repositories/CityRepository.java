package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savinov.pizzaservice.entities.City;

public interface CityRepository extends JpaRepository<City, Integer> {
}
