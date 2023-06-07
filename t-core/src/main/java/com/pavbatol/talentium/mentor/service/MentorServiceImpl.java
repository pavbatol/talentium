package com.pavbatol.talentium.mentor.service;

import com.pavbatol.talentium.app.exception.ValidationException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.app.util.ServiceUtils;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.mentor.dto.MentorDtoRequest;
import com.pavbatol.talentium.mentor.dto.MentorDtoResponse;
import com.pavbatol.talentium.mentor.dto.MentorDtoUpdate;
import com.pavbatol.talentium.mentor.mapper.MentorMapper;
import com.pavbatol.talentium.mentor.model.Mentor;
import com.pavbatol.talentium.mentor.repository.MentorRepository;
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
public class MentorServiceImpl implements MentorService {
    private static final String ENTITY_SIMPLE_NAME = Mentor.class.getSimpleName();
    private final JwtProvider jwtProvider;
    private final MentorMapper mentorMapper;
    private final MentorRepository mentorRepository;

    @Override
    public MentorDtoResponse add(HttpServletRequest servletRequest, MentorDtoRequest dto) {
        Long userId;
        if (!ServiceUtils.hasRole(RoleName.MENTOR, servletRequest, jwtProvider)) {
            userId = dto.getUserId();
            if (Objects.isNull(userId)) {
                throw new ValidationException(String.format("For a non-%s, you must specify the userId from the auth-service", ENTITY_SIMPLE_NAME));
            }
        } else {
            userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        }
        Mentor entity = mentorMapper.toEntity(dto, userId);
        entity.setRegisteredOn(LocalDateTime.now());
        Mentor saved = mentorRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return mentorMapper.toResponseDto(saved);
        // TODO: 25.05.2023 Check having management only from owner
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public MentorDtoResponse update(HttpServletRequest servletRequest, Long mentorId, MentorDtoUpdate dto) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Mentor entity = Checker.getNonNullObject(mentorRepository, mentorId);
        ServiceUtils.checkIdsEqualOrAdminRole(userId, entity.getUserId(), servletRequest, jwtProvider);
        Mentor updated = mentorMapper.updateEntity(dto, entity);
        updated = mentorRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return mentorMapper.toResponseDto(updated);
        // TODO: 25.05.2023 Check having management only from owner
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long mentorId) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Mentor entity = Checker.getNonNullObject(mentorRepository, mentorId);
        ServiceUtils.checkIdsEqualOrAdminRole(userId, entity.getUserId(), servletRequest, jwtProvider);
        entity.setDeleted(true);
        mentorRepository.save(entity);
        log.debug("Marked as removed {} by id #{}", ENTITY_SIMPLE_NAME, mentorId);
    }

    @Transactional(readOnly = true)
    @Override
    public MentorDtoResponse findById(Long mentorId) {
        Mentor found = Checker.getNonNullObject(mentorRepository, mentorId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return mentorMapper.toResponseDto(found);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MentorDtoResponse> findAll(Integer from, Integer size) {
        Page<Mentor> page = mentorRepository.findAll(PageRequest.of(from, size));
        log.debug("Found {}-count: {}, from: {}, size: {}", ENTITY_SIMPLE_NAME, page.getTotalElements(), from, size);
        return mentorMapper.toDtos(page.getContent());
    }
}
