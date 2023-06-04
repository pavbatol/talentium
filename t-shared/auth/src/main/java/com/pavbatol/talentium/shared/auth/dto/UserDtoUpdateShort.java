package com.pavbatol.talentium.shared.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import javax.validation.constraints.Email;
import java.util.Set;

@Value
public class UserDtoUpdateShort {

    @Pattern(regexp = ".*\\S.*")
    @Email
    String email;

    @Pattern(regexp = ".*\\S{6}.*")
    String password;

    @Schema(description = "Security access roles")
    Set<RoleDto> roles;
}
