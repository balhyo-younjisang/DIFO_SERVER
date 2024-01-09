package com.digitech.difo.domain.TechStack.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.TechStack.domain.TechStack;
import com.digitech.difo.domain.TechStack.dto.TechStackDTO;
import com.digitech.difo.domain.TechStack.service.TechStackService;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@AllArgsConstructor
public class TechStackControllerImpl implements TechStackController{
    private final TechStackService techStackService;

    @Override
    public ResponseEntity<SuccessResponse<TechStack>> postAddStack(TechStackDTO.AddTechStackRequestDTO addTechStackRequestDTO) {
        SuccessResponse<TechStack> techStack = techStackService.addStack(addTechStackRequestDTO.getStackName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(techStack, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse<TechStackDTO.TechStackResponseDTO>> getStackData(String stackName) throws NotFoundException {
        SuccessResponse<TechStackDTO.TechStackResponseDTO> techStack = techStackService.getStack(stackName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(techStack, headers, HttpStatus.OK);
    }
}
