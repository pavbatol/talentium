package com.pavbatol.talentium.role.controller;

import com.pavbatol.talentium.role.dto.RoleDto;
import com.pavbatol.talentium.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/role")
@Tag(name = "Admin: Role", description = "API for working with roles")
public class AdminRoleController {

    private final RoleService roleService;

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a role")
    public ResponseEntity<RoleDto> add(@Valid @RequestBody @Parameter(description = "JSON with role data") RoleDto dto) {
        log.debug("POST add() with {}", dto);
        RoleDto body = roleService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PatchMapping("/{roleId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "role update")
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto dto,
                                          @PathVariable("roleId") Long roleId) {
        log.debug("PATCH update() with roleId {}, dto {}", roleId, dto);
        RoleDto body = roleService.update(roleId, dto);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{roleId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a role")
    public ResponseEntity<Void> remove(@PathVariable("roleId") Long roleId) {
        log.debug("DELETE remove() with roleId {}", roleId);
        roleService.remove(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{roleId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a role by Id")
    public ResponseEntity<RoleDto> findById(@PathVariable("roleId") Long roleId) {
        log.debug("GET findById() with roleId {}", roleId);
        RoleDto body = roleService.findById(roleId);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "getting roles page by page")
    public ResponseEntity<List<RoleDto>> findAll() {
        log.debug("GET findAll()");
        List<RoleDto> body = roleService.findAll();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/acceptable")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "getAcceptableRoles", description = "getting acceptable user roles")
    public ResponseEntity<List<String>> getAcceptableRoles() {
        log.debug("GET getAcceptableRoles()");
        List<String> body = roleService.getAcceptableRoles();
        return ResponseEntity.ok(body);
    }
}
