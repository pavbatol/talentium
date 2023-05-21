package com.pavbatol.talentium.student.model;

import com.pavbatol.talentium.app.enums.Level;
import com.pavbatol.talentium.app.enums.Position;
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
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    Long id;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name")
    String secondName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    Position position = Position.CANDIDATE;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    Level level;

    @Column(name = "intern_On")
    LocalDateTime internOn;
}
