package com.pavbatol.talentium.hh.service;

import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.model.HhFilter;
import com.pavbatol.talentium.hh.model.HhSort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class HhServiceImpl implements HhService {
    @Override
    public HhDtoResponse add(HttpServletRequest servletRequest, HhDtoRequest dto) {
        return null;
    }

    @Override
    public HhDtoResponse update(HttpServletRequest servletRequest, Long hhId, HhDtoUpdate dto) {
        return null;
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long hhId) {

    }

    @Override
    public HhDtoResponse findById(Long hhId) {
        return null;
    }

    @Override
    public List<HhDtoResponse> findAll(HttpServletRequest servletRequest, HhFilter hhFilter, HhSort hhSort, Integer from, Integer size) {
        return null;
    }
}
