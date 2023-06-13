package com.pavbatol.talentium.reference.highschool.repository;

import com.pavbatol.talentium.reference.highschool.model.HighSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface HighSchoolRepository extends JpaRepository<HighSchool, Long> {

    @Query(value = "select h.name from HighSchool h where h.name in :names")
    List<String> findAllByNameIn(Collection<String> names);
}
