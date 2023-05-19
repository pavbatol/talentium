package com.pavbatol.talentium.auth.controller;

import com.pavbatol.talentium.auth.dto.AuthDtoRefreshRequest;
import com.pavbatol.talentium.auth.dto.AuthDtoRequest;
import com.pavbatol.talentium.auth.dto.AuthDtoResponse;
import com.pavbatol.talentium.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Public: Authorization", description = "API for working with authorization")
public class PublicAuthController {

    private final AuthService authService;

    @GetMapping("/logout")
    @Operation(summary = "logout", description = "de - logging (let the frontend not forget to delete the access token)")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        log.debug("GET logout()");
        authService.logout(request);
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/login")
    @Operation(summary = "login", description = "checking login and password and provide access and refresh tokens")
    public ResponseEntity<?> login(HttpServletRequest request, @Valid @RequestBody AuthDtoRequest dtoAuthRequest) {
        log.debug("POST login() with {}", dtoAuthRequest);
        AuthDtoResponse dtoAuthResponse = authService.login(request, dtoAuthRequest);
        String body = String.format("%s\n\n%s", dtoAuthResponse.getAccessToken(), dtoAuthResponse.getRefreshToken());
        return ResponseEntity.ok(body);
    }

    @PostMapping("/token")
    @Operation(summary = "getNewAccessToken", description = "getting a new access token to replace the old one")
    public ResponseEntity<AuthDtoResponse> getNewAccessToken(HttpServletRequest request,
                                                             @Valid @RequestBody AuthDtoRefreshRequest dtoRefreshRequest) {
        log.debug("POST getNewAccessToken() with {}", dtoRefreshRequest);
        AuthDtoResponse body = authService.getNewAccessToken(request, dtoRefreshRequest.getRefreshToken());
        return ResponseEntity.ok(body);
    }
}
