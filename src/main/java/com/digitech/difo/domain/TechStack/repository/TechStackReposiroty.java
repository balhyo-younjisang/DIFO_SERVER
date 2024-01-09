package com.digitech.difo.domain.TechStack.repository;

import com.digitech.difo.domain.TechStack.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechStackReposiroty extends JpaRepository<TechStack, Long> {
    Optional<TechStack> findByStackName(String stack);
}
