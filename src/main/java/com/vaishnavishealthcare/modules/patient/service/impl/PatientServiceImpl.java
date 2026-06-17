package com.vaishnavishealthcare.modules.patient.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.vaishnavishealthcare.modules.patient.entity.Patient;
import com.vaishnavishealthcare.modules.patient.repository.PatientRepository;
import com.vaishnavishealthcare.modules.patient.service.PatientService;

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