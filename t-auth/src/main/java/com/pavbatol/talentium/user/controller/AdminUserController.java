package com.pavbatol.talentium.user.controller;

import com.pavbatol.talentium.user.dto.UserDtoRequest;
import com.pavbatol.talentium.user.dto.UserDtoResponse;
import com.pavbatol.talentium.user.dto.UserDtoUpdate;
import com.pavbatol.talentium.user.service.UserService;
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
@RequestMapping("/admin/user")
@Tag(name = "Admin: User", description = "API for working with users")
public class AdminUserController {

    private final UserService userService;

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a user")
    public ResponseEntity<UserDtoResponse> add(@Valid @RequestBody @Parameter(description = "JSON with user data") UserDtoRequest dto) {
        log.debug("POST add() with {}", dto);
        UserDtoResponse body = userService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PatchMapping("/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "user update")
    public ResponseEntity<UserDtoResponse> update(@PathVariable("userId") Long userId,
                                                  @Valid @RequestBody UserDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", userId, dto);
        UserDtoResponse body = userService.update(userId, dto);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a user")
    public ResponseEntity<Void> remove(@PathVariable("userId") Long userId) {
        log.debug("DELETE remove() with userId {}", userId);
        userService.remove(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a user by Id")
    public ResponseEntity<UserDtoResponse> findById(@PathVariable("userId") Long userId) {
        log.debug("GET findById() with userId {}", userId);
        UserDtoResponse body = userService.findById(userId);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "getting users page by page")
    public ResponseEntity<List<UserDtoResponse>> findAll(@RequestParam(value = "from", defaultValue = "0") Integer from,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll()");
        List<UserDtoResponse> body = userService.findAll(from, size);
        return ResponseEntity.ok(body);
    }
}
