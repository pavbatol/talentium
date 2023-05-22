package com.pavbatol.talentium.hh.dto;

import lombok.Value;

@Value
public class HhDtoResponse {

    Long id;

    Long userId;

    String email;

    String firstName;

    String secondName;

    String authority;

    String management;

    String address;

    String contacts;

    Integer rate;
}
