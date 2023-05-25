package com.pavbatol.talentium.student.dto;

import com.pavbatol.talentium.management.dto.ManagementDtoRequest;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import com.pavbatol.talentium.mentor.dto.MentorDtoRequest;
import com.pavbatol.talentium.mentor.dto.MentorDtoResponse;
import com.pavbatol.talentium.student.model.InternLevel;
import com.pavbatol.talentium.student.model.StudentPosition;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class StudentDtoUpdate {

    Long userId;

    String email;

    String firstName;

    String secondName;

    StudentPosition position;

    InternLevel level;

    LocalDateTime internOn;

    MentorDtoRequest mentor;

    ManagementDtoRequest management;

    Boolean deleted;
}
