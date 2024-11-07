package com.med.backend.persistence.repository;

import com.med.backend.persistence.entity.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, Integer> {
}
