package com.pavbatol.talentium.management.controller;

import com.pavbatol.talentium.management.dto.ManagementDtoRequest;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import com.pavbatol.talentium.management.dto.ManagementDtoUpdate;
import com.pavbatol.talentium.management.service.ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
@Tag(name = "Admin: Management", description = "API for working with managements")
public class ManagementController {
    public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    private final ManagementService managementService;

    @PreAuthorize(HAS_ROLE_ADMIN)
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a Management")
    public ResponseEntity<ManagementDtoResponse> add(@Valid @RequestBody ManagementDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        ManagementDtoResponse body = managementService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PreAuthorize(HAS_ROLE_ADMIN)
    @PatchMapping("/{managementId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "Management update")
    public ResponseEntity<ManagementDtoResponse> update(@PathVariable("managementId") Long managementId,
                                                        @Valid @RequestBody ManagementDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", managementId, dto);
        ManagementDtoResponse body = managementService.update(managementId, dto);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(HAS_ROLE_ADMIN)
    @DeleteMapping("/{managementId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a Management")
    public ResponseEntity<Void> remove(@PathVariable("managementId") Long managementId) {
        log.debug("DELETE remove() with userId {}", managementId);
        managementService.remove(managementId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(HAS_ROLE_ADMIN)
    @GetMapping("/{managementId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a Management by Id")
    public ResponseEntity<ManagementDtoResponse> findById(@PathVariable("managementId") Long managementId) {
        log.debug("GET findById() with userId {}", managementId);
        ManagementDtoResponse body = managementService.findById(managementId);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "getting Managements")
    public ResponseEntity<List<ManagementDtoResponse>> findAll() {
        log.debug("GET findAll()");
        List<ManagementDtoResponse> body = managementService.findAll();
        return ResponseEntity.ok(body);
    }
}
