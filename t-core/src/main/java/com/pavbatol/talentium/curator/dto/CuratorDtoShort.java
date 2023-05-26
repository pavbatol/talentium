package com.pavbatol.talentium.curator.dto;

import com.pavbatol.talentium.hh.dto.HhDtoShort;
import lombok.Value;

@Value
public class CuratorDtoShort {
    Long id;

    Long userId;

    HhDtoShort owner;
}
