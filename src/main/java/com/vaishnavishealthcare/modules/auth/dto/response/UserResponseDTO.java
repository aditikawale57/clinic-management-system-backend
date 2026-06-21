package com.vaishnavishealthcare.modules.auth.dto.response;

import java.util.Set;

public record UserResponseDTO(
    Long id,
    String username,
    String email,
    Set<String> roles
) {}
