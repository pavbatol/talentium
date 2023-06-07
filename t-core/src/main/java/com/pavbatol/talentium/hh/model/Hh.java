package com.pavbatol.talentium.hh.model;

import com.pavbatol.talentium.app.util.BasePerson;
import com.pavbatol.talentium.app.util.BasePersonDto;
import com.pavbatol.talentium.hh.feedback.model.HhFeedback;
import com.pavbatol.talentium.management.model.Management;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "hhs")
public class Hh implements BasePerson<Hh> {
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

    @Column(name = "second_name", nullable = false)
    String secondName;

    @Column(name = "authority", nullable = false)
    String authority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hhs_managements",
            joinColumns = @JoinColumn(name = "hh_id"),
            inverseJoinColumns = @JoinColumn(name = "management_id"))
    @ToString.Exclude
    Set<Management> managements;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "contacts", nullable = false)
    String contacts;

    @Column(name = "registered_on", nullable = false)
    LocalDateTime registeredOn;

    @Column(name = "rate")
    Integer rate = 0;

    @Column(name = "deleted")
    Boolean deleted = false;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hh")
    List<HhFeedback> feedbacks = new ArrayList<>();

    public void addFeedback(HhFeedback feedback) {
        feedbacks.add(feedback);
        rate = feedbacks.stream()
                .map(HhFeedback::getRate)
                .reduce(0, Integer::sum) / feedbacks.size();
    }
}
