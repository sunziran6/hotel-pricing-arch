package com.hps.archdesign.service;

import com.hps.archdesign.model.ConversationEntry;
import com.hps.archdesign.model.IterationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationLogService {

    private static final Logger log = LoggerFactory.getLogger(ConversationLogService.class);

    @Value("${app.output.conversation-log:./output/conversation-logs}")
    private String outputDir;

    private final List<ConversationEntry> globalLog = new ArrayList<>();

    public void log(String agentName, String role, String message) {
        ConversationEntry entry = ConversationEntry.of(agentName, role, message);
        globalLog.add(entry);
        log.info(entry.toFormattedString());
    }

    public List<ConversationEntry> getGlobalLog() {
        return new ArrayList<>(globalLog);
    }

    public void saveIterationLog(IterationResult result) {
        ensureOutputDir();
        String filename = String.format("%s/iteration-%02d-conversation-log.md",
                outputDir, result.getIterationNumber());

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("# Iteration " + result.getIterationNumber()
                    + " - Conversation Log");
            writer.println();
            writer.println("**Goal**: " + result.getIterationGoal());
            writer.println();
            writer.println("**Generated**: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            writer.println();
            writer.println("---");
            writer.println();

            for (ConversationEntry entry : result.getConversation()) {
                writer.println("### " + entry.getAgentName() + " (" + entry.getRole() + ")");
                writer.println();
                writer.println("**Timestamp**: " + entry.getTimestamp());
                writer.println();
                writer.println(entry.getMessage());
                writer.println();
                writer.println("---");
                writer.println();
            }

            log.info("Conversation log saved to: {}", filename);
        } catch (IOException e) {
            log.error("Failed to save conversation log: {}", e.getMessage());
        }
    }

    public void saveFullConversationLog(List<IterationResult> allResults) {
        ensureOutputDir();
        String filename = outputDir + "/full-conversation-log.md";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("# Hotel Pricing System - ADD 3.0 Multi-Agent Architecture Design");
            writer.println("## Complete Conversation Log");
            writer.println();
            writer.println("**AI Paradigm**: Multi-Agent (Distributed Reasoning + Collaborative Verification)");
            writer.println("**LLM**: deepseek-v4-pro");
            writer.println("**Framework**: Spring AI Alibaba");
            writer.println("**Generated**: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            writer.println();

            for (IterationResult result : allResults) {
                writer.println("---");
                writer.println();
                writer.println("# Iteration " + result.getIterationNumber()
                        + ": " + result.getIterationGoal());
                writer.println();

                for (ConversationEntry entry : result.getConversation()) {
                    writer.println("### [" + entry.getTimestamp() + "] "
                            + entry.getAgentName() + " (" + entry.getRole() + ")");
                    writer.println();
                    writer.println(entry.getMessage());
                    writer.println();
                }

                writer.println("### Design Decision Summary");
                writer.println(result.getFinalDesignDecision());
                writer.println();

                if (result.getMermaidDiagram() != null && !result.getMermaidDiagram().isEmpty()) {
                    writer.println("### Architecture Diagram");
                    writer.println();
                    writer.println("```mermaid");
                    writer.println(result.getMermaidDiagram());
                    writer.println("```");
                    writer.println();
                }
            }

            log.info("Full conversation log saved to: {}", filename);
        } catch (IOException e) {
            log.error("Failed to save full conversation log: {}", e.getMessage());
        }
    }

    private void ensureOutputDir() {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
