package com.pavbatol.talentium.internship.model.filter;

import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
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
public class AbstractInternshipSearchFilter {
    String title;
    String annotation;
    String description;

    List<Long> initiatorIds;
    List<Long> managementIds;

    Double latitude;
    Double longitude;

    Integer rangeStartAgeFrom;
    Integer rangeEndAgeTo;

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

    WorkingDayDuration dayDuration;
}
