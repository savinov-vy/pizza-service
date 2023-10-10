package ru.savinov.pizzaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.savinov.pizzaservice.audit.AuditorAwareImpl;


@Configuration
@EnableJpaAuditing
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

}