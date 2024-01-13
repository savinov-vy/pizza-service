package ru.savinov.pizzaservice.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CityReadDto {

    private Integer id;
    private String name;
    private Map<String, String> locales;

}