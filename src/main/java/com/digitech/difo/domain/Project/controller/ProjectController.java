package com.digitech.difo.domain.Project.controller;


import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.Project.service.ProjectService;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/project")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    /**
     * 프로젝트 등록
     * @param registerProjectRequestDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Project>> postRegisterProject(@ModelAttribute ProjectDTO.RegisterProjectRequestDTO registerProjectRequestDTO) throws IOException {
        SuccessResponse<Project> response = projectService.registerProject(registerProjectRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
