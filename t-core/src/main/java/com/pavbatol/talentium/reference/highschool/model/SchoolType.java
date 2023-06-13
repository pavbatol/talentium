package com.pavbatol.talentium.reference.highschool.model;

import lombok.NonNull;

public enum SchoolType {
    STATE,
    COMMERCIAL,
    UNKNOWN;

    public static SchoolType by(@NonNull String type) throws IllegalArgumentException {
        try {
            return SchoolType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown type: " + type, e);
        }
    }
}
