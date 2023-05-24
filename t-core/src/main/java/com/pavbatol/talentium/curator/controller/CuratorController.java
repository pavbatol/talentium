package com.pavbatol.talentium.curator.controller;

import com.pavbatol.talentium.curator.dto.CuratorDtoRequest;
import com.pavbatol.talentium.curator.dto.CuratorDtoResponse;
import com.pavbatol.talentium.curator.dto.CuratorDtoUpdate;
import com.pavbatol.talentium.curator.service.CuratorService;
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
@RequestMapping("/curators")
@Tag(name = "Private: Curator", description = "API for working with Curators ")
public class CuratorController {
    public static final String HAS_ANY_ROLE_CURATOR_ADMIN = "hasAnyRole('CURATOR', 'ADMIN')";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    private final CuratorService curatorService;

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a Curator")
    public ResponseEntity<CuratorDtoResponse> add(HttpServletRequest servletRequest,
                                                  @Valid @RequestBody CuratorDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        CuratorDtoResponse body = curatorService.add(servletRequest, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PatchMapping("/{curatorId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "Curator update")
    public ResponseEntity<CuratorDtoResponse> update(HttpServletRequest servletRequest,
                                                     @PathVariable("curatorId") Long curatorId,
                                                     @Valid @RequestBody CuratorDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", curatorId, dto);
        CuratorDtoResponse body = curatorService.update(servletRequest, curatorId, dto);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @DeleteMapping("/{curatorId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a Curator")
    public ResponseEntity<Void> remove(HttpServletRequest servletRequest,
                                       @PathVariable("curatorId") Long curatorId) {
        log.debug("DELETE remove() with userId {}", curatorId);
        curatorService.remove(servletRequest, curatorId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping("/{curatorId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a Curator by Id")
    public ResponseEntity<CuratorDtoResponse> findById(@PathVariable("curatorId") Long curatorId) {
        log.debug("GET findById() with userId {}", curatorId);
        CuratorDtoResponse body = curatorService.findById(curatorId);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "find all Curators getting page by page")
    public ResponseEntity<List<CuratorDtoResponse>> findAll(
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with from: {}, size: {}", from, size);
        List<CuratorDtoResponse> body = curatorService.findAll(from, size);
        return ResponseEntity.ok(body);
    }
}
