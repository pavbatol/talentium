package com.pavbatol.talentium.auth.service;

import com.pavbatol.talentium.auth.dto.AuthDtoRequest;
import com.pavbatol.talentium.auth.dto.AuthDtoResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthDtoResponse login(HttpServletRequest httpServletRequest, AuthDtoRequest dtoAuthRequest);

    void logout(HttpServletRequest httpServletRequest);

    AuthDtoResponse getNewAccessToken(HttpServletRequest httpServletRequest, String refreshToken);

    AuthDtoResponse getNewRefreshToken(HttpServletRequest httpServletRequest, String refreshToken);

    void removeRefreshTokensByUserId(Long userId);

    void removeAllRefreshTokens();
}
