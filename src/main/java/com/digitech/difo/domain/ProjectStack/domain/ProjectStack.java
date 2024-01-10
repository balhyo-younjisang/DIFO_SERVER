package com.digitech.difo.domain.ProjectStack.domain;

import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.TechStack.domain.Stack;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "StackID")
    private Stack stack;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectID")
    private Project project;
}
