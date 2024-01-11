package com.digitech.difo.domain.Project.domain;

import com.digitech.difo.domain.MemberProject.domain.MemberProject;
import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.TechStack.dto.StackDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long projectId;
    
    @Column(nullable = false)
    private String thumbnail; // 포트폴리오 썸네일

    @Column(nullable = false)
    private String subject; // 웹 개발, 앱 개발 등

    @Column(nullable = false)
    private String projectName; // 프로젝트명

    @Column(nullable = false)
    private String projectIntroduction; // 프로젝트 소개

    @Column(nullable = false)
    private Date startDate; // 프로젝트 시작일

    @Column(nullable = false)
    private Date endDate; // 프로젝트 종료일

    @Column(nullable = false)
    private String githubUrl; // 깃헙 레포

    @Column(nullable = false)
    private String deployUrl; // 배포 url

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String mainContents; // 프로젝트 내용

    @JsonIgnore // 무한 순환을 막기 위해
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberProject> members = new ArrayList<>();

    @JsonIgnore // 무한 순환을 막기 위해
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectStack> stacks = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private long likes = 0;

    public ProjectDTO.ProjectDetailsResponseDTO toDTO(List<MemberDTO.MemberResponseDTO> members, List<StackDTO.StackProjectResponseDTO> stacks) {
        return ProjectDTO.ProjectDetailsResponseDTO.builder()
                .projectName(projectName).projectIntroduction(projectIntroduction)
                .deployUrl(deployUrl).thumbnailUrl(thumbnail)
                .githubUrl(githubUrl).startDate(startDate)
                .endDate(endDate).mainContents(mainContents)
                .subject(subject).userData(members).stacks(stacks).likes(likes)
                .build();
    }

    public ProjectDTO.ProjectSummaryResponseDTO toSummaryDTO(Long projectId, String projectName, String thumbnail) {
        return ProjectDTO.ProjectSummaryResponseDTO.builder().projectId(projectId).projectName(projectName).thumbnail(thumbnail).build();
    }
}
