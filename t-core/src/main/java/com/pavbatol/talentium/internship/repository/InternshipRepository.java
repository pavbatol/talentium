package com.pavbatol.talentium.internship.repository;

import com.pavbatol.talentium.internship.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface InternshipRepository extends JpaRepository<Internship, Long>, QuerydslPredicateExecutor<Internship> {
}
