package com.digitech.difo.domain.ProjectStack.domain;

import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.TechStack.domain.TechStack;
import jakarta.persistence.*;
import lombok.*;

import java.util.Stack;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stackProjectId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STACK_ID")
    private TechStack techStack;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =" PROJECT_ID")
    private Project project;
}
