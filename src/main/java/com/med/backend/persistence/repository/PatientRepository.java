package com.med.backend.persistence.repository;

import com.med.backend.persistence.entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient,Integer> {
}
