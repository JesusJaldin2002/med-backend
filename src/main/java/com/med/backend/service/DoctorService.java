package com.med.backend.service;

import java.util.List;

import com.med.backend.dto.doctor.UpdateDoctorDTO;
import com.med.backend.dto.doctor.saveDoctorDto;
import com.med.backend.dto.doctor.DoctorUserDTO;
import com.med.backend.dto.user.SaveUser;
import com.med.backend.persistence.entity.Doctor;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    Doctor registerOneDoctor(saveDoctorDto newDoctor, SaveUser newUser);
    List<DoctorUserDTO> getAllDoctors();
    Doctor updateDoctor(int doctorId, UpdateDoctorDTO updatedDoctorData, SaveUser updatedUserData);
    void deleteDoctor(int doctorId);
}
