package ru.savinov.pizzaservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savinov.pizzaservice.controllers.dto.UserCreateEditDto;
import ru.savinov.pizzaservice.services.CityService;
import ru.savinov.pizzaservice.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final CityService cityService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("cities", cityService.findAll());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Validated UserCreateEditDto form,
                                      Errors errors, Model model) {
        if (errors.hasErrors()) {
            List<String> allErrors = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errorMessages", allErrors);
            return "registration";
        }
        userService.create(form);
        return "redirect:/login";
    }

}