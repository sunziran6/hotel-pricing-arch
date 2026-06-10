package com.hps.archdesign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ADDStep {
    private int stepNumber;
    private String stepName;
    private String content;

    @Override
    public String toString() {
        return String.format("=== ADD Step %d: %s ===\n%s\n", stepNumber, stepName, content);
    }
}
