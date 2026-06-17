package com.vaishnavishealthcare.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PatientRequestDTO(
    @NotBlank(message = "Patient name cannot be empty")
    String name,

    @Positive(message = "Age must be a positive number")
    @Max(value = 150, message = "Age cannot be greater than 150")
    int age,

    @NotBlank(message = "Gender must be specified")
    String gender
) {}