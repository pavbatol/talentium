package com.pavbatol.talentium.management.service;

import com.pavbatol.talentium.management.dto.ManagementDtoRequest;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import com.pavbatol.talentium.management.dto.ManagementDtoUpdate;
import com.pavbatol.talentium.management.mapper.ManagementMapper;
import com.pavbatol.talentium.management.model.Management;
import com.pavbatol.talentium.management.repository.ManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pavbatol.talentium.app.util.Checker.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService {
    private static final String ENTITY_SIMPLE_NAME = Management.class.getSimpleName();
    private final ManagementMapper managementMapper;
    private final ManagementRepository managementRepository;

    @Override
    public ManagementDtoResponse add(ManagementDtoRequest dto) {
        Management entity = managementMapper.toEntity(dto);
        Management saved = managementRepository.save(entity);
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return managementMapper.toResponseDto(saved);
    }

    @Override
    public ManagementDtoResponse update(Long managementId, ManagementDtoUpdate dto) {
        Management management = getNonNullObject(managementRepository, managementId);
        Management updated = managementMapper.updateEntity(dto, management);
        updated = managementRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return managementMapper.toResponseDto(updated);
    }

    @Override
    public void remove(Long managementId) {
        throw new UnsupportedOperationException("The remove method is not supported");
    }

    @Override
    public ManagementDtoResponse findById(Long managementId) {
        Management found = getNonNullObject(managementRepository, managementId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return managementMapper.toResponseDto(found);
    }

    @Override
    public List<ManagementDtoResponse> findAll() {
        List<Management> entities = managementRepository.findAll();
        log.debug("Found number of {}'s: {}", ENTITY_SIMPLE_NAME, entities.size());
        return managementMapper.toDtos(entities);
    }
}
