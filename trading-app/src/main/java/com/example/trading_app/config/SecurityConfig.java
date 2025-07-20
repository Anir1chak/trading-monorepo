package com.example.trading_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity in development. Enable it as needed.
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permit unauthenticated access to these endpoints:
                .requestMatchers("/api/signup", "/api/login", "/api/market/**", "/api/analytics/**").permitAll()
                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            // Use HTTP Basic authentication for endpoints that require auth (if needed)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
