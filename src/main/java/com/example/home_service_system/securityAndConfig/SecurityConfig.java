package com.example.home_service_system.securityAndConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomUserDetailsService userDetailsService
    )
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/customerSignup.html",
                                "/expertSignup.html",
                                "/reCaptchaTest.html",
                                "/login.html",
                                "/register/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/auth/**",
                                "/perform_login",
                                "/verify"
                        ).permitAll()
                        .requestMatchers("/v1/admin/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/users/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/expert/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/customer/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/main-services/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/sub-services/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/orders/**").hasAnyRole("ADMIN", "CUSTOMER", "EXPERT")
                        .requestMatchers("/v1/expert-suggestions/**").hasAnyRole("EXPERT","CUSTOMER", "ADMIN")
                        .requestMatchers("/v1/customer-comments/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}