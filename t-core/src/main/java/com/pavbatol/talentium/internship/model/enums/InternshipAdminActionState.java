package com.pavbatol.talentium.internship.model.enums;

public enum InternshipAdminActionState {
    PUBLISH_INTERNSHIP,
    REJECT_INTERNSHIP;

    public static InternshipAdminActionState by(String stateName) {
        try {
            return InternshipAdminActionState.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
