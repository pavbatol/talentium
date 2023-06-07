package com.pavbatol.talentium.student.service;

import com.pavbatol.talentium.app.client.AuthUserClient;
import com.pavbatol.talentium.app.exception.ValidationException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.app.util.ServiceUtils;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import com.pavbatol.talentium.student.dto.StudentDtoRequest;
import com.pavbatol.talentium.student.dto.StudentDtoResponse;
import com.pavbatol.talentium.student.dto.StudentDtoUpdate;
import com.pavbatol.talentium.student.mapper.StudentMapper;
import com.pavbatol.talentium.student.model.Student;
import com.pavbatol.talentium.student.repository.StudentRepository;
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
public class StudentServiceImpl implements StudentService {
    private static final String ENTITY_SIMPLE_NAME = Student.class.getSimpleName();
    private final JwtProvider jwtProvider;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final AuthUserClient authUserClient;

    @Override
    public StudentDtoResponse add(HttpServletRequest servletRequest, StudentDtoRequest dto) {
        Long userId;
        if (!ServiceUtils.hasRole(RoleName.CANDIDATE, servletRequest, jwtProvider)) {
            userId = dto.getUserId();
            if (Objects.isNull(userId)) {
                throw new ValidationException(String.format("For a non-%s, you must specify the userId from the auth-service", ENTITY_SIMPLE_NAME));
            }
        } else {
            userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        }
        Student entity = studentMapper.toEntity(dto, userId);
        entity.setRegisteredOn(LocalDateTime.now());
        Student saved = studentRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return studentMapper.toResponseDto(saved);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public StudentDtoResponse update(HttpServletRequest servletRequest, Long sStudentId, StudentDtoUpdate dto) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Student entity = Checker.getNonNullObject(studentRepository, sStudentId);
        ServiceUtils.checkIdsEqualOrAdminRole(userId, entity.getUserId(), servletRequest, jwtProvider);
        ServiceUtils.updateUserInsensitiveInAuthService(dto, entity, servletRequest, jwtProvider, authUserClient);
        Student updated = studentMapper.updateEntity(dto, entity);
        updated = studentRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return studentMapper.toResponseDto(updated);
    }

    @Override
    public void remove(HttpServletRequest servletRequest, Long sStudentId) {
        Long userId = ServiceUtils.getUserId(servletRequest, jwtProvider);
        Student entity = Checker.getNonNullObject(studentRepository, sStudentId);
        ServiceUtils.checkIdsEqualOrAdminRole(userId, entity.getUserId(), servletRequest, jwtProvider);
        entity.setDeleted(true);
        studentRepository.save(entity);
        log.debug("Marked as removed {} by id #{}", ENTITY_SIMPLE_NAME, sStudentId);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentDtoResponse findById(Long sStudentId) {
        Student found = Checker.getNonNullObject(studentRepository, sStudentId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return studentMapper.toResponseDto(found);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDtoResponse> findAll(Integer from, Integer size) {
        Page<Student> page = studentRepository.findAll(PageRequest.of(from, size));
        log.debug("Found {}-count: {}, from: {}, size: {}", ENTITY_SIMPLE_NAME, page.getTotalElements(), from, size);
        return studentMapper.toDtos(page.getContent());
    }
}
