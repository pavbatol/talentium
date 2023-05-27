package com.pavbatol.talentium.hh.repository;

import com.pavbatol.talentium.hh.dto.HhDtoShort;
import com.pavbatol.talentium.hh.model.Hh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface HHRepository extends JpaRepository<Hh, Long>, QuerydslPredicateExecutor<Hh> {
    Optional<HhDtoShort> findByUserId(Long authUserId);
}
