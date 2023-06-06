package com.pavbatol.talentium.hh.model;

import org.springframework.data.domain.Sort;

public enum HhSort {
    ID_ASC(Sort.by(Constants.ID).ascending()),
    ID_DES(Sort.by(Constants.ID).descending()),
    AUTHORITY(Sort.by(Constants.AUTHORITY).ascending()),
    MANAGEMENT(Sort.by(Constants.MANAGEMENT).ascending()),
    CONTACTS(Sort.by(Constants.CONTACTS).ascending()),
    ADDRESS(Sort.by(Constants.ADDRESS).ascending()),
    RATE_ASC(Sort.by(Constants.RATE).ascending()),
    RATE_DES(Sort.by(Constants.RATE).descending());

    public final Sort sort;

    HhSort(Sort sort) {
        this.sort = sort;
    }

    public static HhSort by(String name) {
        try {
            return HhSort.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown sort: " + name, e);
        }
    }

    private static class Constants {
        public static final String ID = "id";
        public static final String AUTHORITY = "authority";
        public static final String MANAGEMENT = "management";
        public static final String CONTACTS = "contacts";
        public static final String ADDRESS = "address";
        public static final String RATE = "rate";
    }
}
