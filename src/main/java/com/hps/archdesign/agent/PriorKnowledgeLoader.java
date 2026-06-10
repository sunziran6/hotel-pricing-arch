package com.hps.archdesign.agent;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PriorKnowledgeLoader {

    private String addMethod;
    private String caseStudy;

    @PostConstruct
    public void init() {
        this.addMethod = loadResource("prompts/add-method.txt");
        this.caseStudy = loadResource("prompts/case-study.txt");
    }

    private String loadResource(String path) {
        try {
            return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prompt resource: " + path, e);
        }
    }

    public String getAddMethod() {
        return addMethod;
    }

    public String getCaseStudy() {
        return caseStudy;
    }

    public String getCombinedKnowledge() {
        return addMethod + "\n\n" + caseStudy;
    }
}
