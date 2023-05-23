package com.pavbatol.talentium.hh.service;

import com.pavbatol.talentium.app.exception.NotEnoughRightsException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.auth.jwt.JwtUtils;
import com.pavbatol.talentium.auth.role.model.RoleName;
import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.mapper.HhMapper;
import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.hh.model.HhFilter;
import com.pavbatol.talentium.hh.model.HhSort;
import com.pavbatol.talentium.hh.repository.HHRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public HhDtoResponse add(HttpServletRequest servletRequest, HhDtoRequest dto) {
        Long userId = getUserId(servletRequest);
        Hh entity = hhMapper.toEntity(dto, userId);
        entity.setRegisteredOn(LocalDateTime.now());
        Hh saved = hhRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return hhMapper.toResponseDto(saved);
    }

    @Override
    public HhDtoResponse update(HttpServletRequest servletRequest, Long hhId, HhDtoUpdate dto) {
        Long userId = getUserId(servletRequest);
        Hh entity = Checker.getNonNullObject(hhRepository, hhId);
        checkIdsEqualOrAdminRole(servletRequest, userId, entity);
        Hh updated = hhMapper.updateEntity(dto, entity);
        updated = hhRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return hhMapper.toResponseDto(updated);
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long hhId) {
        Long userId = getUserId(servletRequest);
        Hh entity = Checker.getNonNullObject(hhRepository, hhId);
        checkIdsEqualOrAdminRole(servletRequest, userId, entity);
        entity.setDeleted(true);
        hhRepository.save(entity);
        log.debug("Marked as removed {} by id #{}", ENTITY_SIMPLE_NAME, hhId);
    }

    @Override
    public HhDtoResponse findById(Long hhId) {
        Hh found = getNonNullObject(hhRepository, hhId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return hhMapper.toResponseDto(found);
    }

    @Override
    public List<HhDtoResponse> findAll(HttpServletRequest servletRequest, HhFilter hhFilter, HhSort hhSort, Integer from, Integer size) {
        return null;
    }

    private Long getUserId(HttpServletRequest servletRequest) {
        return jwtProvider.geUserId(servletRequest);
    }

    private boolean hasRole(HttpServletRequest servletRequest, RoleName roleName) {
        return JwtUtils.hasRole(servletRequest, roleName.name(), jwtProvider);
    }

    private void checkIdsEqualOrAdminRole(HttpServletRequest servletRequest, Long userId, Hh entity) {
        if (!Objects.equals(entity.getUserId(), userId) && !hasRole(servletRequest, RoleName.ADMIN)) {
            throw new NotEnoughRightsException("Other person's data can only be changed by the Administrator");
        }
    }
}
