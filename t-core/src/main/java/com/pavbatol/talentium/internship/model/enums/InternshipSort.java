package com.pavbatol.talentium.internship.model.enums;

public enum InternshipSort {
    EVENT_DATE,
    VIEWS;

    public static InternshipSort by(String stateName) {
        try {
            return InternshipSort.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown sort: " + stateName, e);
        }
    }
}
