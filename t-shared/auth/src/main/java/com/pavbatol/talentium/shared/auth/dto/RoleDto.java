package com.pavbatol.talentium.shared.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RoleDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @NotBlank
    @Schema(description = "Name of role according Enum in server", example = "ADMIN")
    String name;
}
