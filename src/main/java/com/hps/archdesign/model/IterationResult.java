package com.hps.archdesign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IterationResult {
    private int iterationNumber;
    private String iterationGoal;
    private List<ADDStep> steps;
    private List<ConversationEntry> conversation;
    private String finalDesignDecision;
    private String mermaidDiagram;
    private boolean reviewPassed;

    public static IterationResult create(int number, String goal) {
        return IterationResult.builder()
                .iterationNumber(number)
                .iterationGoal(goal)
                .steps(new ArrayList<>())
                .conversation(new ArrayList<>())
                .build();
    }

    public void addStep(ADDStep step) {
        this.steps.add(step);
    }

    public void addConversation(ConversationEntry entry) {
        this.conversation.add(entry);
    }
}
