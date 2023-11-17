package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.controllers.dto.PizzaOrderReadDto;
import ru.savinov.pizzaservice.entities.PizzaOrder;
import ru.savinov.pizzaservice.entities.User;
import ru.savinov.pizzaservice.mapper.PizzaOrderReadDtoMapper;
import ru.savinov.pizzaservice.repositories.OrderRepository;
import ru.savinov.pizzaservice.status.PizzaStatus;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PizzaOrderService {

    private final PizzaOrderReadDtoMapper pizzaOrderReadDtoMapper;
    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    @Transactional
    public void prepareAndCreate(PizzaOrder order, User user) {
        prepare(order, user);
        log.info("user with id = {} create order", user.getId());
        entityManager.merge(order);
    }

    private void prepare(PizzaOrder order, User user) {
        order.setUser(user);
        order.getPizzas().forEach(pizza -> {
            pizza.setPizzaOrder(order);
            pizza.setStatus(PizzaStatus.PROCESS);
        });
    }

    public List<PizzaOrderReadDto> findBy(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable).stream()
                .map(pizzaOrderReadDtoMapper::map)
                .collect(Collectors.toList());
    }

}