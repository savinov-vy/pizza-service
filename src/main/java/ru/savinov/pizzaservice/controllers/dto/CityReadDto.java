package ru.savinov.pizzaservice.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityReadDto {

    private Integer id;
    private String name;

}