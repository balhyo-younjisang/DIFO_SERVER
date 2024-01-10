package com.digitech.difo.domain.MemberProject.repository;

import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.MemberProject.domain.MemberProject;
import com.digitech.difo.domain.Project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Stack;

public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
    List<MemberProject> findAllByMember(Member member);


}
