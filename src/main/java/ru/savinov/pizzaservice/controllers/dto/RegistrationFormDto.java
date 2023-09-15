package ru.savinov.pizzaservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.entities.User;

@Data
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
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullname(fullname)
                .street(street)
                .city(City.of(city))
                .state(state)
                .zip(zip)
                .phoneNumber(phone)
                .build();
    }

}