package ru.savinov.pizzaservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.exceptions.PasswordNullException;
import ru.savinov.pizzaservice.repositories.CityRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;
    private final CityRepository cityRepository;
    private static final String PASSWORD_MUST_FILLED = "Password must be filled";

    @Override
    public User map(UserCreateEditDto object) {
        return User.builder()
                .fullname(object.getFullname())
                .username(object.getUsername())
                .password(Optional.ofNullable(object.getPassword())
                        .filter(StringUtils::hasText)
                        .map(passwordEncoder::encode).orElseThrow(() -> new PasswordNullException(PASSWORD_MUST_FILLED)))
                .role(object.getRole())
                .street(object.getStreet())
                .city(getCity(object.getCityId()))
                .build();
    }

    @Override
    public User map(UserCreateEditDto fromDto, User toUser) {
        toUser.setFullname(fromDto.getFullname());
        toUser.setUsername(fromDto.getUsername());
        toUser.setRole(fromDto.getRole());
        toUser.setStreet(fromDto.getStreet());
        toUser.setCity(getCity(fromDto.getCityId()));
        return toUser;
    }

    private City getCity(Integer cityId) {
        return Optional.of(cityId)
                .flatMap(cityRepository::findById)
                .orElse(null);
    }

}