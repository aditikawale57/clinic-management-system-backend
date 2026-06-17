package com.vaishnavishealthcare.feature.patient.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.vaishnavishealthcare.feature.patient.entity.Patient;
import com.vaishnavishealthcare.feature.patient.repository.PatientRepository;
import com.vaishnavishealthcare.feature.patient.service.PatientService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}