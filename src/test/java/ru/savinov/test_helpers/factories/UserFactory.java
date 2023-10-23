package ru.savinov.test_helpers.factories;

import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;


public class UserFactory {

    public static User of() {
        String passwordTest = "$2y$10$WfKo9qqW70z9T..iSJw1QOUc0tBz.xUBIEqRG2sis6CvjQuwDpWBu";
        return User.builder()
                .username("usernameTest")
                .password(passwordTest)
                .fullname("fullnameUserTest")
                .street("nameStreetUserTest")
                .role(Role.USER)
                .city(CityFactory.of())
                .build();
    }

}