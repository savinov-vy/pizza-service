package ru.savinov.pizzaservice.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.integration.IntegrationTestBase;
import ru.savinov.pizzaservice.repositories.UserRepository;
import ru.savinov.test_helpers.factories.UserCreateEditDtoFactory;

import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private UserCreateEditDto userDto;
    private final UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userDto = UserCreateEditDtoFactory.of();
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(8)));
    }

    @Test
    @Transactional
    void create() throws Exception {
        User isNotSaved = userRepository.findByUsername(userDto.getUsername());
        assertTrue(Optional.ofNullable(isNotSaved).isEmpty());

        ResultActions result = mockMvc.perform(post("/users").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
                result.andExpect(status().is3xxRedirection());

        User isSaved = userRepository.findByUsername(userDto.getUsername());
        assertTrue(Optional.ofNullable(isSaved).isPresent());
    }

}