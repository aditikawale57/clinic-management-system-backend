package com.vaishnavishealthcare.modules.treatment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaishnavishealthcare.modules.treatment.entity.Treatment;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}