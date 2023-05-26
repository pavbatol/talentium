package com.pavbatol.talentium.internship.model.enums;

public enum AdminActionState {
    PUBLISH_EVENT,
    REJECT_EVENT;

    public static AdminActionState by(String stateName) {
        try {
            return AdminActionState.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
