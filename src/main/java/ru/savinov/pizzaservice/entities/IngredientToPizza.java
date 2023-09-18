package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@Table(name = "ingredient_pizza")
@AllArgsConstructor
@NoArgsConstructor
public class IngredientToPizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ingredient_id")
    private String ingredientId;

    @Column(name = "pizza_id")
    private Integer pizzaId;

}