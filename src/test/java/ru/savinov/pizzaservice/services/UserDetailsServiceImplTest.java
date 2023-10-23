package ru.savinov.pizzaservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import ru.savinov.pizzaservice.repositories.UserRepository;
import ru.savinov.test_helpers.factories.UserFactory;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepo;
    @InjectMocks
    private UserDetailsServiceImpl subject;

    private UserDetails user;

    @BeforeEach
    void setUp() {
        user = UserFactory.of();
    }

    @Test
    void loadUserByUsername() {
        String username = user.getUsername();

        doReturn(user).when(userRepo).findByUsername(username);

        UserDetails actualResult = subject.loadUserByUsername(username);
        assertTrue(nonNull(actualResult));
        assertEquals(user, actualResult);
    }

}