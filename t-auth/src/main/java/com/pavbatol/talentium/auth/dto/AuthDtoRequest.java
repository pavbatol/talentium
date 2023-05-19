package com.pavbatol.talentium.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class AuthDtoRequest {

    @NotBlank
    @Size(min = 6)
    String username;

    @NotBlank
    @Size(min = 6)
    String password;
}
