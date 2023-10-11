package ru.savinov.pizzaservice.repositories;

import ru.savinov.pizzaservice.repositories.dto.InvoiceUser;

import java.util.List;

public interface FilterInvoiceRepository {

    List<InvoiceUser> findAllByUser(Long userId);

}