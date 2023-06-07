package com.pavbatol.talentium.curator.model;

import com.pavbatol.talentium.app.util.BasePerson;
import com.pavbatol.talentium.hh.model.Hh;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "curators")
public class Curator implements BasePerson<Curator> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curator_id", nullable = false)
    Long id;

    @Column(name = "user_id", unique = true)
    Long userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name")
    String secondName;

    @Column(name = "registered_on", nullable = false)
    LocalDateTime registeredOn;

    @Column(name = "deleted")
    Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    @ToString.Exclude
    Hh owner;
}
