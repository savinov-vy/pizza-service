package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "pizza_order")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PizzaOrder implements BaseEntity<Long> {

    private static final long serialVersionUID = -332167508885431556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Street is required")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String deliveryStreet;

    @CreditCardNumber(message = "Not valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|[0-2])([\\/])([2-9][0-9])$",
             message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCVV;

    private Date placedAt = new Date();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pizzaOrder")
    private List<Pizza> pizzas = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

}