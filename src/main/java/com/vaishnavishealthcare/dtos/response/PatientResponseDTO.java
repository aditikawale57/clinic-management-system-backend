package com.vaishnavishealthcare.dtos.response;

public record PatientResponseDTO(
    Long id,
    String name,
    int age,
    String gender
) {}