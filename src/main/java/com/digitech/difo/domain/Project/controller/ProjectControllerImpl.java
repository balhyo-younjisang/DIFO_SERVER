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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@AllArgsConstructor
public class ProjectControllerImpl implements ProjectController{
    private final ProjectService projectService;

    @Override
    public ResponseEntity<SuccessResponse<Project>> postRegisterProject(@ModelAttribute ProjectDTO.RegisterProjectRequestDTO registerProjectRequestDTO) throws IOException {
        SuccessResponse<Project> response = projectService.registerProject(registerProjectRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SuccessResponse<Void>> deleteProject(@RequestParam(value = "id") Long id) throws Exception {
        SuccessResponse<Void> response = projectService.deleteProject(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse<ProjectDTO.ProjectDetailsResponseDTO>> getProjectDetails(@RequestParam(value = "id") Long id) throws Exception {
        SuccessResponse<ProjectDTO.ProjectDetailsResponseDTO> response = this.projectService.getProjectDetails(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse<Void>> patchLikeProject(@RequestParam(name = "id") Long id) throws Exception {
        SuccessResponse<Void> response = this.projectService.likeProject(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
