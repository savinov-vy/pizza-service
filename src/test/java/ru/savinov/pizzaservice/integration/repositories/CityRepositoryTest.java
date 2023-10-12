package ru.savinov.pizzaservice.integration.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.entities.City;
import ru.savinov.pizzaservice.integration.annotation.IT;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@IT
@RequiredArgsConstructor
class CityRepositoryTest {

    private final EntityManager entityManager;

    @Test
    @Transactional
    void findById() {
        var city = entityManager.find(City.class, 1);
        assertNotNull(city);
        assertThat(city.getLocales()).hasSize(2);
    }

}