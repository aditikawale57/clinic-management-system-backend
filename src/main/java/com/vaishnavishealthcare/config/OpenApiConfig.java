package com.vaishnavishealthcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenAPI (Swagger) documentation configuration. Exposes a JWT bearer security
 * scheme so the "Authorize" button in Swagger UI can attach a token to
 * requests. Swagger UI is served at {@code /swagger-ui.html} and the raw spec
 * at {@code /v3/api-docs}.
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI vaishnavisHealthcareOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vaishnavi's Healthcare API")
                        .version("v1")
                        .description("API documentation for the Vaishnavi's Healthcare backend.")
                        .contact(new Contact().name("API Support")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter your JWT access token")));
    }
}
