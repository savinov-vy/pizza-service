package ru.savinov.pizzaservice.controllers.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PizzaOrderReadDto {

    private String deliveryStreet;
    private final Date placedAt;
    private List<PizzaDto> pizzasDto;

}