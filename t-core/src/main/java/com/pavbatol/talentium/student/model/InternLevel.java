package com.pavbatol.talentium.student.model;

import lombok.NonNull;

public enum InternLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public static InternLevel of(@NonNull String name) throws IllegalArgumentException {
        try {
            return InternLevel.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
