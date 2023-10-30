package ru.savinov.pizzaservice.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers().frameOptions().disable()

                .and()
                .authorizeRequests()
                .mvcMatchers("/design", "/orders").access("hasRole('USER')")
                .antMatchers("/", "/**").access("permitAll")

                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/register/login-error")
                .defaultSuccessUrl("/design")
                .loginProcessingUrl("/authenticateTheUser")

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .build();
    }
}