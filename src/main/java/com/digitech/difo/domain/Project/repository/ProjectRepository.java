package com.digitech.difo.domain.Project.repository;

import com.digitech.difo.domain.Project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByLikes();
}
