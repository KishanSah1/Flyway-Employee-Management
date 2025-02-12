package com.kishan.Flyware.repository;

import com.kishan.Flyware.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
