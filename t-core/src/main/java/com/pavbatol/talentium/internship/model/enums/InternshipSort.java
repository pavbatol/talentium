package com.pavbatol.talentium.internship.model.enums;

public enum InternshipSort {
    START_DATE,
    PUBLISHED_ON,
    TITLE,
    AGE_FROM;

    public static InternshipSort by(String stateName) {
        try {
            return InternshipSort.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown sort: " + stateName, e);
        }
    }
}
