package com.hospital.MedicalRecordAPI.service;

import com.hospital.MedicalRecordAPI.model.MedicalRecord;
import com.hospital.MedicalRecordAPI.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository repository;

    public MedicalRecord uploadRecord(String patientName, MultipartFile file) throws IOException {
        MedicalRecord record = MedicalRecord.builder()
                .patientName(patientName)
                .fileName(file.getOriginalFilename())
                .fileData(file.getBytes())
                .uploadDate(LocalDateTime.now())
                .build();
        return repository.save(record);
    }

    public List<MedicalRecord> listAllRecords() {
        return repository.findAll();
    }

    public Optional<MedicalRecord> getRecordById(Long id) {
        return repository.findById(id);
    }

    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }
}
