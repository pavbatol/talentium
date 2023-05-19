package com.pavbatol.talentium.user.mapper;

import com.pavbatol.talentium.app.util.BaseMapper;
import com.pavbatol.talentium.role.mapper.RoleMapper;
import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.user.dto.UserDtoRegistry;
import com.pavbatol.talentium.user.dto.UserDtoRequest;
import com.pavbatol.talentium.user.dto.UserDtoResponse;
import com.pavbatol.talentium.user.dto.UserDtoUpdate;
import com.pavbatol.talentium.user.model.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper extends BaseMapper<User, UserDtoRequest> {
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", source = "enabled", defaultValue = "true")
    User toEntity(UserDtoRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "false")
    @Mapping(target = "registeredOn", ignore = true)
    @Mapping(target = "password", source = "pass")
    @Mapping(target = "roles", source = "role", qualifiedByName = "setRoles")
    User toEntity(UserDtoRegistry dto, String pass, Role role);

    @Named("setRoles")
    default Set<Role> getRoles(Role role) {
        return Set.of(role);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateEntity(UserDtoUpdate dto, @MappingTarget User targetEntity);

    UserDtoResponse toResponseDto(User user);

    List<UserDtoResponse> toResponseDtos(List<User> users);
}
