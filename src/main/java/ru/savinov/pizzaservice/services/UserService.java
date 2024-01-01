package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.audit.listener.entities.AccessType;
import ru.savinov.pizzaservice.audit.listener.entities.EntityEvent;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.exceptions.UserExistException;
import ru.savinov.pizzaservice.mapper.UserCreateEditDtoMapper;
import ru.savinov.pizzaservice.mapper.UserReadMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditDtoMapper userCreateEditDtoMapper;

    public List<UserReadDto> findAll() {
        return userRepo.findAll().stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepo.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        eventPublisher.publishEvent(new EntityEvent(userDto, AccessType.CREATE));
        return Optional.of(userDto)
                .map(userCreateEditDtoMapper::map)
                .map(this::saveIsNotExist)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public User create(OidcUserRequest userRequest) {
        eventPublisher.publishEvent(new EntityEvent(userRequest, AccessType.CREATE));
        return Optional.of(userRequest)
                .map(userCreateEditDtoMapper::map)
                .map(this::saveIsNotExist)
                .orElseThrow();
    }

    private User saveIsNotExist(User user) {
        Optional<User> existUser = userRepo.findByUsername(user.getUsername());
        existUser.ifPresent(s -> {
            String errorSaveMessage = String.format("A user with login '%s' already exists", user.getUsername());
            throw new UserExistException(errorSaveMessage);
        });
        return userRepo.save(user);
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        eventPublisher.publishEvent(new EntityEvent(userDto, AccessType.UPDATE));
        return userRepo.findById(id)
                .map(user -> userCreateEditDtoMapper.map(userDto, user))
                .map(userRepo::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    eventPublisher.publishEvent(new EntityEvent(user, AccessType.DELETE));
                    userRepo.delete(user);
                    userRepo.flush();
                    return true;
                })
                .orElse(false);
    }

}