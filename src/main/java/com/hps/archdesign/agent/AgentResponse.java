package com.hps.archdesign.agent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentResponse {
    private String agentName;
    private String role;
    private String content;
    private Long promptTokens;
    private Long completionTokens;
    private Long totalTokens;

    public AgentResponse(String agentName, String role, String content) {
        this(agentName, role, content, null, null, null);
    }

    public boolean hasTokenUsage() {
        return totalTokens != null && totalTokens > 0;
    }

    public String getTokenSummary() {
        if (!hasTokenUsage()) return "N/A";
        return String.format("input=%d, output=%d, total=%d",
                promptTokens, completionTokens, totalTokens);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s - %s]", agentName, role));
        if (hasTokenUsage()) {
            sb.append(String.format(" [tokens: %s]", getTokenSummary()));
        }
        sb.append(":\n").append(content);
        return sb.toString();
    }
}
