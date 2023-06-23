package ru.savinov.pizzaservice.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static java.util.Objects.isNull;

@Data
@Table("INGREDIENT")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient implements Persistable<String> {

    @Id
    private String id;

    @Column("NAME")
    private String name;

    @Column("TYPE")
    private Type type;

    public enum Type {
        CHEESE, THIN_CRUST, THICK_CRUST, SAUSAGE, TOMATO, VEGGIES
    }

    @Override
    public boolean isNew() {
        return isNull(id);
    }

}