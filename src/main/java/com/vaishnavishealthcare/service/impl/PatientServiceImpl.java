package com.vaishnavishealthcare.service.impl;

import com.vaishnavishealthcare.entity.Patient;
import com.vaishnavishealthcare.repository.PatientRepository;
import com.vaishnavishealthcare.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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