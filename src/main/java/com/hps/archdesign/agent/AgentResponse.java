package com.hps.archdesign.agent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentResponse {
    private String agentName;
    private String role;
    private String content;

    @Override
    public String toString() {
        return String.format("[%s - %s]:\n%s", agentName, role, content);
    }
}
