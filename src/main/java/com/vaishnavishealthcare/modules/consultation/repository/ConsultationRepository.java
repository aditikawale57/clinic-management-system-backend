package com.vaishnavishealthcare.modules.consultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaishnavishealthcare.modules.consultation.entity.Consultation;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}