package com.pavbatol.talentium.internship.service;

import com.pavbatol.talentium.app.exception.NotFoundException;
import com.pavbatol.talentium.app.util.ServiceUtils;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.hh.dto.HhDtoShort;
import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.hh.repository.HHRepository;
import com.pavbatol.talentium.internship.dto.InternshipDtoRequest;
import com.pavbatol.talentium.internship.dto.InternshipDtoResponse;
import com.pavbatol.talentium.internship.dto.InternshipDtoUpdate;
import com.pavbatol.talentium.internship.mapper.InternshipMapper;
import com.pavbatol.talentium.internship.model.Internship;
import com.pavbatol.talentium.internship.model.enums.InternshipSort;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.filter.InternshipAdminSearchFilter;
import com.pavbatol.talentium.internship.model.filter.InternshipSearchFilter;
import com.pavbatol.talentium.internship.repository.InternshipRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private static final String ENTITY_SIMPLE_NAME = Internship.class.getSimpleName();
    private final JwtProvider jwtProvider;
    private final InternshipMapper internshipMapper;
    private final InternshipRepository internshipRepository;
    private final HHRepository hhRepository;

    @Override
    public InternshipDtoResponse add(HttpServletRequest servletRequest, InternshipDtoRequest dto) {
        Long hhId = getIdByAuthUserId(servletRequest);
        Internship entity = internshipMapper.toEntity(dto);
        entity.setCreatedOn(LocalDateTime.now());
        entity.setInitiator(new Hh().setId(hhId));
        entity.setPublishedOn(null);
        entity.setState(InternshipState.PENDING);
        Internship saved = internshipRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return internshipMapper.toResponseDto(saved);
    }


    @Override
    public InternshipDtoResponse update(HttpServletRequest servletRequest, Long internshipId, InternshipDtoUpdate dto) {
        return null;
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long internshipId) {

    }

    @Override
    public InternshipDtoResponse findById(Long internshipId) {
        return null;
    }

    @Override
    public List<InternshipDtoResponse> findAll(InternshipSearchFilter filter, Integer from, Integer size, InternshipSort sort) {
        return null;
    }

    @Override
    public List<InternshipDtoResponse> adminFindAll(InternshipAdminSearchFilter filter, Integer from, Integer size, InternshipSort sort) {
        return null;
    }

    private Long getIdByAuthUserId(HttpServletRequest servletRequest) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        HhDtoShort hhDtoShort = hhRepository.findAllByUserId(userId).orElseThrow(() ->
                new NotFoundException(String.format("Not found %s with userId: %s", ENTITY_SIMPLE_NAME, userId)));
        return hhDtoShort.getId();
    }

}
