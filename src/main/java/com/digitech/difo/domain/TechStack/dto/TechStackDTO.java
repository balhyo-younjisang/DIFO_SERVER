package com.digitech.difo.domain.TechStack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class TechStackDTO {
    @Data
    @AllArgsConstructor
    public class TechStackResponseDTO {
        private Long stackId;
        private String stackName;
    }
}
