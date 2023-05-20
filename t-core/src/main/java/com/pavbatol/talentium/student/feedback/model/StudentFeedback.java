package com.pavbatol.talentium.student.feedback.model;

import com.pavbatol.talentium.mentor.model.Mentor;
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
@Table(name = "student_feedbacks")
public class StudentFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    Mentor reviewer;

    @Column(name = "text", nullable = false)
    String text;

    @Column(name = "rate", nullable = false)
    Integer rate;
}
