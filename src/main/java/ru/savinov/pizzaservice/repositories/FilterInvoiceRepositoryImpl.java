package ru.savinov.pizzaservice.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.savinov.pizzaservice.repositories.dto.InvoiceUser;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FilterInvoiceRepositoryImpl implements FilterInvoiceRepository {

    private static final String FIND_BY_USER_ID = "" +
            "SELECT u.username login, p.name pizzaName, " +
            "po.created_at invoiceCreated, po.delivery_street deliveryStreet " +
            "FROM pizza_order po " +
            "JOIN pizza p ON po.id = p.pizza_order_id " +
            "JOIN users u ON po.user_id = u.id " +
            "WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<InvoiceUser> findAllByUser(Long userId) {
        return jdbcTemplate.query(FIND_BY_USER_ID,
                (rs, i) -> new InvoiceUser(
                rs.getString("login"),
                rs.getString("pizzaName"),
                rs.getDate("invoiceCreated").toLocalDate(),
                rs.getString("deliveryStreet")
        ), userId);
    }

}