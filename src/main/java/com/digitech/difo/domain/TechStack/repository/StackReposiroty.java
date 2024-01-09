package com.digitech.difo.domain.TechStack.repository;

import com.digitech.difo.domain.TechStack.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StackReposiroty extends JpaRepository<Stack, Long> {
    Optional<Stack> findByStackName(String stack);
}
