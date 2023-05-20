package com.pavbatol.talentium.hr.model;

import lombok.NonNull;

public enum MentorStatus {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    CERTIFICATED;

    public static MentorStatus of(@NonNull String name) throws IllegalArgumentException {
        try {
            return MentorStatus.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
