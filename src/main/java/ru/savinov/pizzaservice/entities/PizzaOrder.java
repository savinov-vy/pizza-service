package ru.savinov.pizzaservice.entities;

import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Setter
@Table("PIZZA_ORDER")
public class PizzaOrder {

    private static final long serialVersionUID = -332167508885431556L;

    @Id
    private Long id;

    @Column("DELIVERY_NAME")
    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @Column("DELIVERY_STREET")
    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @Column("DELIVERY_CITY")
    @NotBlank(message = "City is required")
    private String deliveryCity;

    @Column("DELIVERY_STATE")
    @NotBlank(message = "State is required")
    private String deliveryState;

    @Column("DELIVERY_ZIP")
    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @Column("CC_NUMBER")
    @CreditCardNumber(message = "Not valid credit card number")
    private String ccNumber;

    @Column("CC_EXPIRATION")
    @Pattern(regexp = "^(0[1-9]|[0-2])([\\/])([2-9][0-9])$",
             message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Column("CC_CVV")
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @Column("PLACED_AT")
    private Date placedAt = new Date();

    private List<Pizza> pizzas = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

}