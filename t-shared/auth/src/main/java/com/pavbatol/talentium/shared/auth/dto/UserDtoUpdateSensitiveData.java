package com.pavbatol.talentium.shared.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.Set;

@Value
public class UserDtoUpdateSensitiveData {

    @Pattern(regexp = ".*\\S{6}.*")
    String password;

    @Schema(description = "Security access roles")
    Set<RoleDto> roles;
}
