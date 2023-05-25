package com.pavbatol.talentium.hh.dto;

import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import lombok.Value;

import java.util.Set;

@Value
public class HhDtoResponse {

    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    String authority;

    Set<ManagementDtoResponse> managements;

    String address;

    String contacts;

    Integer rate;
}
