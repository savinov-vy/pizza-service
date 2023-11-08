package ru.savinov.pizzaservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.controllers.dto.PizzaDto;
import ru.savinov.pizzaservice.controllers.dto.PizzaOrderReadDto;
import ru.savinov.pizzaservice.entities.PizzaOrder;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PizzaOrderReadMapper implements Mapper<PizzaOrder, PizzaOrderReadDto> {

    private final PizzaDtoMapper pizzaDtoMapper;

    @Override
    public PizzaOrderReadDto map(PizzaOrder object) {
        return PizzaOrderReadDto.builder()
                .placedAt(object.getPlacedAt())
                .deliveryStreet(object.getDeliveryStreet())
                .pizzasDto(toPizzaDtoList(object))
                .build();
    }

    private List<PizzaDto> toPizzaDtoList(PizzaOrder order) {
        return order.getPizzas().stream()
                .map(pizzaDtoMapper::map)
                .collect(Collectors.toList());
    }

}