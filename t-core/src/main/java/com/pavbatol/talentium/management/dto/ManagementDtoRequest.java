package com.pavbatol.talentium.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ManagementDtoRequest {
    Long id;

    @NotBlank
    String name;
}
