package ru.savinov.pizzaservice.mapper;

import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.exceptions.PasswordNullException;
import ru.savinov.pizzaservice.repositories.CityRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditDtoMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;
    private final CityRepository cityRepository;
    private static final String PASSWORD_MUST_FILLED = "Password must be filled";

    @Override
    public User map(UserCreateEditDto object) {
        return User.builder()
                .fullname(object.getFullname())
                .username(object.getUsername())
                .image(object.getImage().getOriginalFilename())
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
        toUser.setImage(fromDto.getImage().getOriginalFilename());
        toUser.setRole(fromDto.getRole());
        toUser.setStreet(fromDto.getStreet());
        toUser.setCity(getCity(fromDto.getCityId()));
        return toUser;
    }

    public User map(OidcUserRequest userRequest) {
        OidcIdToken token = userRequest.getIdToken();
        String randomPassword = "%" + RandomStringUtils.randomAlphanumeric(10) + "!";
        return User.builder()
                .username(token.getEmail())
                .password(passwordEncoder.encode(randomPassword))
                .fullname(token.getFullName())
                .role(Role.USER)
                .build();
    }

    private City getCity(Integer cityId) {
        return Optional.of(cityId)
                .flatMap(cityRepository::findById)
                .orElse(null);
    }

}