package com.pavbatol.talentium.internship.service;

import com.pavbatol.talentium.internship.dto.InternshipDtoRequest;
import com.pavbatol.talentium.internship.dto.InternshipDtoResponse;
import com.pavbatol.talentium.internship.dto.InternshipDtoUpdate;
import com.pavbatol.talentium.internship.model.enums.InternshipSort;
import com.pavbatol.talentium.internship.model.filter.InternshipAdminSearchFilter;
import com.pavbatol.talentium.internship.model.filter.InternshipSearchFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface InternshipService {
    InternshipDtoResponse add(HttpServletRequest servletRequest, InternshipDtoRequest dto);

    InternshipDtoResponse update(HttpServletRequest servletRequest, Long internshipId, InternshipDtoUpdate dto);

    void remove(HttpServletRequest servletRequest, Long internshipId);

    InternshipDtoResponse findById(Long internshipId);

    List<InternshipDtoResponse> findAll(InternshipSearchFilter filter,
                                        Integer from,
                                        Integer size,
                                        InternshipSort sort);

    List<InternshipDtoResponse> adminFindAll(InternshipAdminSearchFilter filter,
                                             Integer from,
                                             Integer size,
                                             InternshipSort sort);
}
