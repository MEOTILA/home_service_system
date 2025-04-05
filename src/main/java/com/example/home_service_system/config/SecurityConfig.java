package com.example.home_service_system.config;

import com.example.home_service_system.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                                "/login.html",
                                "/register/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/auth/**",
                                "/perform_login"  // Allow access to login endpoint
                        ).permitAll()
                        .requestMatchers("/v1/expert/**").hasAnyRole("Customer", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/customer/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/main-services/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/sub-services/**").hasAnyRole("CUSTOMER", "EXPERT", "ADMIN")
                        .requestMatchers("/v1/orders/**").hasAnyRole("CUSTOMER", "EXPERT")
                        .requestMatchers("/v1/expert-suggestions/**").hasAnyRole("CUSTOMER", "ADMIN")
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