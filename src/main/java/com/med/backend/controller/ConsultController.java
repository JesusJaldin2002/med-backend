package com.med.backend.controller;

import com.med.backend.dto.consult.SaveConsultDTO;
import com.med.backend.dto.consult.UpdateConsultDTO;
import com.med.backend.persistence.entity.Consult;
import com.med.backend.service.ConsultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','DOCTOR')")
    @MutationMapping(name = "registerConsult")
    public Consult registerConsult(@Argument("consultInput") @Valid SaveConsultDTO newConsult) {
        return consultService.registerOneConsult(newConsult);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @QueryMapping(name = "getAllConsults")
    public List<Consult> getAllConsults() {
        return consultService.getAllConsults();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DOCTOR')")
    @MutationMapping(name = "updateConsult")
    public Consult updateConsult(@Argument("consultId") int consultId,
                                 @Argument("consultInput") @Valid UpdateConsultDTO updatedConsult) {
        return consultService.updateConsult(consultId, updatedConsult);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @MutationMapping(name = "deleteConsult")
    public void deleteConsult(@Argument("consultId") int consultId) {
        consultService.deleteConsult(consultId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DOCTOR')")
    @QueryMapping(name = "findConsultsByDoctor")
    public List<Consult> findConsultsByDoctor(@Argument("doctorId") int doctorId) {
        return consultService.findConsultsByDoctor(doctorId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DOCTOR', 'PATIENT')")
    @QueryMapping(name = "findConsultsByPatient")
    public List<Consult> findConsultsByPatient(@Argument("patientId") int patientId) {
        return consultService.findConsultsByPatient(patientId);
    }
}
