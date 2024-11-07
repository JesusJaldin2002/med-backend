package com.med.backend.persistence.repository;

import com.med.backend.persistence.entity.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedule,Integer> {
}
