package com.pavbatol.talentium.internship.model;

import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.internship.model.enums.InternshipState;
import com.pavbatol.talentium.internship.model.enums.WorkingDayDuration;
import com.pavbatol.talentium.management.model.Management;
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
@Table(name = "internships")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internships_id", nullable = false)
    Long id;

    @Column(name = "title", length = 120, nullable = false)
    String title;

    @Column(name = "annotation", length = 2000, nullable = false)
    String annotation;

    @Column(name = "description", length = 7000, nullable = false)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    @ToString.Exclude
    Hh initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "management_id", nullable = false)
    @ToString.Exclude
    Management management;

    @Column(name = "latitude", nullable = false)
    Double latitude;

    @Column(name = "longitude", nullable = false)
    Double longitude;

    @Column(name = "age_from", nullable = false)
    Integer ageFrom;

    @Column(name = "age_to", nullable = false)
    Integer ageTo;

    @Column(name = "participantLimit", columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    Integer participantLimit;

    @Column(name = "confirmed_requests", columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    Long confirmedRequests;

    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    WorkingDayDuration dayDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    InternshipState state;
}
