package ru.savinov.pizzaservice.entities;

import lombok.Data;

import java.util.List;

@Data
public class Pizza {

    private String id;
    private String name;
    private List<Ingredient> ingredients;

}