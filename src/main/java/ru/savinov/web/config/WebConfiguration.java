package ru.savinov.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!qa")
@Configuration
public class WebConfiguration {
}