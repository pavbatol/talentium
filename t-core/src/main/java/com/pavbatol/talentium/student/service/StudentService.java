package com.pavbatol.talentium.student.service;

import com.pavbatol.talentium.student.dto.StudentDtoRequest;
import com.pavbatol.talentium.student.dto.StudentDtoResponse;
import com.pavbatol.talentium.student.dto.StudentDtoUpdate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StudentService {
    StudentDtoResponse add(HttpServletRequest servletRequest, StudentDtoRequest dto);

    StudentDtoResponse update(HttpServletRequest servletRequest, Long sStudentId, StudentDtoUpdate dto);

    void remove(HttpServletRequest servletRequest, Long sStudentId);

    StudentDtoResponse findById(Long sStudentId);

    List<StudentDtoResponse> findAll(Integer from, Integer size);
}
