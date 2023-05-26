package com.pavbatol.talentium.internship.dto;

import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InternshipDtoResponse {
    Long id;

    String title;

    String annotation;

    String description;

    HhDtoResponse initiator;

    ManagementDtoResponse management;

    Double latitude;

    Double longitude;

    Integer ageFrom;

    Integer ageTo;

    Integer participantLimit;

    Long confirmedRequests;

    LocalDateTime createdOn;

    LocalDateTime publishedOn;

    LocalDateTime startDate;

    LocalDateTime endDate;

    WorkingDayDuration dayDuration;

    InternshipState state;
}
