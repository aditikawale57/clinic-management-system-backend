package com.vaishnavishealthcare.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.vaishnavishealthcare.common.dto.StatusCode;
import com.vaishnavishealthcare.common.util.ApiResponseWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Returns the standard error envelope with HTTP 401 when an unauthenticated
 * request hits a protected endpoint.
 */
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApiResponseWriter responseWriter;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        responseWriter.write(
                response, HttpStatus.UNAUTHORIZED, StatusCode.FAILURE, "Authentication required");
    }
}
