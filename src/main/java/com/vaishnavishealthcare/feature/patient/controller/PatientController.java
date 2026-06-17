package com.vaishnavishealthcare.feature.patient.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vaishnavishealthcare.feature.patient.dto.request.PatientRequestDTO;
import com.vaishnavishealthcare.feature.patient.dto.response.PatientResponseDTO;
import com.vaishnavishealthcare.feature.patient.entity.Patient;
import com.vaishnavishealthcare.feature.patient.service.PatientService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> registerPatient(@Valid @RequestBody PatientRequestDTO requestDTO) {
        // 1. Convert Request DTO  to Database Entity
        Patient patient = new Patient();
        patient.setName(requestDTO.name()); 
        patient.setAge(requestDTO.age());
        patient.setGender(requestDTO.gender());

        // 2. Save the Entity
        Patient savedPatient = patientService.registerPatient(patient);

        // 3. Convert saved Entity back to Response DTO 
        return ResponseEntity.ok(convertToDTO(savedPatient));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        // Convert the list of Entities to a list of Response DTOs
        List<PatientResponseDTO> responseList = patientService.getAllPatients()
                .stream()
                .map(this::convertToDTO) 
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responseList);
    }
    
   
    private PatientResponseDTO convertToDTO(Patient patient) {
        return new PatientResponseDTO(
            patient.getId(),
            patient.getName(),
            patient.getAge(),
            patient.getGender()
        );
    }
}