package com.pavbatol.talentium.internship.controller;

import com.pavbatol.talentium.internship.dto.InternshipDtoRequest;
import com.pavbatol.talentium.internship.dto.InternshipDtoResponse;
import com.pavbatol.talentium.internship.dto.InternshipDtoUpdate;
import com.pavbatol.talentium.internship.service.InternshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/hunters/internships")
@Tag(name = "Private: Internship", description = "API for working with Internships ")
public class PrivateInternshipController {
    public static final String HAS_ANY_ROLE_CURATOR_ADMIN = "hasAnyRole('HH', 'ADMIN')";
    public static final String HAS_ROLE_HH = "hasRole('HH')";
    private final InternshipService internshipService;

    @PreAuthorize(HAS_ROLE_HH)
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a Internship")
    public ResponseEntity<InternshipDtoResponse> add(HttpServletRequest servletRequest,
                                                     @Valid @RequestBody InternshipDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        InternshipDtoResponse body = internshipService.add(servletRequest, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PatchMapping("/{internshipId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "Internship update")
    public ResponseEntity<InternshipDtoResponse> update(HttpServletRequest servletRequest,
                                                        @PathVariable("internshipId") Long internshipId,
                                                        @Valid @RequestBody InternshipDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", internshipId, dto);
        InternshipDtoResponse body = internshipService.update(servletRequest, internshipId, dto);
        return ResponseEntity.ok(body);
    }
}
