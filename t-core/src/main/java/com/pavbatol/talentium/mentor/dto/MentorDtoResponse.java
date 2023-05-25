package com.pavbatol.talentium.mentor.dto;

import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MentorDtoResponse {
    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    Integer rate;

    LocalDateTime registeredOn;

    Boolean deleted;

    HhDtoResponse owner;

    ManagementDtoResponse management;
}
