package com.med.backend.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "appointments") // Define la colección como "appointments" en MongoDB
public class Appointment {

    @Id
    private int id;

    private LocalDateTime date;
    private String status;
    private String reason;

    // Llaves foráneas para las relaciones
    private int patientId;
    private int doctorId;

    // Constructor sin argumentos
    public Appointment() {}

    // Constructor con argumentos
    public Appointment(int id, LocalDateTime date, String status, String reason, int patientId, int doctorId) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.reason = reason;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
