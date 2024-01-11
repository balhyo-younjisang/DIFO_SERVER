package com.digitech.difo.domain.Member.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

public class MemberDTO {
    @Getter
    public static abstract class BaseMemberDTO {
        private Long memberId;

        public BaseMemberDTO(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Getter
    @Setter
    public static class MemberUpdateRequestDTO extends BaseMemberDTO{
        private String githubUrl;


        @Builder
        public MemberUpdateRequestDTO(Long memberId, String githubUrl) {
            super(memberId);
            this.githubUrl = githubUrl;
        }
    }

    @Getter
    @Setter
    public static class MemberResponseDTO extends BaseMemberDTO {
        private String name;
        private String email;
        private String githubUrl;
        private List<Long> projectsId;
        private Long portfolioId;

        @Builder
        public MemberResponseDTO(Long memberId, String name, String email, String githubUrl, List<Long> projectsId, Long portfolioId) {
            super(memberId);
            this.name = name;
            this.email = email;
            this.githubUrl = githubUrl;
            this.projectsId = projectsId;
            this.portfolioId = portfolioId;
        }
    }
}
