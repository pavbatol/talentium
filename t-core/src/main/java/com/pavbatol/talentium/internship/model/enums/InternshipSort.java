package com.pavbatol.talentium.internship.model.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public enum InternshipSort {
    ID_ASC(Sort.by(Constants.ID).ascending()),
    ID_DES(Sort.by(Constants.ID).descending()),
    START_DATE_ASC(Sort.by(Constants.START_DATE).ascending()),
    START_DATE_DES(Sort.by(Constants.START_DATE).descending()),
    PUBLISHED_ON_ASC(Sort.by(Constants.PUBLISHED_ON).ascending()),
    PUBLISHED_ON_DES(Sort.by(Constants.PUBLISHED_ON).descending()),
    TITLE(Sort.by(Constants.TITLE).ascending()),
    AGE_FROM_ASC(Sort.by(Constants.AGE_FROM).ascending()),
    AGE_FROM_DES(Sort.by(Constants.AGE_FROM).descending());

    public final Sort sort;

    public static InternshipSort by(String stateName) {
        try {
            return InternshipSort.valueOf(stateName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown sort: " + stateName, e);
        }
    }

    private static class Constants {
        public static final String ID = "id";
        public static final String START_DATE = "startDate";
        public static final String PUBLISHED_ON = "publishedOn";
        public static final String TITLE = "title";
        public static final String AGE_FROM = "ageFrom";
    }
}
