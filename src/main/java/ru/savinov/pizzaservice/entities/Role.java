package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    public static Role getByValue(String value) {
        return Arrays.stream(values())
                .filter(item -> item.getValue().equals(value))
                .findAny().orElse(null);
    }

}