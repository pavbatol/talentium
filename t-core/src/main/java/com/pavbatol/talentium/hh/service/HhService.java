package com.pavbatol.talentium.hh.service;

import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.model.HhFilter;
import com.pavbatol.talentium.hh.model.HhSort;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface HhService {
    HhDtoResponse add(HttpServletRequest servletRequest, HhDtoRequest dto);

    HhDtoResponse update(HttpServletRequest servletRequest, Long hhId, HhDtoUpdate dto);

    void remove(HttpServletRequest servletRequest, Long hhId);

    HhDtoResponse findById(Long hhId);

    List<HhDtoResponse> findAll(HttpServletRequest servletRequest, HhFilter hhFilter, HhSort hhSort, Integer from, Integer size);
}
