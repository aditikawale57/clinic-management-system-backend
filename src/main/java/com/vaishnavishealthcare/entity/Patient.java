package com.vaishnavishealthcare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Patient name cannot be empty")
    private String name;

    @Positive(message = "Age must be a positive number")
    @Max(value = 150, message = "Age cannot be greater than 150")
    private int age;

    @NotBlank(message = "Gender must be specified")
    private String gender;

}