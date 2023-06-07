package com.pavbatol.talentium.curator.dto;

import com.pavbatol.talentium.app.util.BasePersonDto;
import com.pavbatol.talentium.hh.model.Hh;
import lombok.Value;

@Value
public class CuratorDtoUpdate implements BasePersonDto {

    String email;

    String firstName;

    String secondName;

    Hh owner;
}
