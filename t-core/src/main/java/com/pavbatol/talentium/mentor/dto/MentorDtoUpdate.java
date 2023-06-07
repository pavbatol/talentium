package com.pavbatol.talentium.mentor.dto;

import com.pavbatol.talentium.app.util.BasePersonDto;
import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.management.model.Management;
import lombok.Value;

@Value
public class MentorDtoUpdate implements BasePersonDto {

    String email;

    String firstName;

    String secondName;

    Boolean deleted;

    Hh owner;

    Management management;
}
