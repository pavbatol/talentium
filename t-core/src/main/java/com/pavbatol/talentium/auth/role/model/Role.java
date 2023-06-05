package com.pavbatol.talentium.auth.role.model;

import com.pavbatol.talentium.shared.auth.model.RoleName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 50, nullable = false, unique = true)
    RoleName roleName;
}
