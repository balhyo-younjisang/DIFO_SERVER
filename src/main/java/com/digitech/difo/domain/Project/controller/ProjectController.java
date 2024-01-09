package com.digitech.difo.domain.Project.controller;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

public interface ProjectController {

    /**
     * 프로젝트를 Project Table에 저장하고 생성된 Project를 리턴
     * @param registerProjectRequestDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Project>> postRegisterProject(@ModelAttribute ProjectDTO.RegisterProjectRequestDTO registerProjectRequestDTO) throws IOException;

    /**
     * Parameter로 들어온 Project Id를 Project Table에서 검색 후 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse<Void>> deleteProject(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * Parameter로 들어온 Project Id를 Project Table에서 검색한 후 리턴
     * @param id
     * @return
     */
    @GetMapping("/details")
    public ResponseEntity<SuccessResponse<ProjectDTO.ProjectDetailsResponseDTO>> getProjectDetails(@RequestParam(value = "id") Long id) throws Exception;
}
