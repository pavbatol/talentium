package com.pavbatol.talentium.internship.controller;

import com.pavbatol.talentium.internship.dto.InternshipDtoResponse;
import com.pavbatol.talentium.internship.model.enums.InternshipSort;
import com.pavbatol.talentium.internship.model.filter.InternshipPublicSearchFilter;
import com.pavbatol.talentium.internship.service.InternshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/internships")
@Tag(name = "Public: Internship", description = "API for working with Internships ")
public class PublicInternshipController {
    private final InternshipService internshipService;

    @PreAuthorize("permitAll()")
    @GetMapping("/{internshipId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a Internship by Id")
    public ResponseEntity<InternshipDtoResponse> findById(@PathVariable("internshipId") Long internshipId) {
        log.debug("GET findById() with userId {}", internshipId);
        InternshipDtoResponse body = internshipService.findById(internshipId);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "find all Internships getting page by page")
    public ResponseEntity<List<InternshipDtoResponse>> findAllByFilter(
            InternshipPublicSearchFilter filter,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false) String sort) {
        log.debug("GET findAll() with from: {}, size: {}, sort:{}", from, size, sort);
        InternshipSort internshipSort = sort == null ? InternshipSort.START_DATE : InternshipSort.by(sort);
        List<InternshipDtoResponse> body = internshipService.findAll(filter, from, size, internshipSort);
        return ResponseEntity.ok(body);
    }
}
