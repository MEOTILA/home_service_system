package com.example.home_service_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup/**", "/login").permitAll()
                        .requestMatchers("/v1/expert/**").hasAnyRole("Customer","EXPERT","ADMIN")
                        .requestMatchers("/v1/customer/**").hasAnyRole("CUSTOMER","EXPERT","ADMIN")
                        .requestMatchers("/v1/main-services/**").hasAnyRole("CUSTOMER","EXPERT","ADMIN")
                        .requestMatchers("/v1/sub-services/**").hasAnyRole("CUSTOMER","EXPERT","ADMIN")
                        .requestMatchers("/v1/orders/**").hasAnyRole("CUSTOMER","EXPERT")
                        .requestMatchers("/v1/expert-suggestions/**").hasAnyRole("CUSTOMER","ADMIN")
                        .requestMatchers("/v1/customer-comments/**").hasAnyRole("CUSTOMER","EXPERT","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin().disable()
                .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}