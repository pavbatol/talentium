package com.pavbatol.talentium.internship.dto;

import com.pavbatol.talentium.hh.dto.HhDtoShort;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
import com.pavbatol.talentium.management.dto.ManagementDtoShort;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InternshipDtoFull {
    Long id;

    String title;

    String annotation;

    String description;

    HhDtoShort initiator;

    ManagementDtoShort management;

    Double latitude;

    Double longitude;

    Integer ageFrom;

    Integer ageTo;

    Integer participantLimit;

    Integer confirmedRequests;

    LocalDateTime createdOn;

    LocalDateTime publishedOn;

    LocalDateTime startDate;

    LocalDateTime endDate;

    WorkingDayDuration dayDuration;

    InternshipState state;
}
