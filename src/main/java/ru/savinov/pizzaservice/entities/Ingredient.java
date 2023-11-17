package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@Table(name = "ingredient")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Ingredient implements BaseEntity<String>{

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        CHEESE, THIN_CRUST, THICK_CRUST, SAUSAGE, TOMATO, VEGGIES
    }

}