package com.ridewise.backend.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SwaggerApiConfig {


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ridewise-public")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI rideWiseOpenApi() {
        return new OpenAPI()
                .info(new Info().title("RideWise API")
                .description("RideWise backend application")
                .version("v0.0.1"));
    }
}
