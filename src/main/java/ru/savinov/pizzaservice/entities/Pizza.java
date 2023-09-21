package ru.savinov.pizzaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savinov.pizzaservice.controllers.dto.PizzaDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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
    @Size(min = 2, max = 64, message = "Name must be between 2 and 5 characters long")
    @Column(name = "name")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingredient_to_pizza", joinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "pizza_order_id")
    PizzaOrder pizzaOrder;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    public static Pizza ofDto(PizzaDto dto) {
        return Pizza.builder()
                .id(dto.getId())
                .name(dto.getName())
                .ingredients(dto.getIngredients())
                .build();
    }

}