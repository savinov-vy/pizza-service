package ru.savinov.pizzaservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import ru.savinov.pizzaservice.entities.PizzaOrder;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.repositories.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")
public class OrderController {

    private final OrderRepository orderRepo;
    private final PizzaPageProps pizzaPageProps;
    private final Integer showPageNum;

    public OrderController(@Value("${pizza.show.page}") Integer showPageNum,
                           OrderRepository orderRepo,
                           PizzaPageProps pizzaPageProps) {
        this.orderRepo = orderRepo;
        this.pizzaPageProps = pizzaPageProps;
        this.showPageNum = showPageNum;
    }

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
        order.setUser(user);
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        int sizePage = pizzaPageProps.getSizePage();
        Pageable pageable = PageRequest.of(showPageNum, sizePage);
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        log.info("count orders in page: {}", sizePage);
        return "orderList";
    }

}