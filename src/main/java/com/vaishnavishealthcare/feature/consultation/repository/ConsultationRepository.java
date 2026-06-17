package com.vaishnavishealthcare.feature.consultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaishnavishealthcare.feature.consultation.entity.Consultation;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}