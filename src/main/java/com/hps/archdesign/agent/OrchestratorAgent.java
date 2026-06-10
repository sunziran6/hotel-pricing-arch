package com.hps.archdesign.agent;

import com.hps.archdesign.model.ConversationEntry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrchestratorAgent {

    private final ChatClient chatClient;
    private final String priorKnowledge;
    private final String caseStudy;

    public OrchestratorAgent(ChatClient chatClient,
                             PriorKnowledgeLoader priorKnowledgeLoader) {
        this.chatClient = chatClient;
        this.priorKnowledge = priorKnowledgeLoader.getAddMethod();
        this.caseStudy = priorKnowledgeLoader.getCaseStudy();
    }

    public AgentResponse orchestrate(String task, List<ConversationEntry> history) {
        String systemPrompt = buildSystemPrompt();
        String context = buildContextFromHistory(history);
        String fullPrompt = systemPrompt + "\n\n" + context + "\n\n## Current Task\n" + task;

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(fullPrompt)
                .call()
                .content();

        return new AgentResponse("Orchestrator", "Coordinator", response);
    }

    private String buildSystemPrompt() {
        return """
                # Role: Architecture Design Orchestrator (Coordinator Agent)

                You are the coordinator of a multi-agent architecture design team using the ADD 3.0 method.
                Your responsibilities:

                1. **Task Decomposition**: Break down the iteration goal into specific design tasks.
                2. **Workflow Management**: Ensure the ADD 3.0 process is followed in order (Steps 1-7).
                3. **Conflict Resolution**: When the Designer and Reviewer disagree, you make the final decision and provide rationale.
                4. **Progress Tracking**: Keep track of which architectural drivers have been addressed.
                5. **Quality Gate**: Ensure each iteration's output meets the quality bar before proceeding.

                ## Decision Rules (derived from system instructions):
                - Prioritize High-importance drivers over Medium-importance drivers.
                - For greenfield development, always start with the system context diagram in Iteration 1.
                - Each design decision must reference at least one architectural driver.
                - When alternatives exist, evaluate them against: constraints satisfaction, quality attribute impact, team knowledge alignment (Java, Angular, Kafka).
                - Cloud-native patterns should be favored (CON-6).
                - REST APIs as primary integration protocol, with extensibility for other protocols later (CON-5).

                ## Output Format:
                For each orchestration action, clearly state:
                1. Which ADD step is being executed
                2. What the Designer should focus on
                3. What the Reviewer should verify
                """;
    }

    private String buildContextFromHistory(List<ConversationEntry> history) {
        if (history.isEmpty()) return "";
        StringBuilder sb = new StringBuilder("## Conversation History\n");
        for (ConversationEntry entry : history) {
            sb.append(entry.toFormattedString()).append("\n");
        }
        return sb.toString();
    }
}
