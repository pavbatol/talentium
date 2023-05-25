package com.pavbatol.talentium.mentor.controller;

import com.pavbatol.talentium.mentor.dto.MentorDtoRequest;
import com.pavbatol.talentium.mentor.dto.MentorDtoResponse;
import com.pavbatol.talentium.mentor.dto.MentorDtoUpdate;
import com.pavbatol.talentium.mentor.service.MentorService;
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
@RequestMapping("/mentors")
@Tag(name = "Private: Mentor", description = "API for working with Mentors ")
public class MentorController {
    public static final String HAS_ANY_ROLE_CURATOR_ADMIN = "hasAnyRole('MENTOR', 'ADMIN')";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    private final MentorService mentorService;

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a Mentor")
    public ResponseEntity<MentorDtoResponse> add(HttpServletRequest servletRequest,
                                                 @Valid @RequestBody MentorDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        MentorDtoResponse body = mentorService.add(servletRequest, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PatchMapping("/{mentorId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "Mentor update")
    public ResponseEntity<MentorDtoResponse> update(HttpServletRequest servletRequest,
                                                     @PathVariable("mentorId") Long mentorId,
                                                     @Valid @RequestBody MentorDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", mentorId, dto);
        MentorDtoResponse body = mentorService.update(servletRequest, mentorId, dto);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @DeleteMapping("/{mentorId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a Mentor")
    public ResponseEntity<Void> remove(HttpServletRequest servletRequest,
                                       @PathVariable("mentorId") Long mentorId) {
        log.debug("DELETE remove() with userId {}", mentorId);
        mentorService.remove(servletRequest, mentorId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping("/{mentorId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a Mentor by Id")
    public ResponseEntity<MentorDtoResponse> findById(@PathVariable("mentorId") Long mentorId) {
        log.debug("GET findById() with userId {}", mentorId);
        MentorDtoResponse body = mentorService.findById(mentorId);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "find all Mentors getting page by page")
    public ResponseEntity<List<MentorDtoResponse>> findAll(
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with from: {}, size: {}", from, size);
        List<MentorDtoResponse> body = mentorService.findAll(from, size);
        return ResponseEntity.ok(body);
    }
}
