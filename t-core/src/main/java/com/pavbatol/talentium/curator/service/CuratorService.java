package com.pavbatol.talentium.curator.service;

import com.pavbatol.talentium.curator.dto.CuratorDtoRequest;
import com.pavbatol.talentium.curator.dto.CuratorDtoResponse;
import com.pavbatol.talentium.curator.dto.CuratorDtoUpdate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CuratorService {
    CuratorDtoResponse add(HttpServletRequest servletRequest, CuratorDtoRequest dto);

    CuratorDtoResponse update(HttpServletRequest servletRequest, Long curatorId, CuratorDtoUpdate dto);

    void remove(HttpServletRequest servletRequest, Long curatorId);

    CuratorDtoResponse findById(Long curatorId);

    List<CuratorDtoResponse> findAll(Integer from, Integer size);
}
