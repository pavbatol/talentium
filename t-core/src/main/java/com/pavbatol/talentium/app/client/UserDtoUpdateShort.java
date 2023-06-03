package com.pavbatol.talentium.app.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

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
