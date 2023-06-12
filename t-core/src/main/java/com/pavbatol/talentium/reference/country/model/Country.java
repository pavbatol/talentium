package com.pavbatol.talentium.reference.country.model;

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
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    Long id;

    @Column(name = "code", length = 2, nullable = false)
    String code;

    @Column(name = "name_en", length = 70, nullable = false)
    String nameEn;

    @Column(name = "name_ru", length = 70, nullable = false)
    String nameRu;
}
