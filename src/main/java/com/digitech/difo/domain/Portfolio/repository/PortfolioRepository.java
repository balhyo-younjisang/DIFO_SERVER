package com.digitech.difo.domain.Portfolio.repository;

import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
