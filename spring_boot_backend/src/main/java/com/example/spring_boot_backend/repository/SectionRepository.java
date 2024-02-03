package com.example.spring_boot_backend.repository;

import com.example.spring_boot_backend.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section,Long> {

}
