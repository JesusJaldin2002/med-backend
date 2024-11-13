package com.med.backend.controller;

import com.med.backend.dto.appointment.SaveAppointmentDTO;
import com.med.backend.dto.appointment.UpdateAppointmentDTO;
import com.med.backend.persistence.entity.Appointment;
import com.med.backend.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST')")
    @MutationMapping(name = "registerAppointment")
    public Appointment registerAppointment(@Argument("appointmentInput") @Valid SaveAppointmentDTO newAppointment) {
        return appointmentService.registerOneAppointment(newAppointment);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST')")
    @MutationMapping(name = "updateAppointment")
    public Appointment updateAppointment(@Argument("appointmentId") int appointmentId,
                                         @Argument("appointmentInput") @Valid UpdateAppointmentDTO updatedAppointment) {
        return appointmentService.updateAppointment(appointmentId, updatedAppointment);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST')")
    @MutationMapping(name = "deleteAppointment")
    public void deleteAppointment(@Argument("appointmentId") int appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST')")
    @QueryMapping(name = "getAllAppointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST','PATIENT')")
    @QueryMapping(name = "getAppointmentsByPatient")
    public List<Appointment> getAppointmentsByPatient(@Argument("patientId") int patientId) {
        return appointmentService.getAllByPatient(patientId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST', 'DOCTOR')")
    @QueryMapping(name = "getAppointmentsByDoctor")
    public List<Appointment> getAppointmentsByDoctor(@Argument("doctorId") int doctorId) {
        return appointmentService.getAllByDoctor(doctorId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'RECEPTIONIST', 'DOCTOR')")
    @QueryMapping(name = "getAppointmentById")
    public Appointment getAppointmentById(@Argument("appointmentId") int appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @MutationMapping(name = "updateAppointmentStatus")
    public Appointment updateAppointmentStatus(@Argument("appointmentId") int appointmentId,
                                              @Argument("status") String status) {
        return appointmentService.updateAppointmentStatus(appointmentId, status);
    }
}
