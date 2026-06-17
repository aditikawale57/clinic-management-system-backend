package com.vaishnavishealthcare.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.vaishnavishealthcare.common.dto.StatusCode;
import com.vaishnavishealthcare.common.util.ApiResponseWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Returns the standard error envelope with HTTP 403 when an authenticated user
 * lacks permission for the requested endpoint.
 */
@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ApiResponseWriter responseWriter;

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        responseWriter.write(
                response, HttpStatus.FORBIDDEN, StatusCode.FAILURE, "Permission denied");
    }
}
