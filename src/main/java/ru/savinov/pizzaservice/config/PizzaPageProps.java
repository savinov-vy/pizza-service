package ru.savinov.pizzaservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "pizza.orders")
public class PizzaPageProps {

    @Min(value=2, message="must be between 2 and 4")
    @Max(value=4, message="must be between 2 and 4")
    int sizePage;

}