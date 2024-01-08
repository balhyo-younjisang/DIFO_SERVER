package com.digitech.difo.domain.Member.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public class MemberDTO {

    @Data
    @Builder
    public static class MemberResponseDTO {
        private Long memberId;
        private String name;
        private String email;
        private String githubUrl;
        private List<Long> projectsId;
    }
}
