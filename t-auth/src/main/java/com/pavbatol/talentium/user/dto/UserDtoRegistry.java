package com.pavbatol.talentium.user.dto;

import com.pavbatol.talentium.role.model.Role;
import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class UserDtoRegistry {

    @NotNull
    @Pattern(regexp = "^\\S{6,}$", message = "The string must not contain spaces and at least 6 characters")
    String username;

    @NotBlank
    @Pattern(regexp = "^(?=\\S)(?!.*\\s$).{6,}$", message = "The string must not start/end with spaces")
    String password;

    @NotBlank
    @Email
    String email;

    @NotNull
    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String firstName;

    @NotNull
    Role role;
}
