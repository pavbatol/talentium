package com.pavbatol.talentium.student.repository;

import com.pavbatol.talentium.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
