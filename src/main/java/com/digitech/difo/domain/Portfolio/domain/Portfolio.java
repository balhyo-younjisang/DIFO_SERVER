package com.digitech.difo.domain.Portfolio.domain;


import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.TechStack.domain.Stack;
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
    @Setter
    @OneToOne(mappedBy = "portfolio")
    private Member member;
}
