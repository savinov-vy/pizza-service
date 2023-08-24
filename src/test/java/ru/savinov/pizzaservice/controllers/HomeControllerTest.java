package ru.savinov.pizzaservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.savinov.pizzaservice.config.PizzaPageProps;
import ru.savinov.pizzaservice.repositories.IngredientRepository;
import ru.savinov.pizzaservice.repositories.OrderRepository;
import ru.savinov.pizzaservice.repositories.PizzaRepository;
import ru.savinov.pizzaservice.repositories.UserRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;
    @MockBean
    PizzaPageProps pizzaPageProps;
    @MockBean
    PizzaRepository pizzaRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    RegistrationController registrationController;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "customUserName")
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(
                        containsString("Welcome to...")));
    }

}
