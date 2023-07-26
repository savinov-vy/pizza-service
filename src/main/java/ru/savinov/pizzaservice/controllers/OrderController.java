package ru.savinov.pizzaservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.savinov.pizzaservice.entities.PizzaOrder;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.repositories.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")
@AllArgsConstructor
public class OrderController {

    private OrderRepository orderRepo;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid PizzaOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info(user.getFullname());
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

}