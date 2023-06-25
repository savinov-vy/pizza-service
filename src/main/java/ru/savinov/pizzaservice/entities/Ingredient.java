package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Id
    private String id;

    private String name;

    private Type type;

    public enum Type {
        CHEESE, THIN_CRUST, THICK_CRUST, SAUSAGE, TOMATO, VEGGIES
    }

}