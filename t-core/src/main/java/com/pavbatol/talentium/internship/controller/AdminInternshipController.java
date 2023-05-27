package com.pavbatol.talentium.internship.controller;

import com.pavbatol.talentium.internship.dto.InternshipDtoResponse;
import com.pavbatol.talentium.internship.model.enums.InternshipSort;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.filter.InternshipAdminSearchFilter;
import com.pavbatol.talentium.internship.service.InternshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/internships")
@Tag(name = "Admin: Internship", description = "API for working with Internships ")
public class AdminInternshipController {
    public static final String HAS_ANY_ROLE_CURATOR_ADMIN = "hasAnyRole('CURATOR', 'ADMIN')";
    private final InternshipService internshipService;

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PatchMapping("/{internshipId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "Internship update")
    public ResponseEntity<InternshipDtoResponse> updateState(HttpServletRequest servletRequest,
                                                             @PathVariable("internshipId") Long internshipId,
                                                             @RequestParam(value = "state") String state) {
        log.debug("PATCH update() with userId {}, state {}", internshipId, state);
        InternshipDtoResponse body = internshipService.updateState(servletRequest, internshipId, InternshipState.by(state));
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "find all Internships getting page by page")
    public ResponseEntity<List<InternshipDtoResponse>> findAllByFilter(
            InternshipAdminSearchFilter filter,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false) String sort) {
        log.debug("GET findAll() with from: {}, size: {}, sort:{}", from, size, sort);
        InternshipSort internshipSort = sort == null ? InternshipSort.START_DATE : InternshipSort.by(sort);
        List<InternshipDtoResponse> body = internshipService.adminFindAll(filter, from, size, internshipSort);
        return ResponseEntity.ok(body);
    }
}
