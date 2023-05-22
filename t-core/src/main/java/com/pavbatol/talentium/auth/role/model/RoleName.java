package com.pavbatol.talentium.auth.role.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleName implements GrantedAuthority {

    ADMIN,
    USER,
    //---
    CANDIDATE,
    INTERN,
    CURATOR,
    MENTOR,
    HH;

    private static final String ROLE = "ROLE_";

    @Override
    public String getAuthority() {
        return ROLE + name();
    }

    public static RoleName of(@NonNull String name) throws IllegalArgumentException {
        try {
            return RoleName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
