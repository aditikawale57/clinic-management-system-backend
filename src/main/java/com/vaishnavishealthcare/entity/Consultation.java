package com.vaishnavishealthcare.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotNull(message = "Patient must be provided")
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Vitals (Optional)
    private String bloodPressure;
    private String pulse;
    private String spo2;
    private String rbsl;
    private String weight;

    // Doctor's Notes (Required)
    @NotBlank(message = "Complaints cannot be empty")
    private String complaints;

    @NotBlank(message = "Diagnosis cannot be empty")
    private String diagnosis;

    private String instructions;
    
    private LocalDate followUp;
}