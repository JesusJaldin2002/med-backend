package com.med.backend.persistence.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMINISTRATOR(Arrays.asList(
            RolePermission.READ_MY_PROFILE,
            RolePermission.FIND_ALL_USERS,

            RolePermission.FIND_ALL_DOCTORS,
            RolePermission.CREATE_ONE_DOCTOR,
            RolePermission.UPDATE_ONE_DOCTOR,
            RolePermission.DELETE_ONE_DOCTOR,

            RolePermission.CREATE_ONE_RECEPTIONIST,
            RolePermission.UPDATE_ONE_RECEPTIONIST,
            RolePermission.DELETE_ONE_RECEPTIONIST,
            RolePermission.FIND_ALL_RECEPTIONISTS,

            RolePermission.CREATE_ONE_PATIENT,
            RolePermission.UPDATE_ONE_PATIENT,
            RolePermission.DELETE_ONE_PATIENT,
            RolePermission.FIND_ALL_PATIENTS,

            RolePermission.CREATE_ONE_SCHEDULE,
            RolePermission.UPDATE_ONE_SCHEDULE,
            RolePermission.DELETE_ONE_SCHEDULE,
            RolePermission.FIND_ALL_SCHEDULES,
            RolePermission.FIND_SCHEDULES_BY_DOCTOR,

            RolePermission.CREATE_ONE_APPOINTMENT,
            RolePermission.UPDATE_ONE_APPOINTMENT,
            RolePermission.DELETE_ONE_APPOINTMENT,
            RolePermission.FIND_ALL_APPOINTMENTS,
            RolePermission.FIND_APPOINTMENTS_BY_PATIENT,
            RolePermission.FIND_APPOINTMENTS_BY_DOCTOR

    )),

    DOCTOR(Arrays.asList(

            RolePermission.FIND_SCHEDULES_BY_DOCTOR,

            RolePermission.READ_MY_PROFILE
    )),
    RECEPTIONIST(Arrays.asList(
            RolePermission.READ_MY_PROFILE,

            // Doctors
            RolePermission.FIND_ALL_DOCTORS,
            // Patients
            RolePermission.CREATE_ONE_PATIENT,
            RolePermission.UPDATE_ONE_PATIENT,
            RolePermission.DELETE_ONE_PATIENT,
            RolePermission.FIND_ALL_PATIENTS,

            // Schedules
            RolePermission.FIND_SCHEDULES_BY_DOCTOR,

            // Appointments
            RolePermission.CREATE_ONE_APPOINTMENT,
            RolePermission.UPDATE_ONE_APPOINTMENT,
            RolePermission.DELETE_ONE_APPOINTMENT,
            RolePermission.FIND_ALL_APPOINTMENTS,
            RolePermission.FIND_APPOINTMENTS_BY_PATIENT,
            RolePermission.FIND_APPOINTMENTS_BY_DOCTOR


    )),

    PATIENT(Arrays.asList(
            RolePermission.READ_MY_PROFILE
    ));

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    private List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }


}
