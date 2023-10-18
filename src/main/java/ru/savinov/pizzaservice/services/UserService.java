package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.savinov.pizzaservice.controllers.dto.UserReadDto;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.audit.listener.entities.AccessType;
import ru.savinov.pizzaservice.audit.listener.entities.EntityEvent;
import ru.savinov.pizzaservice.mapper.UserReadMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final UserReadMapper userReadMapper;

    public void createNewUser(User user) {
        eventPublisher.publishEvent(new EntityEvent(user, AccessType.CREATE));
        userRepo.save(user);
    }

    public List<UserReadDto> findAll() {
        return userRepo.findAll().stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

}