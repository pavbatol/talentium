package com.pavbatol.talentium.internship.model.enums;

public enum InternshipAdminActionState {
    PUBLISH_INTERNSHIP,
    REJECT_INTERNSHIP;

    public static InternshipAdminActionState by(String actionState) {
        try {
            return InternshipAdminActionState.valueOf(actionState.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown actionState: " + actionState, e);
        }
    }
}
