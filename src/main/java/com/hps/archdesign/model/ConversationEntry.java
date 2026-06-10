package com.hps.archdesign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationEntry {
    private String agentName;
    private String role;
    private String message;
    private String timestamp;

    public static ConversationEntry of(String agentName, String role, String message) {
        return ConversationEntry.builder()
                .agentName(agentName)
                .role(role)
                .message(message)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public String toFormattedString() {
        return String.format("[%s] [%s - %s]:\n%s\n", timestamp, agentName, role, message);
    }
}
