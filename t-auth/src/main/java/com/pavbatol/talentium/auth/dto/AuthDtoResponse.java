package com.pavbatol.talentium.auth.dto;

import lombok.Value;

@Value
public class AuthDtoResponse {
    String type = "Bearer";
    String accessToken;
    String refreshToken;
}
