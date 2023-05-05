package com.ridewise.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
class SecurityConfig {

    @Value("${jwt-key}")
    static String secretKey;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                configuration.setAllowedMethods(Arrays.asList("GET", "DELETE", "POST", "PUT"));
                configuration.setMaxAge(3600L);
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                        "x-auth-token", "Authorization"));
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                return configuration;
            }
        }).and().csrf().disable().authorizeHttpRequests()
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
