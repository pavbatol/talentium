package com.pavbatol.talentium.app.enums;

import lombok.NonNull;

public enum Level {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public static Level of(@NonNull String name) throws IllegalArgumentException {
        try {
            return Level.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
