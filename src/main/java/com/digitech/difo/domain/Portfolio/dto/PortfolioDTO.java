package com.digitech.difo.domain.Portfolio.dto;

import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class PortfolioDTO {

    @Getter
    public static class PortfolioBaseDTO {
//        private Long portfolioId;
        private String githubUrl;
        private Long memberId;
        private String portfolioTitle;
        private String portfolioContent;
        private Date createdDate;
        private String stacks;

        public PortfolioBaseDTO( String githubUrl, Long memberId, String portfolioTitle, String portfolioContent, Date createdDate, String stacks) {
//            this.portfolioId = portfolioId;
            this.githubUrl = githubUrl;
            this.memberId = memberId;
            this.portfolioContent = portfolioContent;
            this.portfolioTitle = portfolioTitle;
            this.createdDate = createdDate;
            this.stacks = stacks;
        }

        public Portfolio toEntity() {
            return Portfolio.builder().portfolioTitle(portfolioTitle).portfolioContent(portfolioContent).build();
        }
    }
}
