package com.digitech.difo.domain.Project.repository;

import com.digitech.difo.domain.Project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
