package ru.savinov.pizzaservice.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Table("PIZZA")
@EqualsAndHashCode(exclude = "createdAt")
public class Pizza {

    @Id
    private Long id;

    @NotNull
    @Column("NAME")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<IngredientRef> ingredients;

    @Column("CREATED_AT")
    private Date createdAt = new Date();

}