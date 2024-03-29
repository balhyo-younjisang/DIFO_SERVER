package com.digitech.difo.domain.TechStack.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.TechStack.domain.Stack;
import com.digitech.difo.domain.TechStack.dto.StackDTO;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/stack")
public interface StackController {
    /**
     * 기술스택이 DB 상에 존재하지 않을 때 기술스택의 이름을 넘겨 Table에 기술스택 데이터 생성
     * @param addTechStackRequestDTO
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<SuccessResponse<Stack>> postAddStack(@Valid @ModelAttribute StackDTO.AddTechStackRequestDTO addTechStackRequestDTO);

    /**
     * 기술스택의 이름을 Table에서 검색하여 추출한 데이터를 리턴
     * @param stackName
     * @return
     * @throws NotFoundException
     */
    @GetMapping(value = "/stack")
    public ResponseEntity<SuccessResponse<StackDTO.TechStackResponseDTO>> getStackData(@RequestParam(value = "name") String stackName) throws NotFoundException;

    @GetMapping(value = "/all")
    public ResponseEntity<SuccessResponse<List<StackDTO.StackProjectResponseDTO>>> getAllStackData() throws Exception;
}
