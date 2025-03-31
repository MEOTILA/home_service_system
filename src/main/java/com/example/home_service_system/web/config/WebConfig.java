package com.example.home_service_system.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:63342") // Adjust if necessary
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
