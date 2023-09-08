package ru.savinov.pizzaservice.services;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.listener.entities.AccessType;
import ru.savinov.pizzaservice.listener.entities.EntityEvent;
import ru.savinov.pizzaservice.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final ApplicationEventPublisher eventPublisher;

    public void createNewUser(User user) {
        eventPublisher.publishEvent(new EntityEvent(user, AccessType.CREATE));
        userRepo.save(user);
    }

}