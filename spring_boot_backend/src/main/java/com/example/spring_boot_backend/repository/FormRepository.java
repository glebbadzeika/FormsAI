package com.example.spring_boot_backend.repository;

import com.example.spring_boot_backend.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form,Long> {
}
