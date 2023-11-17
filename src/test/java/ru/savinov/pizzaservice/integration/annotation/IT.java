package ru.savinov.pizzaservice.integration.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@WithMockUser(username = "testUser", authorities = {"ADMIN", "USER"})
@SpringBootTest
public @interface IT {
}