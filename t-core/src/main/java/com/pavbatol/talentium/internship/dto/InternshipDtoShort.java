package com.pavbatol.talentium.internship.dto;

import com.pavbatol.talentium.hh.dto.HhDtoShort;
import com.pavbatol.talentium.management.dto.ManagementDtoShort;
import lombok.Value;

@Value
public class InternshipDtoShort {
    Long id;

    String title;

    HhDtoShort initiator;

    ManagementDtoShort management;
}
