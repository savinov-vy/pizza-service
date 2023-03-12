package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ingredient {

    private String id;
    private String name;
    private Type type;

    public enum Type {
        CHEESE, THIN_CRUST, THICK_CRUST, SAUSAGE, TOMATO, VEGGIES
    }

}