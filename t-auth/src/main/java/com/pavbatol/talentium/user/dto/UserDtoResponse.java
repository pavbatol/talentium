package com.pavbatol.talentium.user.dto;

import com.pavbatol.talentium.role.dto.RoleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.Set;

@Value
public class UserDtoResponse {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    String email;

    String username;

    @Schema(description = "Enabled or not for security access", example = "true")
    Boolean enabled;

    @Schema(description = "Security access roles")
    Set<RoleDto> roles;

    String firstName;

    String secondName;
}