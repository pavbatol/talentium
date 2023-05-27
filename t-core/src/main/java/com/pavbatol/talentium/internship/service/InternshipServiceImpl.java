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
import com.pavbatol.talentium.internship.model.QInternship;
import com.pavbatol.talentium.internship.model.enums.InternshipSort;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.filter.InternshipAdminSearchFilter;
import com.pavbatol.talentium.internship.model.filter.InternshipSearchFilter;
import com.pavbatol.talentium.internship.repository.InternshipRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Internship entity = Checker.getNonNullObject(internshipRepository, internshipId);
        ServiceUtils.checkIdsEqualOrAdminRole(servletRequest, userId, entity.getInitiator().getUserId(), jwtProvider);
        Internship updated = internshipMapper.updateEntity(dto, entity);
        updated = internshipRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return internshipMapper.toResponseDto(updated);
    }

    @Override
    public InternshipDtoResponse findById(Long internshipId) {
        Internship found = Checker.getNonNullObject(internshipRepository, internshipId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return internshipMapper.toResponseDto(found);
    }

    @Override
    public List<InternshipDtoResponse> findAll(InternshipSearchFilter filter,
                                               Integer from,
                                               Integer size,
                                               InternshipSort internshipSort) {
        Sort sort;
        switch (internshipSort) {
            case START_DATE:
                sort = Sort.by("startDate").ascending();
                break;
            case TITLE:
                sort = Sort.by("title").ascending();
                break;
            case AGE_FROM:
                sort = Sort.by("ageFrom").ascending();
                break;
            case PUBLISHED_ON:
                sort = Sort.by("publishedOn").ascending();
                break;
            default:
                sort = Sort.by("createdOn").descending();
        }
        BooleanBuilder booleanBuilder = makeBooleanBuilder(filter);
        PageRequest pageable = PageRequest.of(from, size, sort);
        Page<Internship> page = internshipRepository.findAll(booleanBuilder, pageable);
        log.debug("Found {}-count: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getOffset(), page.getSize(), page.getSort());
        return internshipMapper.toDtos(page.getContent());
    }

    @Override
    public List<InternshipDtoResponse> adminFindAll(InternshipAdminSearchFilter filter,
                                                    Integer from,
                                                    Integer size,
                                                    InternshipSort sort) {
        return null;
    }

    private Long getIdByAuthUserId(HttpServletRequest servletRequest) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        HhDtoShort hhDtoShort = hhRepository.findAllByUserId(userId).orElseThrow(() ->
                new NotFoundException(String.format("Not found %s with userId: %s", ENTITY_SIMPLE_NAME, userId)));
        return hhDtoShort.getId();
    }

        private BooleanBuilder makeBooleanBuilder(@NonNull InternshipSearchFilter filter) {
        java.util.function.Predicate<Object> isNullOrEmpty = obj ->
                Objects.isNull(obj) || (obj instanceof Collection && ((Collection<?>) obj).isEmpty());
        QInternship qHh = QInternship.internship;
        return new BooleanBuilder()
                .and(!isNullOrEmpty.test(filter.getManagement()) ? qHh.managements.any().name.containsIgnoreCase(filter.getManagement()) : null)
                .and(!isNullOrEmpty.test(filter.getAuthority()) ? qHh.authority.containsIgnoreCase(filter.getAuthority()) : null)
                .and(!isNullOrEmpty.test(filter.getContacts()) ? qHh.contacts.containsIgnoreCase(filter.getContacts()) : null)
                .and(!isNullOrEmpty.test(filter.getAddress()) ? qHh.address.containsIgnoreCase(filter.getAddress()) : null);
    }
}
