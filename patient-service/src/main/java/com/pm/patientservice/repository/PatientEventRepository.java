package com.pm.patientservice.repository;

import com.pm.patientservice.model.PatientEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientEventRepository  extends JpaRepository<PatientEvent, UUID> {
}
