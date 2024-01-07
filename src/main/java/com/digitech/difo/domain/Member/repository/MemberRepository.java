package com.digitech.difo.domain.Member.repository;

import com.digitech.difo.global.auth.GoogleOAuthDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.digitech.difo.domain.Member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    Member findByEmail(String email);
}