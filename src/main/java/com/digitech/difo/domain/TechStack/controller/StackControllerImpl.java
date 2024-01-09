package com.digitech.difo.domain.TechStack.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.TechStack.domain.Stack;
import com.digitech.difo.domain.TechStack.dto.StackDTO;
import com.digitech.difo.domain.TechStack.service.StackService;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@AllArgsConstructor
public class StackControllerImpl implements StackController {
    private final StackService techStackService;

    @Override
    public ResponseEntity<SuccessResponse<Stack>> postAddStack(StackDTO.AddTechStackRequestDTO addTechStackRequestDTO) {
        SuccessResponse<Stack> techStack = techStackService.addStack(addTechStackRequestDTO.getStackName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(techStack, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse<StackDTO.TechStackResponseDTO>> getStackData(String stackName) throws NotFoundException {
        SuccessResponse<StackDTO.TechStackResponseDTO> techStack = techStackService.getStack(stackName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(techStack, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse<List<StackDTO.StackProjectResponseDTO>>> getAllStackData() throws Exception {
        SuccessResponse<List<StackDTO.StackProjectResponseDTO>> stacks = techStackService.getAllStack();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(stacks, headers, HttpStatus.OK);
    }
}
