package com.vaishnavishealthcare.modules.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Set;

import com.vaishnavishealthcare.modules.auth.entity.ERole;

public record UserRequestDTO(
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username,

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be a valid email address")
    @Size(max = 50, message = "Email must be less than 50 characters")
    String email,

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String password,

    @NotEmpty(message = "Roles cannot be empty")
    Set<ERole> roles
) {}