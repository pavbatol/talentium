package com.pavbatol.talentium.management.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "managements")
public class Management {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "management_id", nullable = false)
    Long id;

    @Column(name = "mn_name", nullable = false)
    String name;
}
