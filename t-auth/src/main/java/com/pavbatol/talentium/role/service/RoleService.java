package com.pavbatol.talentium.role.service;


import com.pavbatol.talentium.role.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto add(RoleDto role);

    RoleDto update(Long role_id,  RoleDto dto);

    void remove(Long userId);

    RoleDto findById(Long role_id);

    List<RoleDto> findAll();

    List<String > getAcceptableRoles();
}
