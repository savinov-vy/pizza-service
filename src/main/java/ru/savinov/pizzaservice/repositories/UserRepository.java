package ru.savinov.pizzaservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.savinov.pizzaservice.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}