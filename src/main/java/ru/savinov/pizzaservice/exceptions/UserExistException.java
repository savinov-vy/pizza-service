package ru.savinov.pizzaservice.exceptions;

public class UserExistException extends RuntimeException {

    private static final long serialVersionUID = -2150584498434585335L;

    public UserExistException(String message) {
        super(message);
    }

}