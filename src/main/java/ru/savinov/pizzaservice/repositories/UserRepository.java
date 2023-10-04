package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.savinov.pizzaservice.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select u from User u " +
            "where u.fullname like %:fullname% and u.username like %:username%")
    List<User> findAllBy(String fullname, String username);

}