package ru.savinov.pizzaservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.savinov.web.config.WebConfiguration;

@Import(WebConfiguration.class)
@Configuration
@Profile("dev")
@PropertySource("classpath:application-dev.yml")
@ComponentScan(basePackages = "ru.savinov.pizzaservice",
               useDefaultFilters = false,
               includeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = Component.class),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
        @Filter(type = FilterType.REGEX, pattern = "ru\\..+Repository")
})
public class ApplicationConfig {
}
