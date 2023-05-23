package com.pavbatol.talentium.management.repository;

import com.pavbatol.talentium.management.model.Management;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementRepository extends JpaRepository<Management, Long> {
}
