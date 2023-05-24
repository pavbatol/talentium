package com.pavbatol.talentium.curator.dto;

import com.pavbatol.talentium.hh.model.Hh;
import lombok.Value;

@Value
public class CuratorDtoUpdate {

    String email;

    String firstName;

    String secondName;

    Hh owner;
}
