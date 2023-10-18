package ru.savinov.pizzaservice.mapper;

public interface Mapper<F, T> {

    T map(F object);

}