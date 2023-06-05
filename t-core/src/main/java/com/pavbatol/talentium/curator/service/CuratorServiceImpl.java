package com.pavbatol.talentium.curator.service;

import com.pavbatol.talentium.app.exception.ValidationException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.app.util.ServiceUtils;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.curator.dto.CuratorDtoRequest;
import com.pavbatol.talentium.curator.dto.CuratorDtoResponse;
import com.pavbatol.talentium.curator.dto.CuratorDtoUpdate;
import com.pavbatol.talentium.curator.mapper.CuratorMapper;
import com.pavbatol.talentium.curator.model.Curator;
import com.pavbatol.talentium.curator.repository.CuratorRepository;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuratorServiceImpl implements CuratorService {
    private static final String ENTITY_SIMPLE_NAME = Curator.class.getSimpleName();
    private final JwtProvider jwtProvider;
    private final CuratorMapper curatorMapper;
    private final CuratorRepository curatorRepository;

    @Override
    public CuratorDtoResponse add(HttpServletRequest servletRequest, CuratorDtoRequest dto) {
        Long userId;
        if (!ServiceUtils.hasRole(RoleName.CURATOR, servletRequest, jwtProvider)) {
            userId = dto.getUserId();
            if (Objects.isNull(userId)) {
                throw new ValidationException(String.format("For a non-%s, you must specify the userId from the auth-service", ENTITY_SIMPLE_NAME));
            }
        } else {
            userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        }
        Curator entity = curatorMapper.toEntity(dto, userId);
        entity.setRegisteredOn(LocalDateTime.now());
        Curator saved = curatorRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return curatorMapper.toResponseDto(saved);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public CuratorDtoResponse update(HttpServletRequest servletRequest, Long curatorId, CuratorDtoUpdate dto) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Curator entity = Checker.getNonNullObject(curatorRepository, curatorId);
        ServiceUtils.checkIdsEqualOrAdminRole(servletRequest, userId, entity.getUserId(), jwtProvider);
        Curator updated = curatorMapper.updateEntity(dto, entity);
        updated = curatorRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return curatorMapper.toResponseDto(updated);
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long curatorId) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Curator entity = Checker.getNonNullObject(curatorRepository, curatorId);
        ServiceUtils.checkIdsEqualOrAdminRole(servletRequest, userId, entity.getUserId(), jwtProvider);
        entity.setDeleted(true);
        curatorRepository.save(entity);
        log.debug("Marked as removed {} by id #{}", ENTITY_SIMPLE_NAME, curatorId);
    }

    @Transactional
    @Override
    public CuratorDtoResponse findById(Long curatorId) {
        Curator found = Checker.getNonNullObject(curatorRepository, curatorId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return curatorMapper.toResponseDto(found);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CuratorDtoResponse> findAll(Integer from, Integer size) {
        Page<Curator> page = curatorRepository.findAll(PageRequest.of(from, size));
        log.debug("Found {}-count: {}, from: {}, size: {}", ENTITY_SIMPLE_NAME, page.getTotalElements(), from, size);
        return curatorMapper.toDtos(page.getContent());
    }
}
