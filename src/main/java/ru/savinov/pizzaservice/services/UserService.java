package ru.savinov.pizzaservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public void createNewUser(User user) {
        userRepo.save(user);
    }

}