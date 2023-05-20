package com.pavbatol.talentium.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RoleDtoShort {

    @NotNull
    Long id;

    @Schema(description = "Name of role according Enum in server", example = "ADMIN")
    String name;
}
