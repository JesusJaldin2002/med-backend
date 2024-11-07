package com.med.backend.persistence.repository;

import com.med.backend.persistence.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, Integer> {
}
