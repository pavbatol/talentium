package com.pavbatol.talentium.candidate.model;

import com.pavbatol.talentium.app.enums.Level;
import com.pavbatol.talentium.app.enums.Position;
import com.pavbatol.talentium.app.util.BaseUser;
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
@Table(name = "candidates")
public class Candidate implements BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id", nullable = false)
    Long id;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name")
    String secondName;

    Position position = Position.CANDIDATE;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    Level level;
}
