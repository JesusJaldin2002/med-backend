package com.med.backend.persistence.repository;

import com.med.backend.persistence.entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient,Integer> {

    Optional<Object> findByPhone(String phone);

}
