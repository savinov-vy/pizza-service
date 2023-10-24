package ru.savinov.pizzaservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.savinov.pizzaservice.entities.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class UserCreateEditDto {

    @Size(min = 1, message = "Enter your login")
    private String username;

    @Size(min = 1, message = "Enter your password")
    private String password;

    @Size(min = 1, message = "retry your password")
    private String confirmPassword;

    @Size(min = 1, message = "Enter your fullname")
    private String fullname;

    @Size(min = 1, message = "Enter your street")
    private String street;

    @NotNull(message = "City must be selected")
    private Integer cityId;

    private Role role;

}