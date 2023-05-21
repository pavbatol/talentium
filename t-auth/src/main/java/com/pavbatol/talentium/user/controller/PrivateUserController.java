package com.pavbatol.talentium.user.controller;

import com.pavbatol.talentium.user.dto.UserDtoResponse;
import com.pavbatol.talentium.user.dto.UserDtoUpdateShort;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'CANDIDATE', 'INTERN', 'CURATOR', 'MENTOR', 'HH')")
    @PatchMapping("/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "user update")
    public ResponseEntity<UserDtoResponse> update(HttpServletRequest servletRequest, @PathVariable("userId") Long userId,
                                                  @Valid @RequestBody UserDtoUpdateShort dto) {
        log.debug("PATCH update() with userId {}, dto {}", userId, dto);
        UserDtoResponse body = userService.update(servletRequest, userId, dto);
        return ResponseEntity.ok(body);
    }

}
