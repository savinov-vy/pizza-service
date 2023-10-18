package ru.savinov.pizzaservice.controllers.dto;

import lombok.Builder;
import lombok.Data;
import ru.savinov.pizzaservice.entities.Role;

@Data
@Builder
public class UserReadDto {

    private Long id;
    private String username;
    private String fullname;
    private String street;
    private CityReadDto city;
    private Role role;

}