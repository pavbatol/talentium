package com.pavbatol.talentium.student.dto;

import com.pavbatol.talentium.app.util.BasePersonDto;
import com.pavbatol.talentium.management.dto.ManagementDtoRequest;
import com.pavbatol.talentium.mentor.dto.MentorDtoShort;
import com.pavbatol.talentium.student.model.InternLevel;
import com.pavbatol.talentium.student.model.StudentPosition;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class StudentDtoUpdate implements BasePersonDto {

    Long userId;

    String email;

    String firstName;

    String secondName;

    StudentPosition position;

    InternLevel level;

    LocalDateTime internOn;

    MentorDtoShort mentor;

    ManagementDtoRequest management;

    Boolean deleted;
}
