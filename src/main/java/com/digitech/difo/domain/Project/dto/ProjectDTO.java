package com.digitech.difo.domain.Project.dto;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Project.domain.Project;
import com.fasterxml.classmate.AnnotationOverrides;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDTO {
    @Getter
    public static abstract class BaseProjectDTO {
        private String subject; // 웹 개발, 앱 개발 등
        private String projectName; // 프로젝트명
        private String projectIntroduction; // 프로젝트 소개
        private Date startDate; // 프로젝트 시작일
        private Date endDate; // 프로젝트 종료일
        private String githubUrl; // 깃헙 레포
        private String deployUrl; // 배포 url
        private String mainContents; // 포트폴리오 내용

        public BaseProjectDTO(String subject, String projectName, String projectIntroduction, Date startDate, Date endDate, String githubUrl, String deployUrl, String mainContents) {
            this.subject = subject;
            this.projectName = projectName;
            this.projectIntroduction = projectIntroduction;
            this.startDate = startDate;
            this.endDate = endDate;
            this.githubUrl = githubUrl;
            this.deployUrl = deployUrl;
            this.mainContents = mainContents;
        }
    }

    @Getter
    @Setter
    public static class RegisterProjectRequestDTO extends BaseProjectDTO {
        private MultipartFile thumbnail; // 포트폴리오 썸네일
        private String userEmails; // 참여한 유저들의 이메일
        private String techStacks; // 사용한 스택들

        public RegisterProjectRequestDTO(String subject, String projectName, String projectIntroduction, Date startDate, Date endDate, String githubUrl, String deployUrl, String mainContents, MultipartFile thumbnail, String userEmails, String techStacks) {
            super(subject, projectName, projectIntroduction, startDate, endDate, githubUrl, deployUrl, mainContents);
            this.thumbnail = thumbnail;
            this.userEmails = userEmails;
            this.techStacks = techStacks;
        }

        public Project toEntity(String imageUrl) {
            return Project.builder().projectName(this.getProjectName()).thumbnail(imageUrl).subject(this.getSubject()).projectIntroduction(this.getProjectIntroduction()).startDate(this.getStartDate()).endDate(this.getEndDate()).githubUrl(this.getGithubUrl()).deployUrl(this.getDeployUrl()).mainContents(this.getMainContents()).stacks(new ArrayList<>()).build();
        }
    }

    @Getter
    @Setter
    public static class ProjectDetailsResponseDTO extends BaseProjectDTO {
        private String thumbnailUrl; // 포트폴리오 썸네일
        private List<MemberDTO.MemberResponseDTO> userData; // 참여한 유저들의 이메일

        @Builder
        public ProjectDetailsResponseDTO(String subject, String projectName, String projectIntroduction, Date startDate, Date endDate, String githubUrl, String deployUrl, String mainContents, String thumbnailUrl, List<MemberDTO.MemberResponseDTO> userData) {
            super(subject, projectName, projectIntroduction, startDate, endDate, githubUrl, deployUrl, mainContents);
            this.thumbnailUrl = thumbnailUrl;
            this.userData = userData;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class ProjectSummaryResponseDTO {
        private Long projectId;
        private String projectName;
    }
}
