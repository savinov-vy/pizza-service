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
public class OrderSizeProps {

    @Min(value=5, message="must be between 5 and 45")
    @Max(value=45, message="must be between 5 and 45")
    int sizePage;

}