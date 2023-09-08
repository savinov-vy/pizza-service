package ru.savinov.pizzaservice.listener.entities;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EntitiesListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'CREATE'")
    @Order(10)
    public void createEntity(EntityEvent entityEvent) {
        System.out.println("EntityEvent: " + entityEvent);
    }

}