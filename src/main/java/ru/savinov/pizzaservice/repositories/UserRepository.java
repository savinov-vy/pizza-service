package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savinov.pizzaservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}