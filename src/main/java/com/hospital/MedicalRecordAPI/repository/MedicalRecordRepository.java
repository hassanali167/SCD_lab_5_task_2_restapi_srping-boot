package com.hospital.MedicalRecordAPI.repository;

import com.hospital.MedicalRecordAPI.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
