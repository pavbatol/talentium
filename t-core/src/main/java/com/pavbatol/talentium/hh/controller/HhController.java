package com.pavbatol.talentium.hh.controller;

import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/hunter")
@Tag(name = "Private: Company", description = "API for working with company's")
public class HhController {

    private final JwtProvider jwtProvider;


    //    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @PreAuthorize("hasAnyRole('ADMIN', 'HH')")
//    @PostMapping("/test")
//    @SecurityRequirement(name = "JWT")
//    @Operation(summary = "add", description = "adding a hand hunter")
//    public ResponseEntity<?> test(HttpServletRequest servletRequest) {
//        log.debug("POST add() ");
//        Long body = jwtProvider.geUserId(servletRequest);
//        Claims claims = jwtProvider.getAccessClaims(jwtProvider.resolveToken(servletRequest).orElse("---"));
////        body = body + "\n\n" + claims;
//        return ResponseEntity.status(HttpStatus.CREATED).body(body + "\n\n" + claims);
//    }

    @PreAuthorize("hasAnyRole('HH')")
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a hand hunter")
    public ResponseEntity<?> add(HttpServletRequest servletRequest, HhDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        Long body = jwtProvider.geUserId(servletRequest);
        Claims claims = jwtProvider.getAccessClaims(jwtProvider.resolveToken(servletRequest).orElse("---"));
//        body = body + "\n\n" + claims;
        return ResponseEntity.status(HttpStatus.CREATED).body(body + "\n\n" + claims);
    }


}
