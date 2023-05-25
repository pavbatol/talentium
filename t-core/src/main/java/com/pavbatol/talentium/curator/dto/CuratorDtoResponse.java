package com.pavbatol.talentium.curator.dto;

import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CuratorDtoResponse {
    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    LocalDateTime registeredOn;

    HhDtoResponse owner;
}
