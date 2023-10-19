package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.audit.listener.entities.AccessType;
import ru.savinov.pizzaservice.audit.listener.entities.EntityEvent;
import ru.savinov.pizzaservice.mapper.UserCreateEditMapper;
import ru.savinov.pizzaservice.mapper.UserReadMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    public List<UserReadDto> findAll() {
        return userRepo.findAll().stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepo.findById(id)
                .map(userReadMapper::map);
    }

    public UserReadDto create(UserCreateEditDto userDto) {
        eventPublisher.publishEvent(new EntityEvent(userDto, AccessType.CREATE));
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepo::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

}