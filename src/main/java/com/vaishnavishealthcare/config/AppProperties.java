package com.vaishnavishealthcare.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Type-safe application configuration, the Spring equivalent of the reference
 * service's {@code config/index} ({@code Config}) env object. Values are bound
 * from {@code application.properties} under the {@code app} prefix (which can
 * be overridden by environment variables, e.g. {@code APP_JWT_SECRET}).
 */
@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    /** Runtime environment: {@code development}, {@code test} or {@code production}. */
    private String env = "development";

    private final Jwt jwt = new Jwt();

    public boolean isProduction() {
        return "production".equalsIgnoreCase(env);
    }

    @Getter
    @Setter
    public static class Jwt {

        private String secret = "change-this-secret-in-production-minimum-32-chars";
        private String issuer = "vaishnavis-healthcare";
        private String audience = "vaishnavis-healthcare-client";
        private long validitySeconds = 3600;
    }
}
