package com.pavbatol.talentium.mentor.feedback.model;

import com.pavbatol.talentium.mentor.model.Mentor;
import com.pavbatol.talentium.student.model.Student;
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
@Table(name = "mentor_feedbacks")
public class MentorFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    Mentor mentor;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @Column(name = "text", nullable = false)
    String text;

    @Column(name = "rate", nullable = false)
    Integer rate;
}
