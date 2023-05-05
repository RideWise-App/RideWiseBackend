package com.ridewise.backend.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@AllArgsConstructor
class SecurityConfig {

    @Value("${jwt-key}")
    static String secretKey;

    AuthenticationManager authenticationManager;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/auth/login");

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
                .anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
