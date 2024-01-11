package com.digitech.difo.domain.Portfolio.controller;

import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import com.digitech.difo.domain.Portfolio.dto.PortfolioDTO;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/portfolio")
public interface PortfolioController {
    /**
     * 포트폴리오를 생성한 후 생성된 포트폴리오를 리턴하는 메서드
     * @param portfolioBaseDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<Portfolio>> createPortfolio(@ModelAttribute PortfolioDTO.CreatePortfolioRequestDTO createPortfolioRequestDTO);

    /**
     * 포트폴리오 모두 가져옴
     */
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<PortfolioDTO.ViewPortfolioResponseDTO>>> getAllPortfolio();

    /**
     * 포트폴리오 데이터를 가져와 리턴하는 메서드
     * @param portfolioId
     * @return
     */
    @GetMapping("/view")
    public ResponseEntity<SuccessResponse<PortfolioDTO.ViewPortfolioResponseDTO>> viewPortfolio(@RequestParam(name = "id") Long portfolioId) throws Exception;

    /**
     * 포트폴리오 업데이트
     * @param portfolioBaseDTO
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<SuccessResponse<PortfolioDTO.PortfolioBaseDTO>> updatePortfolio(@ModelAttribute PortfolioDTO.PortfolioBaseDTO portfolioBaseDTO);

    /**
     * 포트폴리오 삭제
     * @param portfolioId
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse<Void>> deletePortfolio(@RequestParam(name = "id") Long portfolioId);

    @PatchMapping("/likes")
    public ResponseEntity<SuccessResponse<PortfolioDTO.ViewPortfolioResponseDTO>> likesPortfolio(@RequestParam(name = "id") long id) throws Exception;

    /**
     * 포트폴리오 추천 순으로 조회
     */
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<List<PortfolioDTO.ViewPortfolioResponseDTO>>> getRecommendPortfolio() throws Exception;
}
