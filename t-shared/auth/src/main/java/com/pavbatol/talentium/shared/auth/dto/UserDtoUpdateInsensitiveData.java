package com.pavbatol.talentium.shared.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class UserDtoUpdateInsensitiveData {

    @Pattern(regexp = ".*\\S.*")
    @Email
    String email;

    @Pattern(regexp = ".*\\S.*")
    String firstName;

    @Pattern(regexp = ".*\\S.*")
    String secondName;
}
