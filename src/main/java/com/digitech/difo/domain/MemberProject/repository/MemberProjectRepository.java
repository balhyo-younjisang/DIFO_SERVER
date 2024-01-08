package com.digitech.difo.domain.MemberProject.repository;

import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.MemberProject.domain.MemberProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
    List<MemberProject> findAllByMember(Member member);
}
