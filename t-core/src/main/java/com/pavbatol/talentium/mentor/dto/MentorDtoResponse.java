package com.pavbatol.talentium.mentor.dto;

import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.management.model.Management;
import com.pavbatol.talentium.mentor.feedback.model.MentorFeedback;
import com.pavbatol.talentium.student.model.Student;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    Hh owner;

    Management management;
}
