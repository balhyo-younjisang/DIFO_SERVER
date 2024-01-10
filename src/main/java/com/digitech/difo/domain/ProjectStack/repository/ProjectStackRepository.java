package com.digitech.difo.domain.ProjectStack.repository;

import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.TechStack.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectStackRepository extends JpaRepository<ProjectStack, Long> {


}
