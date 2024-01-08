package com.digitech.difo.domain.Project.repository;

import com.digitech.difo.domain.Project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
