package com.digitech.difo.domain.Project.dto;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProjectDTO {
    @Data
    @AllArgsConstructor
    public static class RegisterProjectRequestDTO {
        private MultipartFile thumbnail; // 포트폴리오 썸네일
        private String subject; // 웹 개발, 앱 개발 등
        private String projectName; // 프로젝트명
        private String projectIntroduction; // 프로젝트 소개
        private Date startDate; // 프로젝트 시작일
        private Date endDate; // 프로젝트 종료일
        private String githubUrl; // 깃헙 레포
        private String deployUrl; // 배포 url
        private String mainContents; // 포트폴리오 내용
        private String userEmails; // 참여한 유저들의 이메일

        public Project toEntity(String imageUrl) {
            return Project.builder().projectName(projectName).thumbnail(imageUrl).subject(subject).projectIntroduction(projectIntroduction).startDate(startDate).endDate(endDate).githubUrl(githubUrl).deployUrl(deployUrl).mainContents(mainContents).build();
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ProjectDetailsResponseDTO  {
        private String thumbnailUrl; // 포트폴리오 썸네일
        private String subject; // Web, App, Game
        private String projectName; // 프로젝트명
        private String projectIntroduction; // 프로젝트 소개
        private Date startDate; // 프로젝트 시작일
        private Date endDate; // 프로젝트 종료일
        private String githubUrl; // 깃헙 레포
        private String deployUrl; // 배포 url
        private String mainContents; // 포트폴리오 내용
        private List<MemberDTO.MemberResponseDTO> userData; // 참여한 유저들의 이메일
    }
}
