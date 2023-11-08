package ru.savinov.pizzaservice.mapper;

import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.PizzaDto;
import ru.savinov.pizzaservice.entities.Pizza;

@Component
public class PizzaDtoMapper implements Mapper<Pizza, PizzaDto>{

    @Override
    public PizzaDto map(Pizza object) {
        return PizzaDto.builder()
                .name(object.getName())
                .ingredients(object.getIngredients())
                .build();
    }

    public Pizza map(PizzaDto dto) {
        return Pizza.builder()
                .id(dto.getId())
                .name(dto.getName())
                .ingredients(dto.getIngredients())
                .build();
    }

}