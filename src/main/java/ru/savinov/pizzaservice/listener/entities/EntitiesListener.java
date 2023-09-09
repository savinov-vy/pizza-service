package ru.savinov.pizzaservice.listener.entities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EntitiesListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'CREATE'")
    @Order(10)
    public void createEntity(EntityEvent entityEvent) {
        log.info("EntityEvent: " + entityEvent);
    }

}