package com.vaishnavishealthcare.modules.patient.service;

import java.util.List;

import com.vaishnavishealthcare.modules.patient.entity.Patient;

public interface PatientService {
    Patient registerPatient(Patient patient);
    List<Patient> getAllPatients();
}