package com.pavbatol.talentium.curator.repository;

import com.pavbatol.talentium.curator.model.Curator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuratorRepository extends JpaRepository<Curator, Long> {
}
