package com.example.spring_boot_backend.repository;

import com.example.spring_boot_backend.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response,Long> {
}
