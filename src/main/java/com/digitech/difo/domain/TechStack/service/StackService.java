package com.digitech.difo.domain.TechStack.service;

import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.Project.repository.ProjectRepository;
import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.TechStack.domain.Stack;
import com.digitech.difo.domain.TechStack.dto.StackDTO;
import com.digitech.difo.domain.TechStack.repository.StackReposiroty;
import com.digitech.difo.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StackService {
    private final StackReposiroty techStackReposiroty;
    private final ProjectRepository projectRepository;

    public SuccessResponse<Stack> addStack(String stackName) {
        Optional<Stack> techStack = this.techStackReposiroty.findByStackName(stackName);
        if(!techStack.isEmpty()) throw new AlreadyExistsException("Already stack is exists");
        Stack createdStack = this.techStackReposiroty.saveAndFlush(Stack.builder().stackName(stackName).projects(new ArrayList<>()).build());

        return new SuccessResponse<>(true, createdStack);
    }

    public SuccessResponse<StackDTO.TechStackResponseDTO> getStack(String stackName) {
        Optional<Stack> existsTechStack = this.techStackReposiroty.findByStackName(stackName);

        if(existsTechStack.isEmpty()) throw new NotFoundException("Stack is not founded");

        List<ProjectDTO.ProjectSummaryResponseDTO> projects = new ArrayList<>();
        for(ProjectStack techStack : existsTechStack.get().getProjects()) {
            Optional<Project> foundedProject = this.projectRepository.findById(techStack.getProject().getProjectId());

            if(foundedProject.isEmpty()) continue;
            else {
                Long projectId = foundedProject.get().getProjectId();
                String projectName = foundedProject.get().getProjectName();
                String thumbnail = foundedProject.get().getThumbnail();

                projects.add(foundedProject.get().toSummaryDTO(projectId, projectName, thumbnail));
            }
        }

        return new SuccessResponse<>(true, existsTechStack.get().toDTO(projects));
    }

    public SuccessResponse<List<StackDTO.StackProjectResponseDTO>> getAllStack() {
        List<Stack> allStacks = this.techStackReposiroty.findAll();
        List<StackDTO.StackProjectResponseDTO> responseList = new ArrayList<>();

        for(Stack stack : allStacks) {
            responseList.add(stack.toSummaryDTO());
        }

        return new SuccessResponse<>(true, responseList);
    }
}
