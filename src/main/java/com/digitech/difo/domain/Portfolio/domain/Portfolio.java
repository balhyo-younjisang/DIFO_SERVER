package com.digitech.difo.domain.Portfolio.domain;


import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Portfolio.dto.PortfolioDTO;
import com.digitech.difo.domain.TechStack.domain.Stack;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {
    @Id
    @Column(name = "PORTFOLIO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    @Column(nullable = false)
    private String portfolioTitle;

    @Column(nullable = false)
    private String portfolioContent;

    @JsonIgnore // 무한 순환 막기
    @OneToOne(mappedBy = "portfolio")
    private Member member;

    @Column(nullable = false)
    private String githubUrl;

    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = false)
    @Builder.Default
    private long likes = 0;

    public PortfolioDTO.ViewPortfolioResponseDTO toResponseDTO(Long memberId) {
        return PortfolioDTO.ViewPortfolioResponseDTO.builder().portfolioId(portfolioId).portfolioTitle(portfolioTitle).portfolioContent(portfolioContent).githubUrl(githubUrl).likes(likes).createdDate(createdDate).memberId(memberId).build();
    }

}
