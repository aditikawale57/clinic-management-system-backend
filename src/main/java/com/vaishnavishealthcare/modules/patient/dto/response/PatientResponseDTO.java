package com.vaishnavishealthcare.modules.patient.dto.response;

public record PatientResponseDTO(
    Long id,
    String name,
    int age,
    String gender
) {}