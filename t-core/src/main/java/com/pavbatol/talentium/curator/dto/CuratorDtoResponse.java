package com.pavbatol.talentium.curator.dto;

import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import lombok.Value;

@Value
public class CuratorDtoResponse {
    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    HhDtoResponse owner;
}
