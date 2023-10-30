package ru.savinov.pizzaservice.validation.registration;

import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserCreateInfoValidator implements ConstraintValidator<UserCreateInfo, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }
}