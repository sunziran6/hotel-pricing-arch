# Hotel Pricing System — ADD 3.0 Multi-Agent Architecture Design

Software Architecture (2026) Assignment 2  
**AI Paradigm**: Multi-Agent (Distributed Reasoning + Collaborative Verification)  
**LLM**: deepseek-v4-pro  
**Framework**: Spring AI Alibaba

## Quick Start

### 1. Configure API Key

Edit `src/main/resources/application.yml`:

```yaml
spring:
  ai:
    dashscope:
      api-key: ${DASHSCOPE_API_KEY:your-actual-api-key-here}
```

Or set environment variable:

```bash
export DASHSCOPE_API_KEY="your-api-key"
```

### 2. Run

```bash
cd hotel-pricing-arch
mvn spring-boot:run
```

The application will:
1. Initialize three agents (Orchestrator, Designer, Reviewer)
2. Execute ADD 3.0 across 4 iterations
3. Generate conversation logs with timestamps
4. Produce the final architecture design report

### 3. Output

```
output/
├── conversation-logs/
│   ├── iteration-01-conversation-log.md
│   ├── iteration-02-conversation-log.md
│   ├── iteration-03-conversation-log.md
│   ├── iteration-04-conversation-log.md
│   └── full-conversation-log.md
└── report/
    ├── ADD-Report-Hotel-Pricing-System.md
    └── complete-architecture-design.md
```

## Project Structure

```
src/main/java/com/hps/archdesign/
├── HpsArchDesignApplication.java       # Spring Boot entry point
├── config/
│   └── ChatClientConfig.java           # ChatClient bean
├── agent/
│   ├── OrchestratorAgent.java          # Coordinator: task decomposition, conflict resolution
│   ├── DesignerAgent.java              # Designer: architecture decisions, Mermaid diagrams
│   ├── ReviewerAgent.java              # Reviewer: QA verification, constraint checking
│   ├── AgentResponse.java              # Agent response model
│   └── PriorKnowledgeLoader.java       # Loads ADD 3.0 method + case study
├── model/
│   ├── IterationResult.java            # Iteration result with steps + conversation
│   ├── ADDStep.java                    # Single ADD step
│   └── ConversationEntry.java          # Timestamped conversation entry
├── service/
│   ├── ADDOrchestrationService.java    # Core: multi-agent ADD 3.0 workflow
│   ├── ConversationLogService.java     # Conversation logging + persistence
│   └── ReportGenerationService.java    # Final report with Mermaid diagrams
└── runner/
    └── ADDRunner.java                  # CLI runner
```

## Multi-Agent Design

| Agent | System Prompt Highlights |
|-------|-------------------------|
| **Orchestrator** | ADD 3.0 workflow management, driver prioritization (High > Medium), cloud-native preference (CON-6), REST-first with protocol extensibility (CON-5) |
| **Designer** | View generation via Mermaid, design rationale referencing specific drivers, cloud-native patterns, Java/Angular/Kafka stack per CRN-2 |
| **Reviewer** | QA-1 through QA-9 scenario verification, CON-1 through CON-6 constraint compliance, Mermaid syntax checking, completeness validation |

## Four Iterations

| # | Goal | Key Drivers |
|---|------|-------------|
| 1 | Establishing Overall System Structure | CRN-1, CON-6, CON-1, CON-2, CON-4, CRN-2, QA-5 |
| 2 | Identifying Structures to Support Primary Functionality | HPS-1~6, QA-1, QA-2, QA-4, QA-5, QA-6 |
| 3 | Addressing Reliability and Availability | QA-2, QA-3, QA-8, QA-9 |
| 4 | Addressing Development and Operations | QA-7, CRN-3, CRN-4, CRN-5, CON-3, CON-4 |

## Key Architecture Decisions

- **Modular Monolith** over full microservices — balances CON-4 (6-month delivery) with CON-6 (cloud-native)
- **Transactional Outbox + Kafka** — guarantees QA-2 (100% reliable price publication)
- **Redis Cache + Read Replicas** — satisfies QA-1 (<100ms) and QA-4 (1M queries/day)
- **Multi-AZ Deployment** with circuit breakers — achieves QA-3 (99.9% SLA)
- **Interface-based external dependencies** — enables QA-9 (independent testing)

## Requirements Compliance

- All views generated using **Mermaid** code blocks
- No external domain knowledge beyond provided ADD 3.0 method and case study
- No few-shot examples or handcrafted demonstration outputs in prompts
- All decision rules explicitly derived from provided system instructions
- Agents perform self-verification and task decomposition as instructed

## Prerequisites

- Java 21
- Maven 3.9+
- DashScope API key (for deepseek-v4-pro access)

## Dependencies

- Spring Boot 3.4.1
- Spring AI Alibaba 1.0.0-M5
- Lombok
- Jackson
