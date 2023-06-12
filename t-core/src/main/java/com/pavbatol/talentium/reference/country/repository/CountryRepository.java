package com.pavbatol.talentium.reference.country.repository;

import com.pavbatol.talentium.reference.country.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByCode(String code);
}
