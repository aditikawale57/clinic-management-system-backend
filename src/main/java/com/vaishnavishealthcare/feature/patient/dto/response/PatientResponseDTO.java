package com.vaishnavishealthcare.feature.patient.dto.response;

public record PatientResponseDTO(
    Long id,
    String name,
    int age,
    String gender
) {}