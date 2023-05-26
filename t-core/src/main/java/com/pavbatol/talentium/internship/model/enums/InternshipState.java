package com.pavbatol.talentium.internship.model.enums;

public enum InternshipState {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static InternshipState by(String stateName) {
        try {
            return InternshipState.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
