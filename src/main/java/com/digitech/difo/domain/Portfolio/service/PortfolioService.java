package com.digitech.difo.domain.Portfolio.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import com.digitech.difo.domain.Portfolio.dto.PortfolioDTO;
import com.digitech.difo.domain.Portfolio.repository.PortfolioRepository;
import com.digitech.difo.domain.TechStack.domain.Stack;
import com.digitech.difo.domain.TechStack.repository.StackReposiroty;
import com.digitech.difo.global.common.SuccessResponse;
import com.digitech.difo.global.common.utils.ConvertUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final EntityManager entityManager;
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final StackReposiroty stackReposiroty;

    public SuccessResponse<Portfolio> registerProject(PortfolioDTO.PortfolioBaseDTO portfolioBaseDTO) {
        Portfolio portfolio = this.portfolioRepository.save(portfolioBaseDTO.toEntity());
        Optional<Member> portfolioMember = this.memberRepository.findById(portfolioBaseDTO.getMemberId());

        if(portfolioMember.isEmpty()) throw new NotFoundException("User not found error");

        portfolioMember.get().setPortfolio(portfolio);
        portfolio.setMember(portfolioMember.get());

        this.memberRepository.save(portfolioMember.get());
        this.portfolioRepository.save(portfolio);

        return new SuccessResponse<>(true, portfolio);
    }
}
