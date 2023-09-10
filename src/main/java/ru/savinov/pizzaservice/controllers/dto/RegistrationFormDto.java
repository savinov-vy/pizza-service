package ru.savinov.pizzaservice.controllers.dto;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.savinov.pizzaservice.entities.User;

@AllArgsConstructor
public class RegistrationFormDto {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.of(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
    }

}