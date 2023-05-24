package com.pavbatol.talentium.need.model;

import com.pavbatol.talentium.hh.model.Hh;
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
@Table(name = "needs")
public class Need {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "need_id", nullable = false)
    Long id;

//    Hh owner;
//
//    Management management;
//
//    LocalDateTime createdOn;
//    LocalDateTime startOn;
//    LocalDateTime endOn;
//
//    Boolean approved = false;
//
//
//
//    @Column(name = "first_name", nullable = false)
//    String firstName;
//
//    @Column(name = "second_name")
//    String secondName;

}
