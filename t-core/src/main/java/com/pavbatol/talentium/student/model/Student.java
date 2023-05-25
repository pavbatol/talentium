package com.pavbatol.talentium.student.model;

import com.pavbatol.talentium.student.model.enums.InternLevel;
import com.pavbatol.talentium.student.model.enums.StudentPosition;
import com.pavbatol.talentium.management.model.Management;
import com.pavbatol.talentium.mentor.model.Mentor;
import com.pavbatol.talentium.student.feedback.model.StudentFeedback;
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
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    Long id;

    @Column(name = "user_id", unique = true)
    Long userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name", nullable = false)
    String secondName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    StudentPosition position = StudentPosition.CANDIDATE;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    InternLevel level;

    @Column(name = "intern_On")
    LocalDateTime internOn;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "management_id")
    Management management;

    @Column(name = "rate")
    Integer rate = 0;

    @Column(name = "registered_on", nullable = false)
    LocalDateTime registeredOn;

    @Column(name = "deleted")
    Boolean deleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @ToString.Exclude
    Set<StudentFeedback> feedbacks = new HashSet<>();

    public void addFeedback(StudentFeedback feedback) {
        feedbacks.add(feedback);
        rate = feedbacks.stream()
                .map(StudentFeedback::getRate)
                .reduce(0, Integer::sum) / feedbacks.size();
    }
}
