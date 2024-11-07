package com.med.backend.persistence.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMINISTRATOR(Arrays.asList(

            RolePermission.READ_MY_PROFILE,
            RolePermission.FIND_ALL_USERS
    )),
    ASSISTANT_ADMINISTRATOR(Arrays.asList(

            RolePermission.READ_MY_PROFILE
    )),
    CUSTOMER(Arrays.asList(
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
