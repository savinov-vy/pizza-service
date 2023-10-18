package ru.savinov.pizzaservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
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

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullname(fullname)
                .street(street)
                .role(Role.USER)
                .city(City.of(cityId))
                .build();
    }

}