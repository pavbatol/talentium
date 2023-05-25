package com.pavbatol.talentium.mentor.dto;

import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.management.model.Management;
import com.pavbatol.talentium.mentor.feedback.model.MentorFeedback;
import com.pavbatol.talentium.student.model.Student;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
public class MentorDtoUpdate {

    String email;

    String firstName;

    String secondName;

    Boolean deleted;

    Hh owner;

    Management management;
}
