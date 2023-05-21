package com.pavbatol.talentium.company.controller;

import com.pavbatol.talentium.auth.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Tag(name = "Private: Company", description = "API for working with company's")
public class CompanyController {

    private final JwtProvider jwtProvider;

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a user")
    public ResponseEntity<?> test(HttpServletRequest servletRequest) {
        log.debug("POST add() ");
        Long body =jwtProvider.geUserId(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
