package com.pavbatol.talentium.hh.repository;

import com.pavbatol.talentium.hh.model.Hh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HHRepository extends JpaRepository<Hh, Long>, QuerydslPredicateExecutor<Hh> {
}
