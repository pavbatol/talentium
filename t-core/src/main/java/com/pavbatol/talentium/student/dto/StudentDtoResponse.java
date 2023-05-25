package com.pavbatol.talentium.student.dto;

import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import com.pavbatol.talentium.mentor.dto.MentorDtoResponse;
import com.pavbatol.talentium.student.model.InternLevel;
import com.pavbatol.talentium.student.model.StudentPosition;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class StudentDtoResponse {
    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    StudentPosition position;

    InternLevel level;

    LocalDateTime internOn;

    MentorDtoResponse mentor;

    ManagementDtoResponse management;

    Integer rate;

    LocalDateTime registeredOn;
}
