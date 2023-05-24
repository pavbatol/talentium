package com.pavbatol.talentium.hh.controller;

import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.model.HhFilter;
import com.pavbatol.talentium.hh.model.HhSort;
import com.pavbatol.talentium.hh.service.HhService;
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

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/hunter")
@Tag(name = "Private: Company", description = "API for working with companies")
public class HhController {

    public static final String HAS_ANY_ROLE_CURATOR_ADMIN = "hasAnyRole('HH', 'ADMIN')";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    private final HhService hhService;

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a hunter")
    public ResponseEntity<HhDtoResponse> add(HttpServletRequest servletRequest,
                                             @Valid @RequestBody HhDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        HhDtoResponse body = hhService.add(servletRequest, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PatchMapping("/{hhId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "hunter update")
    public ResponseEntity<HhDtoResponse> update(HttpServletRequest servletRequest,
                                                @PathVariable("hhId") Long hhId,
                                                @Valid @RequestBody HhDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", hhId, dto);
        HhDtoResponse body = hhService.update(servletRequest, hhId, dto);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @DeleteMapping("/{hhId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a hunter")
    public ResponseEntity<Void> remove(HttpServletRequest servletRequest,
                                       @PathVariable("hhId") Long hhId) {
        log.debug("DELETE remove() with userId {}", hhId);
        hhService.remove(servletRequest, hhId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping("/{hhId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a hunter by Id")
    public ResponseEntity<HhDtoResponse> findById(@PathVariable("hhId") Long hhId) {
        log.debug("GET findById() with userId {}", hhId);
        HhDtoResponse body = hhService.findById(hhId);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "find all hunter by filter getting page by page")
    public ResponseEntity<List<HhDtoResponse>> findAll(
            HttpServletRequest servletRequest,
            @RequestParam(value = "authority", required = false) String authority,
            @RequestParam(value = "management", required = false) String management,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "contacts", required = false) String contacts,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with " +
                        "authority: {}, management: {}, address: {}, contacts: {}",
                authority, management, address, contacts);
        HhFilter hhFilter = new HhFilter()
                .setAuthority(authority)
                .setManagement(management)
                .setManagement(address)
                .setContacts(contacts);

        HhSort hhSort = sort != null ? HhSort.of(sort) : HhSort.AUTHORITY;
        List<HhDtoResponse> body = hhService.findAll(servletRequest, hhFilter, hhSort, from, size);
        return ResponseEntity.ok(body);
    }
}
