package com.vaishnavishealthcare.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.vaishnavishealthcare.common.dto.ApiResponse;
import com.vaishnavishealthcare.common.dto.StatusCode;

import tools.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Writes a standard {@link ApiResponse} envelope directly to the servlet
 * response. Used by Spring Security components (the JWT filter, the
 * authentication entry point and the access-denied handler) which run before
 * the {@code DispatcherServlet} and therefore cannot rely on the
 * {@link com.vaishnavishealthcare.common.exception.GlobalExceptionHandler}.
 */
@Component
@RequiredArgsConstructor
public class ApiResponseWriter {

    private final ObjectMapper objectMapper;

    public void write(HttpServletResponse response, HttpStatus status, StatusCode statusCode, String message)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), new ApiResponse<Void>(statusCode, message, null));
    }
}
