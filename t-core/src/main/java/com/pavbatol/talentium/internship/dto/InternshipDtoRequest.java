package com.pavbatol.talentium.internship.dto;

import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
import com.pavbatol.talentium.management.dto.ManagementDtoShort;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InternshipDtoRequest {

    @NotBlank
    @Size(min = 3, max = 120)
    String title;

    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;

    @NotBlank
    @Size(min = 20, max = 7000)
    String description;

    @NotNull
    ManagementDtoShort management;

    @NotNull
    Double latitude;

    @NotNull
    Double longitude;

    @NotNull
    @Positive
    Integer ageFrom;

    @NotNull
    @Positive
    Integer ageTo;

    @NotNull
    @PositiveOrZero
    Integer participantLimit;

    @NotNull
    @Future
    LocalDateTime startDate;

    @NotNull
    LocalDateTime endDate;

    @NotNull
    WorkingDayDuration dayDuration;

    @AssertTrue(message = "Start date should be before end date")
    public boolean isValidDates() {
        return startDate.isBefore(endDate);
    }

}
