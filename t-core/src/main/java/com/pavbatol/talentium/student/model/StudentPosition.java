package com.pavbatol.talentium.student.model;

import lombok.NonNull;

public enum StudentPosition {

    CANDIDATE,
    INVITEE,
    LEARNER,
    CONTESTANT,
    CHALLENGER,
    INTERN;

    public static StudentPosition of(@NonNull String name) throws IllegalArgumentException {
        try {
            return StudentPosition.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown position: " + name, e);
        }
    }
}
