package ru.savinov.pizzaservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savinov.pizzaservice.controllers.dto.RegistrationFormDto;
import ru.savinov.pizzaservice.services.CityService;
import ru.savinov.pizzaservice.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static liquibase.repackaged.org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CityService cityService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("cities", cityService.findAll());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationFormDto form, Errors errors, Model model) {
        if (isNotEmpty(form.getPassword()) && !form.getPassword().equals(form.getConfirmPassword()) ) {
            errors.reject("password", "Password and confirm password is not equals");
        }
        if (errors.hasErrors()) {
            List<String> allErrors = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("allErrors", allErrors);
            return "registration";
        }
        userService.createNewUser(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}