package com.digitech.difo.domain.TechStack.domain;

import com.digitech.difo.domain.Project.domain.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stackId;

    private String stackName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
