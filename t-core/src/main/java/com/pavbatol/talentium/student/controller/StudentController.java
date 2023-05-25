package com.pavbatol.talentium.student.controller;

import com.pavbatol.talentium.student.dto.StudentDtoRequest;
import com.pavbatol.talentium.student.dto.StudentDtoResponse;
import com.pavbatol.talentium.student.dto.StudentDtoUpdate;
import com.pavbatol.talentium.student.service.StudentService;
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
@RequestMapping("/students")
@Tag(name = "Private: Student", description = "API for working with Students ")
public class StudentController {
    public static final String HAS_ANY_ROLE_CURATOR_ADMIN = "hasAnyRole('STUDENT', 'ADMIN')";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    private final StudentService studentService;

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "add", description = "adding a Student")
    public ResponseEntity<StudentDtoResponse> add(HttpServletRequest servletRequest,
                                                  @Valid @RequestBody StudentDtoRequest dto) {
        log.debug("POST add() with dto: {} ", dto);
        StudentDtoResponse body = studentService.add(servletRequest, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @PatchMapping("/{studentId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "update", description = "Student update")
    public ResponseEntity<StudentDtoResponse> update(HttpServletRequest servletRequest,
                                                     @PathVariable("studentId") Long studentId,
                                                     @Valid @RequestBody StudentDtoUpdate dto) {
        log.debug("PATCH update() with userId {}, dto {}", studentId, dto);
        StudentDtoResponse body = studentService.update(servletRequest, studentId, dto);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(HAS_ANY_ROLE_CURATOR_ADMIN)
    @DeleteMapping("/{studentId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "remove", description = "deleting a Student")
    public ResponseEntity<Void> remove(HttpServletRequest servletRequest,
                                       @PathVariable("studentId") Long studentId) {
        log.debug("DELETE remove() with userId {}", studentId);
        studentService.remove(servletRequest, studentId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping("/{studentId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findById", description = "getting a Student by Id")
    public ResponseEntity<StudentDtoResponse> findById(@PathVariable("studentId") Long studentId) {
        log.debug("GET findById() with userId {}", studentId);
        StudentDtoResponse body = studentService.findById(studentId);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "findAll", description = "find all Students getting page by page")
    public ResponseEntity<List<StudentDtoResponse>> findAll(
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with from: {}, size: {}", from, size);
        List<StudentDtoResponse> body = studentService.findAll(from, size);
        return ResponseEntity.ok(body);
    }
}
