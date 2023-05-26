package com.pavbatol.talentium.internship.model.enums;

public enum WorkingDayDuration {
    FULL,
    HALF;

    public static WorkingDayDuration by(String durationName) {
        try {
            return WorkingDayDuration.valueOf(durationName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown duration: " + durationName, e);
        }
    }
}
