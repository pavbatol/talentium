package com.pavbatol.talentium.student.dto;

import com.pavbatol.talentium.student.model.StudentPosition;
import lombok.Value;

@Value
public class StudentDtoResponseShort {
    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    StudentPosition position;

}
