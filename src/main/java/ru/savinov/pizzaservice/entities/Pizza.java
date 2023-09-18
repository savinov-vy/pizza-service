package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savinov.pizzaservice.controllers.dto.PizzaDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "pizza")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = "createdAt")
public class Pizza implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    @ManyToOne
    @JoinColumn(name = "pizza_order_id")
    PizzaOrder pizzaOrder;

    private Date createdAt = new Date();

    public static Pizza ofDto(PizzaDto dto) {
        return Pizza.builder()
                .id(dto.getId())
                .name(dto.getName())
                .ingredients(dto.getIngredients())
                .build();
    }

}