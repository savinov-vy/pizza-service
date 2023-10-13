package ru.savinov.pizzaservice.integration;

import lombok.RequiredArgsConstructor;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("ов", "admin");
        ListAssert<User> userListAssert = assertThat(users);
        userListAssert.hasSize(1);
    }

    @Test
    @Transactional
    void checkUpdate() {
        var ivan = userRepository.getById(4L);
        assertSame(Role.USER, ivan.getRole());

        var resultCount = userRepository.updateRole(Role.ADMIN, 4L, 5L);

        var theSameIvan = userRepository.getById(4L);
        assertSame(Role.ADMIN, theSameIvan.getRole());
    }

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(1, 2, Sort.by("id"));
        var result = userRepository.findAllBy(pageable);
        assertThat(result).hasSize(2);
    }

    @Test
    void checkSort() {
        var sortBy = Sort.sort(User.class);
        var sort = sortBy.by(User::getFullname)
                .and(sortBy.by(User::getUsername));

        var allUsers = userRepository.findTop3ByFullnameContaining("ов", sort);
        assertThat(allUsers).hasSize(3);
    }

}