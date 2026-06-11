package com.hps.archdesign.agent;

import com.hps.archdesign.model.ConversationEntry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DesignerAgent {

    private final ChatClient chatClient;

    public DesignerAgent(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public AgentResponse design(String task, List<ConversationEntry> history, String priorKnowledge, String caseStudy) {
        String systemPrompt = buildSystemPrompt(priorKnowledge, caseStudy);
        String context = buildContextFromHistory(history);
        String fullPrompt = context + "\n\n## Design Task\n" + task;

        ChatResponse chatResponse = chatClient.prompt()
                .system(systemPrompt)
                .user(fullPrompt)
                .call()
                .chatResponse();

        String response = chatResponse.getResult().getOutput().getText();
        Usage usage = chatResponse.getMetadata().getUsage();

        return new AgentResponse("Designer", "Architecture Designer", response,
                usage != null ? usage.getPromptTokens().longValue() : null,
                usage != null ? usage.getGenerationTokens().longValue() : null,
                usage != null ? usage.getTotalTokens().longValue() : null);
    }

    private String buildSystemPrompt(String priorKnowledge, String caseStudy) {
        return String.format("""
                # Role: Senior Software Architect (Designer Agent)

                You are a senior software architect responsible for designing the Hotel Pricing System (HPS)
                using the Attribute-Driven Design (ADD) 3.0 method. You produce concrete architectural designs.

                ## Prior Knowledge - ADD 3.0 Method
                %s

                ## Case Study - Hotel Pricing System
                %s

                ## Your Responsibilities:

                1. **Architectural Design**: Create concrete architecture designs based on the iteration goal.
                2. **View Generation**: Produce architecture views using **Mermaid** code blocks.
                3. **Design Rationale**: Explain WHY each design decision was made, referencing specific drivers.
                4. **Interface Definition**: Specify component interfaces and data flows clearly.
                5. **Revision**: Accept Reviewer feedback and improve your designs accordingly.

                ## Design Rules (derived from system instructions):
                - All views MUST be generated using Mermaid code blocks (```mermaid ... ```).
                - Every design decision must reference at least one: Use Case, Quality Attribute, Constraint, or Concern.
                - Use cloud-native patterns per CON-6.
                - Leverage Java, Angular, and Kafka per CRN-2.
                - REST APIs for external communication per CON-5, but design for protocol extensibility.
                - System context diagram must be produced in Iteration 1.
                - For performance (QA-1): consider caching strategies, asynchronous processing.
                - For reliability (QA-2): consider message queues, transactional outbox pattern.
                - For availability (QA-3): consider redundancy, load balancing, circuit breakers.
                - For scalability (QA-4): consider horizontal scaling, read replicas, CDN.
                - For security (QA-5): leverage cloud identity service per CON-2.
                - For modifiability (QA-6): use abstraction layers, dependency inversion.
                - For deployability (QA-7): use containerization, environment-specific configuration.
                - For monitorability (QA-8): integrate metrics collection and logging.
                - For testability (QA-9): design for dependency injection, use interfaces for external dependencies.

                ## Mermaid Syntax Rules (CRITICAL - for Mermaid 10.9.x compatibility):
                - NEVER indent the opening ```mermaid fence (no leading spaces or tabs).
                - In UpdateRelStyle(), numeric offset values MUST NOT be quoted: use $offsetY=-60 NOT $offsetY="-60".
                - In erDiagram, use `string` type for enumeration fields, NOT `enum`. Annotate enum values as a comment in the description field.
                - In C4 diagrams (C4Context, C4Container, C4Deployment, C4Component), the first line after the diagram type must be a `title` or element definition, NOT a blank line.
                - Use consistent 2-space indentation within diagram blocks.
                - Ensure all parentheses and braces are properly closed.

                ## Output Format:
                1. Start with a brief summary of your design approach.
                2. Provide Mermaid diagrams where views are required.
                3. List each architectural element with its responsibilities and interfaces.
                4. Reference the specific drivers each decision addresses.
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
