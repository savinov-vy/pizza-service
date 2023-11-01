package ru.savinov.pizzaservice.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.savinov.pizzaservice.exceptions.UserExistException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public String handleUserExistException(Exception exception, Model model) {
        log.info("handleUserExistException with exception message: {}", exception.getMessage());
        model.addAttribute("errorMessages", exception.getMessage());
        return "registration";
    }

}