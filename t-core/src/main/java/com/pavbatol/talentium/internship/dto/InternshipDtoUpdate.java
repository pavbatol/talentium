package com.pavbatol.talentium.internship.dto;

import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
import com.pavbatol.talentium.management.dto.ManagementDtoShort;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InternshipDtoUpdate {

    String title;

    String annotation;

    String description;

    ManagementDtoShort management;

    Double latitude;

    Double longitude;

    Integer ageFrom;

    Integer ageTo;

    Integer participantLimit;

    LocalDateTime startDate;

    LocalDateTime endDate;

    WorkingDayDuration dayDuration;
}
