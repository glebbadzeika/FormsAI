package com.example.spring_boot_backend.repository;

import com.example.spring_boot_backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
