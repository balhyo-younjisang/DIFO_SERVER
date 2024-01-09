package com.digitech.difo.domain.TechStack.dto;

import com.digitech.difo.domain.Project.dto.ProjectDTO;
import lombok.*;

import java.util.List;

public class StackDTO {
    @Getter
    public static abstract class TechStackBaseDTO {
        private Long stackId;
        private String stackName;

        public TechStackBaseDTO(Long stackId, String stackName) {
            this.stackId = stackId;
            this.stackName = stackName;
        }
    }

    @Getter
    @Setter
    public static class AddTechStackRequestDTO {
        private String stackName;
    }

    @Getter
    @Setter
    public static class StackProjectResponseDTO extends TechStackBaseDTO {
        @Builder
        public StackProjectResponseDTO(Long stackId, String stackName) {
            super(stackId, stackName);
        }
    }

    @Getter
    @Setter
    public static class TechStackResponseDTO extends TechStackBaseDTO {
        private List<ProjectDTO.ProjectSummaryResponseDTO> projectsId;


        @Builder
        public TechStackResponseDTO(Long stackId, String stackName, List<ProjectDTO.ProjectSummaryResponseDTO> projectsId) {
            super(stackId, stackName);
            this.projectsId = projectsId;
        }
    }
}
