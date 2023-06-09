package com.pavbatol.talentium.user.dto;

import com.pavbatol.talentium.shared.auth.dto.RoleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.Set;

@Value
public class UserDtoUpdate {

    @Pattern(regexp = ".*\\S.*")
    @Email
    String email;

    @Pattern(regexp = ".*\\S{5}.*")
    String username;

    @Schema(description = "Enabled or not for security access", example = "true")
    Boolean enabled;

    @Schema(description = "Security access roles")
    Set<RoleDto> roles;

    @Pattern(regexp = ".*\\S.*")
    String firstName;

    @Pattern(regexp = ".*\\S.*")
    String secondName;
}
