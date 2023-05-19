package com.pavbatol.talentium.auth.controller;

import com.pavbatol.talentium.auth.dto.AuthDtoRefreshRequest;
import com.pavbatol.talentium.auth.dto.AuthDtoResponse;
import com.pavbatol.talentium.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Private: Authorization", description = "API for working with authorization")
public class PrivateAuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "getNewRefreshToken", description = "getting a new refresh token to replace the old one")
    public ResponseEntity<AuthDtoResponse> getNewRefreshToken(HttpServletRequest request,
                                                              @Valid @RequestBody AuthDtoRefreshRequest dtoRefreshRequest) {
        log.debug("POST getNewRefreshToken() with {}", dtoRefreshRequest);
        AuthDtoResponse body = authService.getNewRefreshToken(request, dtoRefreshRequest.getRefreshToken());
        return ResponseEntity.ok(body);
    }
}
