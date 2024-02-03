package com.example.spring_boot_backend.repository;

import com.example.spring_boot_backend.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
