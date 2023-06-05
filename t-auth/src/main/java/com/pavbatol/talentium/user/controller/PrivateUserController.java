package com.pavbatol.talentium.user.controller;

import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateInsensitiveData;
import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateSensitiveData;
import com.pavbatol.talentium.user.dto.UserDtoResponse;
import com.pavbatol.talentium.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/private/user")
@Tag(name = "Admin: User", description = "API for working with users")
public class PrivateUserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @PatchMapping("/{userId}/roles")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "updateRoles", description = "user update")
    public ResponseEntity<UserDtoResponse> updateRoles(HttpServletRequest servletRequest,
                                                       @PathVariable("userId") Long userId,
                                                       @Valid @RequestBody UserDtoUpdateSensitiveData dto) {
        log.debug("PATCH updateRoles() with userId {}, dto {}", userId, dto);
        UserDtoResponse body = userService.updateRoles(servletRequest, userId, dto);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CANDIDATE', 'INTERN', 'CURATOR', 'MENTOR', 'HH')")
    @PatchMapping("/{userId}/insensitive")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "updateInsensitive", description = "user update")
    public ResponseEntity<UserDtoResponse> updateInsensitive(HttpServletRequest servletRequest,
                                                             @PathVariable("userId") Long userId,
                                                             @Valid @RequestBody UserDtoUpdateInsensitiveData dto) {
        log.debug("PATCH updateInsensitive() with userId {}, dto {}", userId, dto);
        UserDtoResponse body = userService.updateInsensitive(servletRequest, userId, dto);
        return ResponseEntity.ok(body);
    }

}
