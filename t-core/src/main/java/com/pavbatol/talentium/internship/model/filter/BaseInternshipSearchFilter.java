package com.pavbatol.talentium.internship.model.filter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder(toBuilder = true)
public class BaseInternshipSearchFilter {
    String title;
    String annotation;
    String description;

    List<Long> initiatorIds;
    List<Long> managementIds;

    Double startLatitude;
    Double endLatitude;
    Double startLongitude;
    Double endLongitude;

    Integer rangeStartAgeFrom;
    Integer rangeStartAgeTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime rangeStartPublishedOn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime rangeEndPublishedOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime rangeStartStartDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime rangeEndStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime rangeStartEndDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime rangeEndEndDate;

    String dayDuration;
}
