package com.example.spring_boot_backend.repository;

import com.example.spring_boot_backend.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long> {
}
