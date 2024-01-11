package com.digitech.difo.domain.TechStack.domain;

import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.TechStack.dto.StackDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stack {
    @Id
    @Column(name = "STACK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stackId;

    @Column(nullable = false)
    private String stackName;

    @JsonIgnore // 무한 순환을 막기 위해
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "stack", orphanRemoval = true)
    private List<ProjectStack> projects = new ArrayList<>();


    public StackDTO.TechStackResponseDTO toDTO(List<ProjectDTO.ProjectSummaryResponseDTO> projects) {
        return StackDTO.TechStackResponseDTO.builder().stackId(stackId).stackName(stackName).projectsId(projects).build();
    }

    public StackDTO.StackProjectResponseDTO toSummaryDTO() {
        return StackDTO.StackProjectResponseDTO.builder().stackName(stackName).stackId(stackId).build();
    }
}
