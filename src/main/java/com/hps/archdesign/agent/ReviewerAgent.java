package com.hps.archdesign.agent;

import com.hps.archdesign.model.ConversationEntry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewerAgent {

    private final ChatClient chatClient;

    public ReviewerAgent(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public AgentResponse review(String design, List<ConversationEntry> history,
                                 String priorKnowledge, String caseStudy, String iterationGoal) {
        String systemPrompt = buildSystemPrompt(priorKnowledge, caseStudy);
        String context = buildContextFromHistory(history);
        String fullPrompt = String.format("""
                %s

                %s

                ## Iteration Goal
                %s

                ## Design to Review
                %s

                Please review the above design against the iteration goal, quality attributes, constraints, and architectural concerns.
                """, systemPrompt, context, iterationGoal, design);

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(fullPrompt)
                .call()
                .content();

        return new AgentResponse("Reviewer", "Architecture Reviewer", response);
    }

    private String buildSystemPrompt(String priorKnowledge, String caseStudy) {
        return String.format("""
                # Role: Architecture Quality Reviewer (Reviewer Agent)

                You are an architecture reviewer responsible for verifying that the Hotel Pricing System (HPS)
                architectural designs satisfy all architectural drivers and follow the ADD 3.0 method correctly.

                ## Prior Knowledge - ADD 3.0 Method
                %s

                ## Case Study - Hotel Pricing System
                %s

                ## Your Responsibilities:

                1. **Quality Verification**: Check that the design satisfies all relevant quality attributes.
                2. **Constraint Compliance**: Verify all constraints (CON-1 through CON-6) are addressed.
                3. **Completeness Check**: Ensure all ADD steps for the iteration are properly executed.
                4. **Risk Identification**: Flag potential issues, inconsistencies, or missing elements.
                5. **Improvement Suggestions**: Provide concrete, actionable feedback for the Designer.

                ## Review Criteria (derived from system instructions):

                ### Quality Attribute Verification:
                - QA-1 (Performance): Does the design achieve <100ms price publication latency?
                - QA-2 (Reliability): Does the design guarantee 100%% price change delivery?
                - QA-3 (Availability): Does the design support 99.9%% uptime SLA?
                - QA-4 (Scalability): Does the design support 100K-1M queries/day with <20%% latency increase?
                - QA-5 (Security): Is cloud identity service properly integrated?
                - QA-6 (Modifiability): Can new protocols be added without core changes?
                - QA-7 (Deployability): Is environment portability ensured?
                - QA-8 (Monitorability): Are performance/reliability metrics collectable?
                - QA-9 (Testability): Can elements be tested independently of external systems?

                ### Constraint Verification:
                - CON-1: Web browser access across platforms?
                - CON-2: Cloud identity service + cloud hosting?
                - CON-3: Git-based version control?
                - CON-4: 6-month delivery / 2-month MVP feasible?
                - CON-5: REST with future protocol extensibility?
                - CON-6: Cloud-native approach?

                ### Diagram Verification:
                - Are Mermaid diagrams syntactically correct?
                - Do views adequately represent the architecture?
                - Are component responsibilities and interfaces clearly shown?

                ## Output Format:
                1. **Overall Assessment**: PASS / NEEDS_REVISION / FAIL
                2. **Strengths**: What the design does well
                3. **Issues Found**: Specific problems with references to drivers
                4. **Suggestions**: Concrete improvements
                5. **Decision**: Whether the design is acceptable or needs revision
                """, priorKnowledge, caseStudy);
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
