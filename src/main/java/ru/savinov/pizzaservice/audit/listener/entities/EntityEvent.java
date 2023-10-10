package ru.savinov.pizzaservice.audit.listener.entities;

import org.springframework.context.ApplicationEvent;

public class EntityEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1075310852118506732L;

    private final AccessType accessType;

    public EntityEvent(Object entity, AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }

    public AccessType getAccessType() {
        return accessType;
    }

}