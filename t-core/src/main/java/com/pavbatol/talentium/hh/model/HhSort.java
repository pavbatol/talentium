package com.pavbatol.talentium.hh.model;

public enum HhSort {
    AUTHORITY,
    RATE;

    public static HhSort of(String name) {
        try {
            return HhSort.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + name, e);
        }
    }
}
