package com.pavbatol.talentium.management.service;

import com.pavbatol.talentium.management.dto.ManagementDtoRequest;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import com.pavbatol.talentium.management.dto.ManagementDtoUpdate;

import java.util.List;

public interface ManagementService {
    ManagementDtoResponse add(ManagementDtoRequest dto);

    ManagementDtoResponse update(Long managementId, ManagementDtoUpdate dto);

    void remove(Long managementId);

    ManagementDtoResponse findById(Long managementId);

    List<ManagementDtoResponse> findAll();
}
