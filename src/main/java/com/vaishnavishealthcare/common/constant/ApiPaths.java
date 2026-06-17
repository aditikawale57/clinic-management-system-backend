package com.vaishnavishealthcare.common.constant;

/**
 * Central definition of API path segments and version prefixes.
 *
 * <p>Feature controllers declare only their resource-relative path
 * (e.g. {@code "/patients"}); the {@code /api/v1} prefix is applied
 * automatically by {@link com.vaishnavishealthcare.config.WebConfig}.
 */
public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String API = "/api";

    public static final String V1 = "/v1";

    public static final String API_V1 = API + V1;
}
