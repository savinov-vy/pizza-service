package ru.savinov.pizzaservice.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class InvoiceUser {

    String login;
    String pizzaName;
    LocalDate invoiceCreated;
    String deliveryStreet;

}