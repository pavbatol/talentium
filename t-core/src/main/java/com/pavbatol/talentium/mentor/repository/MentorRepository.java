package com.pavbatol.talentium.mentor.repository;

import com.pavbatol.talentium.mentor.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
