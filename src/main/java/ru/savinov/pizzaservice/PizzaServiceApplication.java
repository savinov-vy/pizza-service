package ru.savinov.pizzaservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.savinov.pizzaservice.controllers.OrderController;

@Slf4j
@SpringBootApplication
public class PizzaServiceApplication {

    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PizzaServiceApplication.class, args);
		OrderController orderController = context.getBean("orderController", OrderController.class);
		log.info("get bean from context: " + orderController);
	}
}
