package com.digitech.difo.domain.Member.domain;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.MemberProject.domain.MemberProject;
import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String picture;

    @Setter
    @Column
    private String githubUrl;

    @JsonIgnore // 무한 순환을 막기 위해
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "member", orphanRemoval = true)
    private List<MemberProject> projects = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "PORTFOLIO_ID")
    private Portfolio portfolio;

    public MemberDTO.MemberResponseDTO toDTO() {
        return MemberDTO.MemberResponseDTO.builder().memberId(this.getMemberId()).email(this.getEmail()).name(this.getName()).githubUrl(this.getGithubUrl()).portfolioId(this.portfolio != null ? this.portfolio.getPortfolioId() : -1).build();
    }
}
