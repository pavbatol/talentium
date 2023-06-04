package com.pavbatol.talentium.role.mapper;

import com.pavbatol.talentium.app.util.BaseMapper;
import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.shared.auth.dto.RoleDto;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDto> {

    @Override
    @Mapping(target = "name", source = "roleName")
    RoleDto toDto(Role entity);

    @Override
    @Mapping(target = "roleName", source = "name", qualifiedByName = "setRoleName")
    Role toEntity(RoleDto dto);

    @Override
    @Mapping(target = "roleName", source = "name", qualifiedByName = "setRoleName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role updateEntity(RoleDto dto, @MappingTarget Role targetEntity);

    @Named("setRoleName")
    default RoleName toRoleName(String name) {
        return name == null ? null : RoleName.of(name);
    }
}
