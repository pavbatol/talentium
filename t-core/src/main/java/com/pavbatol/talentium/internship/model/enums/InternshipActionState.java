package com.pavbatol.talentium.internship.model.enums;

public enum InternshipActionState {
    SEND_TO_REVIEW,
    CANCEL_REVIEW;

    public static InternshipActionState by(String stateName) {
        try {
            return InternshipActionState.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
