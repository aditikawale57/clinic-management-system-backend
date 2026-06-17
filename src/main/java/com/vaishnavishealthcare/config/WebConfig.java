package com.vaishnavishealthcare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vaishnavishealthcare.common.constant.ApiPaths;

/**
 * Applies the versioned {@code /api/v1} prefix to every controller under the
 * {@code modules} package, so controllers only declare their resource-relative
 * path (the equivalent of the {@code routes/v1} router in the reference
 * service). Authentication is handled by Spring Security
 * ({@link com.vaishnavishealthcare.config.security.SecurityConfig}).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String MODULES_BASE_PACKAGE = "com.vaishnavishealthcare.modules";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                ApiPaths.API_V1,
                HandlerTypePredicate.forBasePackage(MODULES_BASE_PACKAGE));
    }
}
