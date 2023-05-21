package com.pavbatol.talentium.mentor.model;

import com.pavbatol.talentium.mentor.feedback.model.MentorFeedback;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mentors")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id", nullable = false)
    Long id;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name")
    String secondName;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mentor")
    List<MentorFeedback> feedbacks = new ArrayList<>();
}