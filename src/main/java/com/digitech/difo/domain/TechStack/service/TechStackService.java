package com.digitech.difo.domain.TechStack.service;

import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.Project.repository.ProjectRepository;
import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.TechStack.domain.TechStack;
import com.digitech.difo.domain.TechStack.dto.TechStackDTO;
import com.digitech.difo.domain.TechStack.repository.TechStackReposiroty;
import com.digitech.difo.global.common.SuccessResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final EntityManager entityManager;
    private final TechStackReposiroty techStackReposiroty;
    private final ProjectRepository projectRepository;

    public SuccessResponse<TechStack> addStack(String stackName) {
        Optional<TechStack> techStack = this.techStackReposiroty.findByStackName(stackName);
        if(techStack.isEmpty()) throw new AlreadyExistsException("Already stack is exists");
        TechStack createdStack = this.techStackReposiroty.saveAndFlush(TechStack.builder().stackName(stackName).build());

        return new SuccessResponse<>(true, createdStack);
    }

    public SuccessResponse<TechStackDTO.TechStackResponseDTO> getStack(String stackName) {
        Optional<TechStack> existsTechStack = this.techStackReposiroty.findByStackName(stackName);

        if(existsTechStack.isEmpty()) throw new NotFoundException("Stack is not founded");

        List<ProjectDTO.ProjectSummaryResponseDTO> projects = new ArrayList<>();
        for(ProjectStack techStack : existsTechStack.get().getProject()) {
            Optional<Project> foundedProject = this.projectRepository.findById(techStack.getProject().getProject_id());

            if(foundedProject.isEmpty()) continue;
            else {
                Long projectId = foundedProject.get().getProject_id();
                String projectName = foundedProject.get().getProjectName();

                projects.add(foundedProject.get().toSummaryDTO(projectId, projectName));
            }
        }

        return new SuccessResponse<>(true, existsTechStack.get().toDTO(projects));
    }
}
