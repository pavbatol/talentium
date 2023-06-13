package com.pavbatol.talentium.reference.highschool.model;

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
@Table(name = "highschools")
public class HighSchool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "highschool_id", nullable = false)
    Long id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 15, nullable = false)
    SchoolType type;

    @Column(name = "region")
    String region;

    @Column(name = "city")
    String city;

    @Column(name = "phone")
    String phone;

    @Column(name = "website")
    String website;
}
