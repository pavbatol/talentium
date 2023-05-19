package com.pavbatol.talentium.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


/**
 * A strange case, jackson cannot deserialize a class with a single field when annotating lombok@Value.
 * In this case, I decided to abandon lombok.
 */
public class AuthDtoRefreshRequest {
    @NotBlank
    private final String refreshToken;

    @JsonCreator
    public AuthDtoRefreshRequest(@JsonProperty String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
