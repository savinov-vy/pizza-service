package ru.savinov.pizzaservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savinov.pizzaservice.controllers.dto.RegistrationFormDto;
import ru.savinov.pizzaservice.services.UserService;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationFormDto form) {
        userService.createNewUser(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}