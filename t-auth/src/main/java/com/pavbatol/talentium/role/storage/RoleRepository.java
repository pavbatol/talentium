package com.pavbatol.talentium.role.storage;


import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

    Collection<Role> findAllByIdIn(Collection<Long> roles);
}
