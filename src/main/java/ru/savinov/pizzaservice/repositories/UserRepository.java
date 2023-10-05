package ru.savinov.pizzaservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select u from User u " +
            "where u.fullname like %:fullname% and u.username like %:username%")
    List<User> findAllBy(String fullname, String username);

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
            nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    List<User> findTop3ByFullnameContaining(String fullname, Sort sort);

    @Query(value = "select u from User u",
            countQuery = "select count(distinct u.fullname) from User u")
    List<User> findAllBy(Pageable pageable);

}