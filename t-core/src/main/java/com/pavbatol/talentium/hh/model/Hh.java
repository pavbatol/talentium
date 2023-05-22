package com.pavbatol.talentium.hh.model;

import com.pavbatol.talentium.hh.feedback.model.HhFeedback;
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
@Table(name = "hhs")
public class Hh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hh_id", nullable = false)
    Long id;

    @Column(name = "user_id", unique = true)
    Long userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "second_name")
    String secondName;

    @Column(name = "authority")
    String authority;

    @Column(name = "management")
    String management;

    @Column(name = "address")
    String address;

    @Column(name = "contacts")
    String contacts;

    @Column(name = "rate")
    Integer rate = 0;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "hh")
    List<HhFeedback> feedbacks = new ArrayList<>();

    public void addFeedback(HhFeedback feedback) {
        feedbacks.add(feedback);
        rate = feedbacks.stream()
                .map(HhFeedback::getRate)
                .reduce(0, Integer::sum) / feedbacks.size();
    }
}
