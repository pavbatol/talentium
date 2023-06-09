package com.pavbatol.talentium.hh.service;

import com.pavbatol.talentium.app.client.AuthUserClient;
import com.pavbatol.talentium.app.exception.ValidationException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.app.util.ServiceUtils;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.mapper.HhMapper;
import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.hh.model.HhFilter;
import com.pavbatol.talentium.hh.model.HhSort;
import com.pavbatol.talentium.hh.model.QHh;
import com.pavbatol.talentium.hh.repository.HHRepository;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.pavbatol.talentium.app.util.Checker.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhServiceImpl implements HhService {
    private static final String ENTITY_SIMPLE_NAME = Hh.class.getSimpleName();
    private final JwtProvider jwtProvider;
    private final HhMapper hhMapper;
    private final HHRepository hhRepository;
    private final AuthUserClient authUserClient;

    @Override
    public HhDtoResponse add(HttpServletRequest servletRequest, HhDtoRequest dto) {
        Long userId;
        if (!ServiceUtils.hasRole(RoleName.HH, servletRequest, jwtProvider)) {
            userId = dto.getUserId();
            if (Objects.isNull(userId)) {
                throw new ValidationException(String.format("For a non-%s, you must specify the userId from the auth-service", ENTITY_SIMPLE_NAME));
            }
        } else {
            userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        }
        Hh entity = hhMapper.toEntity(dto, userId);
        entity.setRegisteredOn(LocalDateTime.now());
        Hh saved = hhRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return hhMapper.toResponseDto(saved);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public HhDtoResponse update(HttpServletRequest servletRequest, Long hhId, HhDtoUpdate dto) {
        Long userId = getUserId(servletRequest);
        Hh entity = Checker.getNonNullObject(hhRepository, hhId);
        ServiceUtils.checkIdsEqualOrAdminRole(userId, entity.getUserId(), servletRequest, jwtProvider);
        ServiceUtils.updateUserInsensitiveInAuthService(dto, entity, servletRequest, jwtProvider, authUserClient);
        Hh updated = hhMapper.updateEntity(dto, entity);
        updated = hhRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return hhMapper.toResponseDto(updated);
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long hhId) {
        Long userId = getUserId(servletRequest);
        Hh entity = Checker.getNonNullObject(hhRepository, hhId);
        ServiceUtils.checkIdsEqualOrAdminRole(userId, entity.getUserId(), servletRequest, jwtProvider);
        entity.setDeleted(true);
        hhRepository.save(entity);
        log.debug("Marked as removed {} by id #{}", ENTITY_SIMPLE_NAME, hhId);
    }

    @Transactional(readOnly = true)
    @Override
    public HhDtoResponse findById(Long hhId) {
        Hh found = getNonNullObject(hhRepository, hhId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return hhMapper.toResponseDto(found);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HhDtoResponse> findAll(HttpServletRequest servletRequest, HhFilter hhFilter, HhSort hhSort, Integer from, Integer size) {
        Sort sort = hhSort.sort;
        BooleanBuilder booleanBuilder = makeBooleanBuilder(hhFilter);
        PageRequest pageable = PageRequest.of(from, size, sort);
        Page<Hh> page = hhRepository.findAll(booleanBuilder, pageable);
        log.debug("Found {}-count: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getOffset(), page.getSize(), page.getSort());
        return hhMapper.toDtos(page.getContent());
    }

    private Long getUserId(HttpServletRequest servletRequest) {
        return jwtProvider.geUserId(servletRequest);
    }

    private BooleanBuilder makeBooleanBuilder(@NonNull HhFilter filter) {
        java.util.function.Predicate<Object> isNullOrEmpty = obj ->
                Objects.isNull(obj) || (obj instanceof Collection && ((Collection<?>) obj).isEmpty());
        QHh qHh = QHh.hh;
        return new BooleanBuilder()
                .and(!isNullOrEmpty.test(filter.getManagement()) ? qHh.managements.any().name.containsIgnoreCase(filter.getManagement()) : null)
                .and(!isNullOrEmpty.test(filter.getAuthority()) ? qHh.authority.containsIgnoreCase(filter.getAuthority()) : null)
                .and(!isNullOrEmpty.test(filter.getContacts()) ? qHh.contacts.containsIgnoreCase(filter.getContacts()) : null)
                .and(!isNullOrEmpty.test(filter.getAddress()) ? qHh.address.containsIgnoreCase(filter.getAddress()) : null);
    }
}
