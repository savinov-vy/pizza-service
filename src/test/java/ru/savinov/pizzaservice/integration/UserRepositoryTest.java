package ru.savinov.pizzaservice.integration;

import lombok.RequiredArgsConstructor;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.Role;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.mapper.UserCreateEditDtoMapper;
import ru.savinov.pizzaservice.repositories.UserRepository;
import ru.savinov.test_helpers.factories.UserCreateEditDtoFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;
    private final UserCreateEditDtoMapper userCreateEditDtoMapper;
    private Long userId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserCreateEditDtoFactory.listOf5Items().stream()
                .map(userCreateEditDtoMapper::map)
                .map(userRepository::saveAndFlush)
                .map(userRepository::saveAndFlush)
                .forEach(user -> userId = user.getId());
    }

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("erTe", "st2");
        ListAssert<User> userListAssert = assertThat(users);
        userListAssert.hasSize(1);
    }

    @Test
    @Transactional
    void checkUpdate() {
        var user = userRepository.getById(userId);
        assertSame(Role.USER, user.getRole());

        var resultCount = userRepository.updateRole(Role.ADMIN, userId, userId-1);

        assertEquals(resultCount, 2);
        user = userRepository.getById(userId);
        assertSame(Role.ADMIN, user.getRole());
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

        var allUsers = userRepository.findTop3ByFullnameContaining("ullNam", sort);
        assertThat(allUsers).hasSize(3);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}