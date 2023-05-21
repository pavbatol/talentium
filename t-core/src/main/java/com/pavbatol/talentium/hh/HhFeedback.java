package com.pavbatol.talentium.hh;

import com.pavbatol.talentium.hh.model.Hh;
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
@Table(name = "hh_feedbacks")
public class HhFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "hh_id", nullable = false)
    Hh hh;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @Column(name = "text", nullable = false)
    String text;

    @Column(name = "rate", nullable = false)
    Integer rate;
}
