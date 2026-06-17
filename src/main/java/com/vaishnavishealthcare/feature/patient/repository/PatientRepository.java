package com.vaishnavishealthcare.feature.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaishnavishealthcare.feature.patient.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}