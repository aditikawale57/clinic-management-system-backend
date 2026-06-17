package com.vaishnavishealthcare.service;

import com.vaishnavishealthcare.entity.Patient;
import java.util.List;

public interface PatientService {
    Patient registerPatient(Patient patient);
    List<Patient> getAllPatients();
}