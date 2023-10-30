package ru.savinov.pizzaservice.validation.registration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserCreateInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserCreateInfo {

    String message() default "Password and confirm password is not equals";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}