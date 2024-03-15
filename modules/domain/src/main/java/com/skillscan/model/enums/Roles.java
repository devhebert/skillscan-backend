package com.skillscan.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Roles {
    SYSADMIN("sysadmin"),
    ADMIN("admin"),
    USER("user"),
    COMMON("common");

    public final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
