package ru.savinov.pizzaservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.repositories.CityRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;
    private final CityRepository cityRepository;

    @Override
    public User map(UserCreateEditDto object) {
        return User.builder()
                .fullname(object.getFullname())
                .username(object.getUsername())
                .password(passwordEncoder.encode(object.getPassword()))
                .role(getRole(object.getRole()))
                .street(object.getStreet())
                .city(getCity(object.getCityId()))
                .build();
    }

    @Override
    public User map(UserCreateEditDto fromDto, User toUser) {
        toUser.setFullname(fromDto.getFullname());
        toUser.setUsername(fromDto.getUsername());
        toUser.setRole(getRole(fromDto.getRole()));
        toUser.setStreet(fromDto.getStreet());
        toUser.setCity(getCity(fromDto.getCityId()));
        return toUser;
    }

    private City getCity(Integer cityId) {
        return Optional.of(cityId)
                .flatMap(cityRepository::findById)
                .orElse(null);
    }

    private Role getRole(String roleValue) {
        return Optional.ofNullable(roleValue)
                .map(Role::getByValue)
                .orElse(defaultRole());
    }

    private Role defaultRole() {
        return Role.USER;
    }

}