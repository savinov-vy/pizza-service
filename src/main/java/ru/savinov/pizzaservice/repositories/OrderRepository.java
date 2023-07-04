package ru.savinov.pizzaservice.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.savinov.pizzaservice.entities.PizzaOrder;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {

    List<PizzaOrder> findByDeliveryZip(String deliveryZip);

    List<PizzaOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    List<PizzaOrder> findByDeliveryStreetAndDeliveryCityIgnoreCase(String deliveryStreet, String deliveryCity);

    List<PizzaOrder> findByDeliveryCityOrderByDeliveryName(String city);

    @Query("select o from PizzaOrder o where o.deliveryCity = :city")
    List<PizzaOrder> readOrdersDeliveredInSeattle(@Param("city") String city);

}