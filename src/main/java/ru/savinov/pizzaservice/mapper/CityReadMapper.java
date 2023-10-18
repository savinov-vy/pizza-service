package ru.savinov.pizzaservice.mapper;

import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.CityReadDto;
import ru.savinov.pizzaservice.entities.City;

@Component
public class CityReadMapper implements Mapper<City, CityReadDto> {

    @Override
    public CityReadDto map(City object) {
        return CityReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

}