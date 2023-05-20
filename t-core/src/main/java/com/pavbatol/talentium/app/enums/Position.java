package com.pavbatol.talentium.app.enums;

import lombok.NonNull;

public enum Position {

    CANDIDATE,
    INTERN,
    CURATOR,
    MENTOR,
    HR;

    public static Position of(@NonNull String name) throws IllegalArgumentException {
        try {
            return Position.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
