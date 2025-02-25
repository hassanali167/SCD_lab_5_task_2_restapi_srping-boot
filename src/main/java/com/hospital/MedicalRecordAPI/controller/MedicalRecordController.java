package com.hospital.MedicalRecordAPI.controller;

import com.hospital.MedicalRecordAPI.model.MedicalRecord;
import com.hospital.MedicalRecordAPI.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("patientName") String patientName,
                                        @RequestParam("file") MultipartFile file) {
        try {
            MedicalRecord record = service.uploadRecord(patientName, file);
            return ResponseEntity.ok("File uploaded successfully: " + record.getFileName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MedicalRecord>> listAllFiles() {
        return ResponseEntity.ok(service.listAllRecords());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        Optional<MedicalRecord> record = service.getRecordById(id);
        if (record.isPresent()) {
            MedicalRecord file = record.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName())
                    .body(file.getFileData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok("File deleted successfully");
    }
}
