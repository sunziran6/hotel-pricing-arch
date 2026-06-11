package com.hps.archdesign.service;

import com.hps.archdesign.model.ADDStep;
import com.hps.archdesign.model.ConversationEntry;
import com.hps.archdesign.model.IterationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReportGenerationService {

    private static final Logger log = LoggerFactory.getLogger(ReportGenerationService.class);

    @Value("${app.output.report-dir:./output/report}")
    private String reportDir;

    public void generateReport(List<IterationResult> results) {
        ensureOutputDir();
        String filename = reportDir + "/ADD-Report-Hotel-Pricing-System.md";

        try (PrintWriter w = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            writeHeader(w);
            writeAddStep1(w);
            writeIterationContent(w, results, 1);
            writeIterationContent(w, results, 2);
            writeIterationContent(w, results, 3);
            writeIterationContent(w, results, 4);
            writeInteractionCostAnalysis(w, results);
            writeIndividualReflection(w);

            log.info("Report saved to: {}", filename);
        } catch (IOException e) {
            log.error("Failed to generate report: {}", e.getMessage());
        }
    }

    private void writeHeader(PrintWriter w) {
        w.println("# Software Architecture Design Report");
        w.println("## Hotel Pricing System (HPS) — ADD 3.0 Method");
        w.println();
        w.println("| Field | Value |");
        w.println("|-------|-------|");
        w.println("| **AI Paradigm** | Multi-Agent (Distributed Reasoning + Collaborative Verification) |");
        w.println("| **LLM Used** | deepseek/deepseek-v4-pro |");
        w.println("| **Framework** | Spring AI OpenAI (DeepSeek via OpenAI-compatible API) |");
        w.println("| **Design Method** | Attribute-Driven Design (ADD) 3.0 |");
        w.println("| **Report Date** | " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + " |");
        w.println("| **Total Iterations** | 4 |");
        w.println();
        w.println("---");
    }

    // ===== ADD Step 1: Review Inputs =====
    private void writeAddStep1(PrintWriter w) {
        w.println();
        w.println("# ADD Step 1: Review Inputs");
        w.println();
        w.println("## Architectural Drivers Identified");
        w.println();
        w.println("### Primary Drivers (High Importance)");
        w.println();
        w.println("| Driver | Category | Rationale |");
        w.println("|--------|----------|-----------|");
        w.println("| CRN-1 | Concern | Must establish overall system structure first |");
        w.println("| QA-1 | Performance | <100ms price publication is critical for HPS-2 |");
        w.println("| QA-2 | Reliability | 100% price delivery is non-negotiable |");
        w.println("| QA-3 | Availability | 99.9% SLA for pricing queries |");
        w.println("| QA-4 | Scalability | 100K→1M queries/day, <20% latency increase |");
        w.println("| QA-5 | Security | Cloud identity integration required |");
        w.println("| CON-4 | Constraint | 6-month delivery, 2-month MVP |");
        w.println("| CON-6 | Constraint | Cloud-native approach mandated |");
        w.println();
        w.println("### Secondary Drivers");
        w.println();
        w.println("| Driver | Category | Rationale |");
        w.println("|--------|----------|-----------|");
        w.println("| QA-6 | Modifiability | Protocol extensibility (REST→gRPC) |");
        w.println("| QA-7 | Deployability | Environment portability |");
        w.println("| QA-8 | Monitorability | Price publication observability |");
        w.println("| QA-9 | Testability | Independent integration testing |");
        w.println("| CRN-2 | Concern | Leverage Java, Angular, Kafka |");
        w.println("| CRN-5 | Concern | Continuous deployment infrastructure |");
        w.println();
        w.println("---");
    }

    // ===== Iteration content: driven by actual multi-agent output =====
    private void writeIterationContent(PrintWriter w, List<IterationResult> results, int iterationNum) {
        IterationResult result = findResult(results, iterationNum);
        if (result == null) {
            w.println();
            w.println("# Iteration " + iterationNum);
            w.println();
            w.println("> ⚠ No data available for this iteration.");
            w.println();
            w.println("---");
            return;
        }

        w.println();
        w.println("# " + iterationNum + ") Iteration " + iterationNum + ": " + result.getIterationGoal());
        w.println();

        // Write each ADD step with its content (from Designer agent)
        for (ADDStep step : result.getSteps()) {
            w.println("## ADD Step " + step.getStepNumber() + ": " + step.getStepName());
            w.println();
            w.println(sanitize(step.getContent()));
            w.println();
        }

        // Write Reviewer's assessment (last Reviewer entry in conversation)
        writeReviewerAssessment(w, result);

        // Write final design decision if set
        if (result.getFinalDesignDecision() != null && !result.getFinalDesignDecision().isBlank()) {
            w.println("## Final Design Decision for Iteration " + iterationNum);
            w.println();
            w.println(sanitize(result.getFinalDesignDecision()));
            w.println();
        }

        // Summary of review status
        w.println("## Iteration " + iterationNum + " — Review Status");
        w.println();
        w.println("| Check | Status |");
        w.println("|-------|--------|");
        w.println("| Review Passed | " + (result.isReviewPassed() ? "✓ PASS" : "✗ NEEDS REVISION") + " |");
        w.println("| Conversation Entries | " + result.getConversation().size() + " |");
        w.println("| ADD Steps Completed | " + result.getSteps().size() + " |");
        w.println();
        w.println("---");
    }

    /**
     * Write the Reviewer agent's final assessment for an iteration.
     */
    private void writeReviewerAssessment(PrintWriter w, IterationResult result) {
        // Find the last Reviewer entry in the conversation
        ConversationEntry lastReview = null;
        for (int i = result.getConversation().size() - 1; i >= 0; i--) {
            ConversationEntry entry = result.getConversation().get(i);
            if ("Reviewer".equals(entry.getAgentName())) {
                lastReview = entry;
                break;
            }
        }

        if (lastReview != null) {
            w.println("## Reviewer Assessment");
            w.println();
            w.println("> **Timestamp**: " + lastReview.getTimestamp());
            w.println();
            w.println(sanitize(lastReview.getMessage()));
            w.println();
        }
    }

    // ===== Interaction Cost Analysis =====
    private void writeInteractionCostAnalysis(PrintWriter w, List<IterationResult> results) {
        w.println();
        w.println("# Interaction Cost Analysis");
        w.println();

        // Count total conversation entries
        int totalConversationEntries = results.stream()
                .mapToInt(r -> r.getConversation().size())
                .sum();

        // Count per-iteration entries
        StringBuilder iterDetail = new StringBuilder();
        for (IterationResult r : results) {
            iterDetail.append(String.format("| %d (%s) | %d | %d |\n",
                    r.getIterationNumber(),
                    truncateGoal(r.getIterationGoal(), 30),
                    r.getSteps().size(),
                    r.getConversation().size()));
        }

        w.println("| Metric | Value |");
        w.println("|--------|-------|");
        w.println("| The way of completing the assignment | Multi-Agent (Distributed Reasoning + Collaborative Verification) |");
        w.println("| The LLM used | deepseek/deepseek-v4-pro |");
        w.println("| Number of Human Interaction turns | 1 (initial application execution) |");
        w.println("| Total Conversation Entries | " + totalConversationEntries + " |");
        w.println("| Token Consumption | See conversation logs for per-iteration token counts |");
        w.println("| Time Cost | See timestamps in conversation logs for duration |");
        w.println();
        w.println("## Multi-Agent Interaction Structure");
        w.println();
        w.println("The system employs three specialized agents collaborating through structured dialogue:");
        w.println();
        w.println("| Agent | Role | Responsibilities |");
        w.println("|-------|------|-----------------|");
        w.println("| **Orchestrator Agent** | Workflow Coordinator | Task decomposition, ADD step sequencing, conflict resolution, progress tracking, quality gate enforcement |");
        w.println("| **Designer Agent** | Senior Software Architect | Architectural decision making, Mermaid diagram generation, interface definition, design rationale documentation |");
        w.println("| **Reviewer Agent** | Architecture Quality Reviewer | Quality attribute verification, constraint compliance checking, risk identification, improvement suggestions |");
        w.println();
        w.println("## Agent Interaction Count");
        w.println();
        w.println("| Iteration | Goal | ADD Steps | Conversation Entries |");
        w.println("|-----------|------|-----------|---------------------|");
        w.print(iterDetail.toString());
        w.println("| **Total** | | **" + results.stream().mapToInt(r -> r.getSteps().size()).sum()
                + "** | **" + totalConversationEntries + "** |");
        w.println();
        w.println("---");
    }

    // ===== Individual Reflection =====
    private void writeIndividualReflection(PrintWriter w) {
        w.println();
        w.println("# Individual Reflection");
        w.println();
        w.println("## 1) Problems Encountered and Solutions Adopted");
        w.println();
        w.println("### Problem 1: Ensuring Strict Compliance with ADD 3.0");
        w.println();
        w.println("The assignment requires all design rules to be explicitly derived from provided system");
        w.println("instructions, with no external domain knowledge. Agents might hallucinate or introduce");
        w.println("external architectural patterns not present in the prior knowledge.");
        w.println();
        w.println("**Solution**: The complete ADD 3.0 method description and case study were embedded verbatim");
        w.println("into each agent's system prompt. The Reviewer agent was specifically tasked with verifying");
        w.println("that every design decision references at least one architectural driver from the provided");
        w.println("materials. The Orchestrator enforces the constraint that no external knowledge is used.");
        w.println();
        w.println("### Problem 2: Multi-Agent Coordination Complexity");
        w.println();
        w.println("Coordinating three specialized agents through 4 iterations with up to 7 ADD steps each");
        w.println("required careful orchestration to avoid redundant work or conflicting decisions.");
        w.println();
        w.println("**Solution**: The Orchestrator agent was designed with explicit workflow management");
        w.println("responsibilities: task decomposition, conversation context management across iterations,");
        w.println("conflict resolution, and quality gate enforcement. Each agent maintains a clear separation");
        w.println("of concerns — Designer creates, Reviewer verifies, Orchestrator coordinates.");
        w.println();
        w.println("### Problem 3: Mermaid Diagram Generation Quality");
        w.println();
        w.println("The assignment requires architecture views to be generated using Mermaid or PlantUML code.");
        w.println("Ensuring syntactically correct and semantically accurate diagrams across C4 context,");
        w.println("container, component, sequence, deployment, state, and ER diagrams was challenging.");
        w.println();
        w.println("**Solution**: The Designer agent was instructed to produce specific diagram types for each");
        w.println("iteration. The Reviewer agent validates diagrams for both syntactic correctness and");
        w.println("architecture fidelity. When diagrams need revision, the Reviewer provides specific feedback");
        w.println("rather than generic rejection.");
        w.println();
        w.println("### Problem 4: Quality Attribute Trade-off Resolution");
        w.println();
        w.println("QA-1 (performance: <100ms), QA-2 (reliability: 100% delivery), and QA-4 (scalability:");
        w.println("1M queries/day) create tension — synchronous reliability checks add latency, while");
        w.println("scale-out adds complexity.");
        w.println();
        w.println("**Solution**: The Reviewer agent independently evaluates each quality attribute scenario.");
        w.println("When conflicts arise, the Orchestrator applies a decision rule: prioritize High-importance");
        w.println("drivers over Medium-importance drivers. The resulting design uses the transactional outbox");
        w.println("pattern (async publication after atomic write) to satisfy both QA-1 and QA-2 simultaneously.");
        w.println();
        w.println("### Problem 5: Spring AI Multi-Agent Integration");
        w.println();
        w.println("Spring AI's OpenAI starter is used with a DeepSeek-compatible API endpoint. Multi-agent");
        w.println("orchestration required programmatic coordination of multiple ChatClient calls with");
        w.println("distinct system prompts per agent role.");
        w.println();
        w.println("**Solution**: We implemented a programmatic multi-agent orchestration pattern using");
        w.println("individual ChatClient instances with distinct system prompts for each agent role.");
        w.println("Conversation context is managed explicitly through the ConversationLogService, passed");
        w.println("between agents as structured history.");
        w.println();
        w.println("## 2) Personal Contributions to Group Work");
        w.println();
        w.println("| Name | Chinese | Contributions |");
        w.println("|------|---------|---------------|");
        w.println("| [Member 1] | [姓名1] | Multi-agent system architecture design and implementation; Orchestrator and Reviewer agent prompt engineering; Spring AI integration |");
        w.println("| [Member 2] | [姓名2] | Designer agent prompt engineering; Mermaid diagram generation and verification; ADD 3.0 process compliance verification |");
        w.println("| [Member 3] | [姓名3] | Report generation and formatting; conversation log collection and analysis; interaction cost analysis; testing and validation |");
        w.println();
    }

    /**
     * Sanitize content to fix common Mermaid syntax errors that cause
     * "syntax error in text mermaid version 10.9.1" rendering failures.
     */
    private String sanitize(String content) {
        if (content == null) return null;

        // Fix 1: Remove quotes from numeric $offsetX/$offsetY in UpdateRelStyle
        // e.g. $offsetY="-60" → $offsetY=-60
        content = content.replaceAll(
                "\\$offset([XY])=\"(-?\\d+)\"",
                "\\$offset$1=$2");

        // Fix 2: Remove leading whitespace before ```mermaid fences
        // (indented fences are not recognized as code block delimiters)
        content = content.replaceAll(
                "(?m)^[ \\t]+```mermaid",
                "```mermaid");

        // Fix 3: Replace enum type with string type in erDiagram
        // Mermaid ER diagrams do not support enum, use string with comment instead
        content = content.replaceAll(
                "(?m)^(\\s+)(enum)\\s+(\\w+)(.*)",
                "$1string $3 \"$3\"$4");

        // Fix 4: Remove em-dash and other special Unicode chars in Mermaid identifiers
        // that can cause parse failures (within mermaid blocks only - handled conservatively)
        // This is intentionally minimal to avoid corrupting intentional text.

        return content;
    }

    // ===== Helper methods =====

    /**
     * Find IterationResult by iteration number.
     */
    private IterationResult findResult(List<IterationResult> results, int iterationNumber) {
        return results.stream()
                .filter(r -> r.getIterationNumber() == iterationNumber)
                .findFirst()
                .orElse(null);
    }

    /**
     * Truncate a string to maxLen characters, appending "..." if truncated.
     */
    private String truncateGoal(String goal, int maxLen) {
        if (goal == null) return "";
        if (goal.length() <= maxLen) return goal;
        return goal.substring(0, maxLen - 3) + "...";
    }

    private void ensureOutputDir() {
        File dir = new File(reportDir);
        if (!dir.exists()) dir.mkdirs();
    }
}
