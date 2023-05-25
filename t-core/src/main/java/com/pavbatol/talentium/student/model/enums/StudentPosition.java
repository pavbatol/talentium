package com.pavbatol.talentium.student.model.enums;

import lombok.NonNull;

public enum StudentPosition {

    CANDIDATE,
    INTERN;

    public static StudentPosition of(@NonNull String name) throws IllegalArgumentException {
        try {
            return StudentPosition.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
