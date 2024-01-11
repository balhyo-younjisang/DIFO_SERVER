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

    public SuccessResponse<Portfolio> registerPortfolio(PortfolioDTO.CreatePortfolioRequestDTO createPortfolioRequestDTO) {
        Portfolio portfolio = this.portfolioRepository.save(createPortfolioRequestDTO.toEntity());
        Optional<Member> portfolioMember = this.memberRepository.findById(createPortfolioRequestDTO.getMemberId());

        if(portfolioMember.isEmpty()) throw new NotFoundException("User not found error");

        portfolioMember.get().setPortfolio(portfolio);
        portfolio.setMember(portfolioMember.get());

        this.memberRepository.save(portfolioMember.get());
        this.portfolioRepository.save(portfolio);

        return new SuccessResponse<>(true, portfolio);
    }

    public SuccessResponse<PortfolioDTO.ViewPortfolioResponseDTO> likePortfolio(long portfolioId) throws Exception {
        try {
            Optional<Portfolio> portfolio = this.portfolioRepository.findById(portfolioId);

            if(portfolio.isEmpty()) throw new NotFoundException("Portfolio is not found");

            long currentLike = portfolio.get().getLikes();
            portfolio.get().setLikes(currentLike + 1);
            this.portfolioRepository.save(portfolio.get());

            Long memberId = portfolio.get().getMember().getMemberId();
            Long likes = portfolio.get().getLikes();

            return new SuccessResponse<>(true, portfolio.get().toResponseDTO(memberId, likes));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SuccessResponse<PortfolioDTO.ViewPortfolioResponseDTO> viewPortfolioDetails(Long portfolioId) throws Exception {
        try {
            Optional<Portfolio> portfolio = this.portfolioRepository.findById(portfolioId);

            if(portfolio.isEmpty()) throw new NotFoundException("Portfolio is Not Found");

            Long memberId = portfolio.get().getMember().getMemberId();
            Long likes = portfolio.get().getLikes();

            return new SuccessResponse<>(true, portfolio.get().toResponseDTO(memberId, likes));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SuccessResponse<List<PortfolioDTO.ViewPortfolioResponseDTO>> recommendPortfolios() throws Exception {
        try {
            List<Portfolio> portfolios = this.portfolioRepository.findAllByOrderByLikes();
            List<PortfolioDTO.ViewPortfolioResponseDTO> portfolioResponseDTOs = new ArrayList<>();
            portfolios.forEach(portfolio -> {
                Long memberId = portfolio.getMember().getMemberId();
                Long likes = portfolio.getLikes();

                portfolioResponseDTOs.add(portfolio.toResponseDTO(memberId, likes));
            });

            return new SuccessResponse<>(true, portfolioResponseDTOs);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SuccessResponse<List<PortfolioDTO.ViewPortfolioResponseDTO>> allPortfolio() {
        List<Portfolio> portfolios = this.portfolioRepository.findAll();
        List<PortfolioDTO.ViewPortfolioResponseDTO> portfolioResponseDTOs = new ArrayList<>();
        portfolios.forEach(portfolio -> {
            Long memberId = portfolio.getMember().getMemberId();
            Long likes = portfolio.getLikes();

            portfolioResponseDTOs.add(portfolio.toResponseDTO(memberId, likes));
        });
        return new SuccessResponse<>(true, portfolioResponseDTOs);
    }
}
