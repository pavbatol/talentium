package com.pavbatol.talentium.mentor.service;

import com.pavbatol.talentium.mentor.dto.MentorDtoRequest;
import com.pavbatol.talentium.mentor.dto.MentorDtoResponse;
import com.pavbatol.talentium.mentor.dto.MentorDtoUpdate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MentorService {
    MentorDtoResponse add(HttpServletRequest servletRequest, MentorDtoRequest dto);

    MentorDtoResponse update(HttpServletRequest servletRequest, Long mentorId, MentorDtoUpdate dto);

    void remove(HttpServletRequest servletRequest, Long mentorId);

    MentorDtoResponse findById(Long mentorId);

    List<MentorDtoResponse> findAll(Integer from, Integer size);
}
