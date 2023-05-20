package com.pavbatol.talentium.user.dto;

import com.pavbatol.talentium.role.dto.RoleDtoShort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.Set;

@Value
public class UserDtoRequest {

    @NotBlank
    @Email
    String email;

    @NotNull
    @Pattern(regexp = "^\\S{6,}$", message = "The string must not contain spaces and at least 6 characters")
    String username;

    @NotNull
    @Pattern(regexp = "^(?=\\S)(?!.*\\s$).{6,}$", message = "The string must not start/end with spaces")
    String password;

    @NotNull
    @Schema(description = "Enabled or not for security access", example = "true")
    Boolean enabled;

    @Schema(description = "Security access roles")
    Set<RoleDtoShort> roles;

    @NotNull
    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String firstName;

    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String secondName;
}
