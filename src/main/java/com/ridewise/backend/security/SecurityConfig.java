package com.ridewise.backend.security;

import com.ridewise.backend.serviceImpl.ClientService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@AllArgsConstructor
class SecurityConfig {

    static String secretKey = Dotenv.load().get("SECRET_KEY");

    AuthenticationManager authenticationManager;
    final ClientService clientService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, clientService);
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
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/class").permitAll()
                .antMatchers("/map/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
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
