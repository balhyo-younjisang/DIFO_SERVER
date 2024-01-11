package com.digitech.difo.domain.Project.controller;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping(value = "/api/v1/project")
public interface ProjectController {

    /**
     * 프로젝트를 Project Table에 저장하고 생성된 Project를 리턴
     * @param registerProjectRequestDTO 프로젝트 데이터
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Project>> postRegisterProject(@ModelAttribute ProjectDTO.RegisterProjectRequestDTO registerProjectRequestDTO) throws IOException;

    /**
     * Parameter로 들어온 Project Id를 Project Table에서 검색 후 삭제
     * @param id 프로젝트 아이디
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse<Void>> deleteProject(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * Parameter로 들어온 Project Id를 Project Table에서 검색한 후 리턴
     * @param id 프로젝트 아이디
     * @return
     */
    @GetMapping("/details")
    public ResponseEntity<SuccessResponse<ProjectDTO.ProjectDetailsResponseDTO>> getProjectDetails(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * 프로젝트 좋아요 기능
     * @param id 프로젝트 아이디
     * @return
     */
    @PatchMapping("/likes")
    public ResponseEntity<SuccessResponse<Void>> patchLikeProject(@RequestParam(value = "id") Long id) throws Exception;

    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<List<ProjectDTO.ProjectSummaryResponseDTO>>> getProjectsRecommend() throws Exception;

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<ProjectDTO.ProjectDetailsResponseDTO>>> getAllProject();
}
