package com.digitech.difo.domain.TechStack.domain;

import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.TechStack.dto.TechStackDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stackId;

    @Column(nullable = false)
    private String stackName;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "stack", orphanRemoval = true)
    private List<ProjectStack> project = new ArrayList<>();

    public TechStackDTO.TechStackResponseDTO toDTO(List<ProjectDTO.ProjectSummaryResponseDTO> projects) {
        return TechStackDTO.TechStackResponseDTO.builder().stackId(stackId).stackName(stackName).projectsId(projects).build();
    }
}
