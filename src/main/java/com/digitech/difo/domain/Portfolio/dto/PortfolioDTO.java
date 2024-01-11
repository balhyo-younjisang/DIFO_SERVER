package com.digitech.difo.domain.Portfolio.dto;

import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class PortfolioDTO {

    @Getter
    public static abstract class PortfolioBaseDTO {
        private String githubUrl;
        private Long memberId;
        private String portfolioTitle;
        private String portfolioContent;
        private Date createdDate;

        public PortfolioBaseDTO(String githubUrl, Long memberId, String portfolioTitle, String portfolioContent, Date createdDate) {
            this.githubUrl = githubUrl;
            this.memberId = memberId;
            this.portfolioContent = portfolioContent;
            this.portfolioTitle = portfolioTitle;
            this.createdDate = createdDate;
        }
    }

    @Getter
    @Setter
    public static class CreatePortfolioRequestDTO extends PortfolioBaseDTO {
        public CreatePortfolioRequestDTO(String githubUrl, Long memberId, String portfolioTitle, String portfolioContent, Date createdDate) {
            super(githubUrl, memberId, portfolioTitle, portfolioContent, createdDate);
        }

        public Portfolio toEntity() {
            return Portfolio.builder().portfolioTitle(this.getPortfolioTitle()).portfolioContent(this.getPortfolioContent()).createdDate(this.getCreatedDate()).githubUrl(this.getGithubUrl()).build();
        }
    }

    @Getter
    public static class ViewPortfolioResponseDTO extends PortfolioBaseDTO{
        private Long portfolioId;
        private long likes;

        @Builder
        public ViewPortfolioResponseDTO(Long portfolioId, long likes, String githubUrl, Long memberId, String portfolioTitle, String portfolioContent, Date createdDate) {
            super( githubUrl,  memberId, portfolioTitle, portfolioContent,  createdDate);
            this.portfolioId = portfolioId;
            this.likes = likes;
        }
    }
}
