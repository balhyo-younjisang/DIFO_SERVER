package com.digitech.difo.domain.Member.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

public class MemberDTO {

    @Getter
    @Builder
    public static class MemberResponseDTO {
        private Long memberId;
        private String name;
        private String email;
        private String githubUrl;
    }
}
