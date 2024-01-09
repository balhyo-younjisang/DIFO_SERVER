package com.digitech.difo.domain.TechStack.domain;

import com.digitech.difo.domain.Project.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stackId;

    @Column
    private String stackName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public TechStack(String stack, Project project) {
        this.stackName = stack;
        this.project = project;
    }
}
