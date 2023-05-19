package com.pavbatol.talentium.role.service;

import com.pavbatol.talentium.role.dto.RoleDto;
import com.pavbatol.talentium.role.mapper.RoleMapper;
import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.role.model.RoleName;
import com.pavbatol.talentium.role.storage.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.pavbatol.talentium.app.util.Checker.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private static final String ENTITY_SIMPLE_NAME = RoleService.class.getSimpleName();
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto add(RoleDto dto) {
        Role role = roleMapper.toEntity(dto);
        Role saved = roleRepository.save(role);
        log.debug("New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return roleMapper.toDto(saved);
    }

    @Override
    public RoleDto update(Long role_id, RoleDto dto) {
        Role role = getNonNullObject(roleRepository, role_id);
        Role updated = roleMapper.updateEntity(dto, role);
        updated = roleRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return roleMapper.toDto(updated);
    }

    @Override
    public void remove(Long role_id) {
        roleRepository.deleteById(role_id);
        log.debug("Removed {} by id #{}", ENTITY_SIMPLE_NAME, role_id);
    }

    @Override
    public RoleDto findById(Long role_id) {
        Role found = getNonNullObject(roleRepository, role_id);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return roleMapper.toDto(found);
    }

    @Override
    public List<RoleDto> findAll() {
        Iterable<Role> iterable = roleRepository.findAll();
        List<Role> found = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        log.debug("Found {}'s number: {}", ENTITY_SIMPLE_NAME, found.size());
        return roleMapper.toDtos(found);
    }

    @Override
    public List<String> getAcceptableRoles() {
        return Arrays.stream(RoleName.values()).map(RoleName::name).collect(Collectors.toList());
    }
}
