package com.pavbatol.talentium.user.controller;

import com.pavbatol.talentium.user.dto.UserDtoRegistry;
import com.pavbatol.talentium.user.service.UserService;
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
@RequestMapping("auth/user")
@Tag(name = "Public: User", description = "API for working with User registration")
public class PublicUserController {
    public static final String REGISTRY = "/registry";
    public static final String CONFIRMATION = "/confirmation";
    public static final String CODE = "code";
    private final UserService userService;

    @GetMapping(REGISTRY)
    @Operation(summary = "register", description = "registering a new user")
    public ResponseEntity<String> register(HttpServletRequest servletRequest, @Valid @RequestBody UserDtoRegistry dtoRegister) {
        log.debug("POST register() with {}", dtoRegister);
        userService.register(servletRequest, dtoRegister);
        String body = "An email with a confirmation code has been sent to your email address.\nConfirm your email address";
        return ResponseEntity.ok(body);
    }

    @GetMapping(CONFIRMATION)
    @Operation(summary = "confirmRegistration", description = "endpoint to confirm registration")
    public ResponseEntity<String> confirmRegistration(@RequestParam(CODE) String code) {
        log.debug("GET confirmRegistration() with {}: {}", CODE, code);
        userService.confirmRegistration(code);
        String body = "Registration is confirmed";
        return ResponseEntity.ok(body);
    }
}
