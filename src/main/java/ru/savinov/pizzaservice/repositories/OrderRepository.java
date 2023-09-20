package ru.savinov.pizzaservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.savinov.pizzaservice.entities.PizzaOrder;
import ru.savinov.pizzaservice.entities.User;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {

    List<PizzaOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    @Query("select o from PizzaOrder o where o.deliveryStreet = :deliveryStreet")
    List<PizzaOrder> readOrdersByDeliveryStreet(@Param("deliveryStreet") String deliveryStreet);

}