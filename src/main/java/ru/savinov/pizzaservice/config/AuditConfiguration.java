package ru.savinov.pizzaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;


@Configuration
@EnableJpaAuditing
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = Optional.ofNullable(authentication).map(Principal::getName).orElse("unknown");
        return () -> Optional.of(userName);
    }

}