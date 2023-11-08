package ru.savinov.pizzaservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.savinov.pizzaservice.config.PizzaPageProps;
import ru.savinov.pizzaservice.controllers.dto.PizzaOrderReadDto;
import ru.savinov.pizzaservice.entities.PizzaOrder;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.services.PizzaOrderService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")
@RequiredArgsConstructor
public class OrderController {

    private final PizzaPageProps pizzaPageProps;
    private final PizzaOrderService orderService;

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
        orderService.prepareAndCreate(order, user);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        int sizePage = pizzaPageProps.getSizePage();
        Pageable pageable = PageRequest.of(0, sizePage);
        List<PizzaOrderReadDto> orders = orderService.findBy(user.getId(), pageable);
        model.addAttribute("orders", orders);
        log.info("count orders in page: {}", sizePage);
        return "orderList";
    }

}