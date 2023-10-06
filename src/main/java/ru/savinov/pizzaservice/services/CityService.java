package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.repositories.CityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

}