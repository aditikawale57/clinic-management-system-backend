package com.vaishnavishealthcare.common.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaishnavishealthcare.common.constant.ApiPaths;

/**
 * Lightweight liveness endpoint. Lives outside the {@code modules} package, so
 * it declares the {@code /api/v1} version prefix explicitly via {@link ApiPaths}
 * rather than relying on the automatic prefix from
 * {@link com.vaishnavishealthcare.config.WebConfig}.
 */
@RestController
@RequestMapping(ApiPaths.API_V1 + "/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "version", "v1",
                "timestamp", Instant.now().toString()));
    }
}
