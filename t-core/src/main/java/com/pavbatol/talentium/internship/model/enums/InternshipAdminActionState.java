package com.pavbatol.talentium.internship.model.enums;

public enum InternshipAdminActionState {
    PUBLISH_EVENT,
    REJECT_EVENT;

    public static InternshipAdminActionState by(String stateName) {
        try {
            return InternshipAdminActionState.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
