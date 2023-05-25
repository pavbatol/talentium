package com.pavbatol.talentium.mentor.model;

import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.management.model.Management;
import com.pavbatol.talentium.mentor.feedback.model.MentorFeedback;
import com.pavbatol.talentium.student.model.Student;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "user_id", nullable = false, unique = true)
    Long userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name", nullable = false)
    String secondName;

    @Column(name = "rate")
    Integer rate = 0;

    @Column(name = "registered_on", nullable = false)
    LocalDateTime registeredOn;

    @Column(name = "deleted")
    Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    Hh owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "management_id", nullable = false)
    @ToString.Exclude
    Management management;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mentor")
    @ToString.Exclude
    Set<Student> students = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mentor")
    @ToString.Exclude
    Set<MentorFeedback> feedbacks = new HashSet<>();

    public void addFeedback(MentorFeedback feedback) {
        feedbacks.add(feedback);
        rate = feedbacks.stream()
                .map(MentorFeedback::getRate)
                .reduce(0, Integer::sum) / feedbacks.size();
    }
}
