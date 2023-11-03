package ru.savinov.pizzaservice.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ru.savinov.pizzaservice.entities.Role.ADMIN;
import static ru.savinov.pizzaservice.entities.Role.USER;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/register", "/design").permitAll()
                        .antMatchers("/orders").hasAuthority(USER.getAuthority())
                        .antMatchers("/users").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/login").loginProcessingUrl("/authenticateTheUser")
                        .failureUrl("/register/login-error")
                        .defaultSuccessUrl("/design")
                        .loginProcessingUrl("/authenticateTheUser"))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))

                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/h2-console/**"));
    }

}