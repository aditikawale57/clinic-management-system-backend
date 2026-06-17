package com.vaishnavishealthcare.config.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vaishnavishealthcare.common.dto.StatusCode;
import com.vaishnavishealthcare.common.exception.ApiException;
import com.vaishnavishealthcare.common.exception.ErrorType;
import com.vaishnavishealthcare.common.util.ApiResponseWriter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Authenticates requests carrying a {@code Authorization: Bearer <token>}
 * header by validating the token with {@link Jwt} and populating the Spring
 * Security context. Requests without a bearer token pass through untouched so
 * the security chain can decide whether the target endpoint is public.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final Jwt jwt;
    private final ApiResponseWriter responseWriter;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(BEARER_PREFIX.length()).trim();
        try {
            JwtPayload payload = jwt.validate(token);
            var authentication = new UsernamePasswordAuthenticationToken(
                    payload.getSub(), null, Collections.emptyList());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ApiException ex) {
            SecurityContextHolder.clearContext();
            StatusCode code = ex.getType() == ErrorType.ACCESS_TOKEN
                    ? StatusCode.INVALID_ACCESS_TOKEN
                    : StatusCode.FAILURE;
            responseWriter.write(response, HttpStatus.UNAUTHORIZED, code, ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
