package com.hps.archdesign.runner;

import com.hps.archdesign.model.IterationResult;
import com.hps.archdesign.service.ADDOrchestrationService;
import com.hps.archdesign.service.ReportGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ADDRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ADDRunner.class);

    private final ADDOrchestrationService orchestrationService;
    private final ReportGenerationService reportService;

    public ADDRunner(ADDOrchestrationService orchestrationService,
                     ReportGenerationService reportService) {
        this.orchestrationService = orchestrationService;
        this.reportService = reportService;
    }

    @Override
    public void run(String... args) {
        log.info("=".repeat(80));
        log.info("Hotel Pricing System - ADD 3.0 Multi-Agent Architecture Design");
        log.info("AI Paradigm: Multi-Agent (Distributed Reasoning + Collaborative Verification)");
        log.info("LLM: deepseek-v4-pro");
        log.info("Framework: Spring AI Alibaba");
        log.info("Start Time: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        log.info("=".repeat(80));

        try {
            List<IterationResult> results = orchestrationService.executeAllIterations();

            log.info("=".repeat(80));
            log.info("All iterations complete. Generating report...");
            log.info("=".repeat(80));

            reportService.generateReport(results);

            log.info("=".repeat(80));
            log.info("Architecture design complete!");
            log.info("Output files are in ./output/ directory");
            log.info("End Time: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            log.info("=".repeat(80));

        } catch (Exception e) {
            log.error("Error during architecture design process", e);
        }
    }
}
