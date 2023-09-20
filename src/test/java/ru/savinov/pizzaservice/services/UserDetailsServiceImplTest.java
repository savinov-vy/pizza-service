package ru.savinov.pizzaservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.repositories.UserRepository;

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
        user = User.builder()
                .username("name_test")
                .password("12345")
                .fullname("fname")
                .street("street")
                .city(City.of("city"))
                .build();
    }

    @Test
    void loadUserByUsername() {
        doReturn(user).when(userRepo).findByUsername("name_test");

        UserDetails actualResult = subject.loadUserByUsername("name_test");

        assertTrue(nonNull(actualResult));
        assertEquals(user, actualResult);
    }

}