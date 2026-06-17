package com.vaishnavishealthcare.feature.patient.service;

import java.util.List;

import com.vaishnavishealthcare.feature.patient.entity.Patient;

public interface PatientService {
    Patient registerPatient(Patient patient);
    List<Patient> getAllPatients();
}