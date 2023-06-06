package com.pavbatol.talentium.internship.service;

import com.pavbatol.talentium.app.exception.NotFoundException;
import com.pavbatol.talentium.app.util.Checker;
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
import com.pavbatol.talentium.internship.model.enums.InternshipAdminActionState;
import com.pavbatol.talentium.internship.model.enums.InternshipSort;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.filter.InternshipAdminSearchFilter;
import com.pavbatol.talentium.internship.model.filter.InternshipFilterHelper;
import com.pavbatol.talentium.internship.model.filter.InternshipPublicSearchFilter;
import com.pavbatol.talentium.internship.repository.InternshipRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
        Long hhId = getIdByAuthUserIdForHh(servletRequest);
        Internship entity = internshipMapper.toEntity(dto);
        entity.setCreatedOn(LocalDateTime.now());
        entity.setInitiator(new Hh().setId(hhId));
        entity.setConfirmedRequests(0);
        entity.setPublishedOn(null);
        entity.setState(InternshipState.PENDING);
        Internship saved = internshipRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return internshipMapper.toResponseDto(saved);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public InternshipDtoResponse update(HttpServletRequest servletRequest, Long internshipId, InternshipDtoUpdate dto) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Internship entity = Checker.getNonNullObject(internshipRepository, internshipId);
        ServiceUtils.checkIdsEqualOrAdminRole(servletRequest, userId, entity.getInitiator().getUserId(), jwtProvider);
        Internship updated = internshipMapper.updateEntity(dto, entity);
        updated = internshipRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return internshipMapper.toResponseDto(updated);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public InternshipDtoResponse updateState(HttpServletRequest servletRequest, Long internshipId, InternshipAdminActionState actionState) {
        Internship entity = Checker.getNonNullObject(internshipRepository, internshipId);
        switch (entity.getState()) {
            case PENDING:
                entity.setState(actionState == InternshipAdminActionState.PUBLISH_INTERNSHIP
                        ? InternshipState.PUBLISHED
                        : InternshipState.CANCELED);
                break;
            case PUBLISHED:
                if (actionState == InternshipAdminActionState.PUBLISH_INTERNSHIP) {
                    throw new IllegalArgumentException("The internship has already been published");
                } else {
                    throw new IllegalArgumentException("You cannot reject a published internship");
                }
            case CANCELED:
                throw new IllegalArgumentException("The internship has been canceled, all actions are prohibited");
        }
        Internship updated = internshipRepository.save(entity);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return internshipMapper.toResponseDto(updated);
    }

    @Transactional(readOnly = true)
    @Override
    public InternshipDtoResponse findById(Long internshipId) {
        Internship found = Checker.getNonNullObject(internshipRepository, internshipId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return internshipMapper.toResponseDto(found);
    }

    @Transactional(readOnly = true)
    @Override
    public List<InternshipDtoResponse> findAll(InternshipPublicSearchFilter filter,
                                               Integer from,
                                               Integer size,
                                               InternshipSort internshipSort) {
        Sort sort = internshipSort.sort;
        BooleanBuilder booleanBuilder = InternshipFilterHelper.getInternshipPublicBooleanBuilder(filter);
        PageRequest pageable = PageRequest.of(from, size, sort);
        Page<Internship> page = internshipRepository.findAll(booleanBuilder, pageable);
        log.debug("Found {}-count: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getOffset(), page.getSize(), page.getSort());
        return internshipMapper.toDtos(page.getContent());
    }

    @Transactional(readOnly = true)
    @Override
    public List<InternshipDtoResponse> adminFindAll(InternshipAdminSearchFilter filter,
                                                    Integer from,
                                                    Integer size,
                                                    InternshipSort internshipSort) {
        Sort sort = internshipSort.sort;
        BooleanBuilder booleanBuilder = InternshipFilterHelper.getInternshipAdminBooleanBuilder(filter);
        PageRequest pageable = PageRequest.of(from, size, sort);
        Page<Internship> page = internshipRepository.findAll(booleanBuilder, pageable);
        log.debug("Found {}-count: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getOffset(), page.getSize(), page.getSort());
        return internshipMapper.toDtos(page.getContent());
    }

    private Long getIdByAuthUserIdForHh(HttpServletRequest servletRequest) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        HhDtoShort hhDtoShort = hhRepository.findByUserId(userId).orElseThrow(() ->
                new NotFoundException(String.format("Not found %s with userId: %s", Hh.class.getSimpleName(), userId)));
        return hhDtoShort.getId();
    }
}
