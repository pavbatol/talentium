package com.pavbatol.talentium.auth.controller;

import com.pavbatol.talentium.auth.service.AuthService;
import com.pavbatol.talentium.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/auth")
@Tag(name = "Admin: Authorization", description = "API for working with authorization")
public class AdminAuthController {

    private final AuthService authService;

    @DeleteMapping("/refresh/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "removeRefreshTokensByUserId", description = "deleting all refresh tokens")
    public ResponseEntity<Void> removeRefreshTokensByUserId(@PathVariable(value = "userId") Long userId) {
        log.debug("DELETE removeRefreshTokensByUserId() with userId: {}", userId);
        authService.removeRefreshTokensByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/refresh")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "removeAllRefreshTokens", description = "deleting all refresh tokens")
    public ResponseEntity<Void> removeAllRefreshTokens() {
        log.debug("DELETE removeAllRefreshTokens()");
        authService.removeAllRefreshTokens();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/secret")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "printRandomSecrets", description = "printing random twu secret strings")
    public String printRandomSecrets() {
        log.debug("GET printRandomSecrets()");
        return String.format("%s\n\n%s", JwtUtils.generateSecretStr(), JwtUtils.generateSecretStr());
    }
}
