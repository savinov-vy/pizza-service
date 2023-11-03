package ru.savinov.pizzaservice.exceptions;

public class PasswordNullException extends RuntimeException{

    private static final long serialVersionUID = -5445968973506019152L;

    public PasswordNullException(String message) {
        super(message);
    }
}