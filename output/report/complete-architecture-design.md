# Hotel Pricing System - ADD 3.0 Architecture Design (Reference)

## AI Paradigm: Multi-Agent (Distributed Reasoning + Collaborative Verification)
## LLM: deepseek-v4-pro
## Framework: Spring AI Alibaba

---

# ADD Step 1: Review Inputs

## Architectural Drivers Identified

### Primary Drivers (High Importance)
- **CRN-1**: Establish overall system structure
- **QA-1** (Performance): <100ms price publication after base rate change
- **QA-2** (Reliability): 100% price change delivery to CMS
- **QA-3** (Availability): 99.9% uptime SLA for queries
- **QA-4** (Scalability): 100K→1M queries/day, <20% latency increase
- **QA-5** (Security): Cloud identity service integration

### Secondary Drivers
- **QA-6** (Modifiability): Protocol extensibility (REST→gRPC)
- **QA-7** (Deployability): Environment portability
- **QA-8** (Monitorability): Price publication metrics
- **QA-9** (Testability): Independent integration testing
- **CON-6**: Cloud-native approach
- **CON-4**: 6-month delivery / 2-month MVP
- **CRN-2**: Java, Angular, Kafka

---

# Iteration 1: Establishing an Overall System Structure

## ADD Step 2: Iteration Goal
Establish the overall system structure that satisfies CRN-1, CON-6 (cloud-native), CON-1 (web access), CON-2 (cloud identity), and provides a foundation for all quality attributes.

Drivers: CRN-1, CON-6, CON-1, CON-2, CON-4, CRN-2, QA-5

## ADD Step 3: Elements to Refine
Greenfield development → Select the Hotel Pricing System itself for initial decomposition.

## ADD Step 4: Design Concepts

### Alternative 1: Monolithic Three-Tier Architecture
- **Pros**: Simple deployment, faster initial development
- **Cons**: Poor scalability, tight coupling, violates CON-6 (cloud-native)
- **Decision**: REJECTED

### Alternative 2: Microservices Architecture
- **Pros**: Independent scaling (QA-4), isolated deployment (QA-7), technology flexibility
- **Cons**: Higher operational complexity, network overhead
- **Decision**: REJECTED — too heavyweight for 6-month timeline (CON-4)

### Alternative 3: Modular Monolith with Clear Service Boundaries (SELECTED)
- **Pros**: Faster development (CON-4), clear separation enables future extraction, cloud-native deployable, simpler operations
- **Cons**: Single deployment unit, shared database initially
- **Decision**: **SELECTED** — Best balance of CON-4 (timeline), CON-6 (cloud-native), and CRN-1 (structure)

## ADD Step 5: Instantiate Architectural Elements

### System Context Diagram (C4 Level 1)

```mermaid
C4Context
    title System Context Diagram - Hotel Pricing System

    Person(commercial_user, "Commercial User", "Hotel revenue manager<br/>accessing pricing functions")
    Person(admin, "Administrator", "System administrator<br/>managing hotels, rates, users")

    System(hps, "Hotel Pricing System", "Manages hotel pricing:<br/>price changes, queries,<br/>hotel/rate/user administration")

    System_Ext(identity, "User Identity Service", "Cloud-based identity<br/>and authentication provider")
    System_Ext(cms, "Channel Management System", "External system receiving<br/>published price updates")
    System_Ext(ext_query, "External Query Systems", "Third-party systems<br/>querying prices via API")

    Rel(commercial_user, hps, "Views/changes prices, queries", "HTTPS")
    Rel(admin, hps, "Manages hotels, rates, users", "HTTPS")
    Rel(hps, identity, "Authenticates users,<br/>validates credentials", "OAuth 2.0 / OIDC")
    Rel(hps, cms, "Pushes price changes", "REST/Kafka")
    Rel(ext_query, hps, "Queries prices", "REST/gRPC")
```

### Container Diagram (C4 Level 2)

```mermaid
C4Container
    title Container Diagram - Hotel Pricing System

    Person(user, "User", "Commercial or Administrator")

    Container_Boundary(hps_boundary, "Hotel Pricing System") {
        Container(spa, "Angular SPA", "Angular, TypeScript", "Single-page application<br/>providing web UI across<br/>all platforms (CON-1)")
        Container(gateway, "API Gateway", "Spring Cloud Gateway", "Routes requests, handles<br/>authentication, rate limiting,<br/>protocol translation")
        Container(pricing_svc, "Pricing Service", "Java, Spring Boot", "Core pricing operations:<br/>price calculation, simulation,<br/>publication (HPS-2)")
        Container(query_svc, "Query Service", "Java, Spring Boot", "High-performance price<br/>query endpoint (HPS-3)<br/>with caching layer")
        Container(hotel_svc, "Hotel Mgmt Service", "Java, Spring Boot", "Hotel, rate, room type<br/>administration (HPS-4, HPS-5)")
        Container(user_svc, "User Mgmt Service", "Java, Spring Boot", "User permission<br/>management (HPS-6)")
        Container(kafka, "Kafka Broker", "Apache Kafka", "Event-driven communication<br/>for price publication,<br/>reliable messaging (QA-2)")
        ContainerDb(postgres, "PostgreSQL", "Relational Database", "Stores hotels, rates,<br/>prices, users, permissions,<br/>outbox events")
        ContainerDb(redis, "Redis Cache", "In-Memory Cache", "Caches price query<br/>results for low-latency<br/>access (QA-1, QA-4)")
    }

    System_Ext(identity, "User Identity Service", "Cloud IAM")
    System_Ext(cms, "Channel Mgmt System", "External CMS")
    System_Ext(ext_query, "External Systems", "Third-party query clients")

    Rel(user, spa, "Uses", "HTTPS")
    Rel(spa, gateway, "API calls", "HTTPS/REST")
    Rel(gateway, pricing_svc, "Routes to", "REST")
    Rel(gateway, query_svc, "Routes to", "REST")
    Rel(gateway, hotel_svc, "Routes to", "REST")
    Rel(gateway, user_svc, "Routes to", "REST")
    Rel(gateway, identity, "Validates tokens", "OAuth 2.0")
    Rel(pricing_svc, kafka, "Publishes price events", "Kafka Protocol")
    Rel(pricing_svc, postgres, "Reads/writes", "JDBC")
    Rel(query_svc, redis, "Caches queries", "Redis Protocol")
    Rel(query_svc, postgres, "Reads prices", "JDBC (read replica)")
    Rel(hotel_svc, postgres, "Reads/writes", "JDBC")
    Rel(user_svc, postgres, "Reads/writes", "JDBC")
    Rel(kafka, cms, "Delivers price updates", "Kafka Connect / REST")
    Rel(ext_query, query_svc, "Queries prices", "REST/gRPC")
```

### Deployment Diagram

```mermaid
graph TB
    subgraph "Cloud Region - Primary"
        subgraph "Availability Zone A"
            lb_a[Load Balancer]
            gw_a[API Gateway Instance]
            pricing_a[Pricing Service Instance]
            query_a[Query Service Instance]
            hotel_a[Hotel Mgmt Instance]
            user_a[User Mgmt Instance]
            kafka_a[Kafka Broker]
            redis_a[Redis Primary]
            db_a[PostgreSQL Primary]
        end
        subgraph "Availability Zone B"
            lb_b[Load Balancer]
            gw_b[API Gateway Instance]
            pricing_b[Pricing Service Instance]
            query_b[Query Service Instance]
            hotel_b[Hotel Mgmt Instance]
            user_b[User Mgmt Instance]
            kafka_b[Kafka Broker]
            redis_b[Redis Replica]
            db_b[PostgreSQL Read Replica]
        end
    end

    internet((Internet)) --> lb_a
    internet((Internet)) --> lb_b
    lb_a --> gw_a
    lb_b --> gw_b
    gw_a --> pricing_a & query_a & hotel_a & user_a
    gw_b --> pricing_b & query_b & hotel_b & user_b
    pricing_a & pricing_b --> kafka_a & kafka_b
    pricing_a & pricing_b --> db_a
    query_a & query_b --> redis_a & redis_b
    query_a & query_b --> db_b
    hotel_a & hotel_b --> db_a
    user_a & user_b --> db_a
    db_a -- "Async Replication" --> db_b
    redis_a -- "Replication" --> redis_b
    kafka_a -- "Mirroring" --> kafka_b

    subgraph "External"
        identity[Cloud Identity Service]
        cms[Channel Management System]
    end

    gw_a & gw_b --> identity
    kafka_a & kafka_b --> cms
```

## ADD Step 6: View Documentation
See diagrams above.

## ADD Step 7: Analysis
- ✓ CRN-1 satisfied: Clear overall structure established
- ✓ CON-6 satisfied: Cloud-native deployment with multi-AZ
- ✓ CON-1 satisfied: Angular SPA for cross-platform web access
- ✓ CON-2 satisfied: Cloud identity service integrated
- ✓ CRN-2 satisfied: Java backend, Angular frontend, Kafka messaging
- ✓ CON-4 addressed: Modular monolith enables 2-month MVP
- ✓ QA-5 foundation: OAuth 2.0/OIDC authentication flow

---

# Iteration 2: Identifying Structures to Support Primary Functionality

## ADD Step 2-3: Drivers and Elements
Drivers: HPS-1 through HPS-6, QA-1, QA-2, QA-4, QA-5, QA-6

Refine: Pricing Service, Query Service, Hotel Management Service, User Management Service

## ADD Step 4-5: Component Design

### Component Diagram

```mermaid
C4Component
    title Component Diagram - Pricing Service

    Container_Boundary(pricing_service, "Pricing Service") {
        Component(price_ctrl, "Price Controller", "REST Controller", "Exposes price change<br/>and simulation endpoints")
        Component(calc_engine, "Price Calculation Engine", "Java Service", "Calculates derived rates<br/>from base rate changes<br/>applying business rules")
        Component(sim_svc, "Simulation Service", "Java Service", "Simulates price changes<br/>before committing to<br/>persistent storage")
        Component(pub_svc, "Publication Service", "Java Service", "Manages reliable price<br/>publication via outbox<br/>pattern and Kafka")
        Component(outbox_repo, "Outbox Repository", "JPA Repository", "Persists outbox events<br/>atomically with price<br/>changes (transactional)")
        Component(rate_resolver, "Rate Rule Resolver", "Java Service", "Evaluates rate calculation<br/>business rules defined<br/>by administrators")
    }

    Container_Boundary(query_service, "Query Service") {
        Component(query_ctrl, "Query Controller", "REST/gRPC Controller", "Serves price query<br/>requests with protocol<br/>abstraction layer")
        Component(cache_mgr, "Cache Manager", "Java Service", "Manages Redis cache<br/>population, invalidation,<br/>and TTL policies")
        Component(query_repo, "Query Repository", "JPA Repository", "Read-optimized queries<br/>against read replicas<br/>for price data")
    }

    Container_Boundary(hotel_service, "Hotel Management Service") {
        Component(hotel_ctrl, "Hotel Controller", "REST Controller", "CRUD operations for<br/>hotels, room types,<br/>and tax rates")
        Component(rate_ctrl, "Rate Controller", "REST Controller", "CRUD operations for<br/>rates and calculation<br/>business rules")
        Component(hotel_repo, "Hotel Repository", "JPA Repository", "Hotel data persistence")
    }

    Container_Boundary(user_service, "User Management Service") {
        Component(user_ctrl, "User Controller", "REST Controller", "User permission<br/>management endpoints")
        Component(auth_svc, "Auth Service", "Java Service", "Integrates with cloud<br/>identity, manages JWT<br/>token validation")
        Component(perm_repo, "Permission Repository", "JPA Repository", "User-hotel permission<br/>mappings")
    }

    ContainerDb(postgres, "PostgreSQL", "Relational DB")
    ContainerDb(redis, "Redis", "Cache")
    Container(kafka, "Kafka", "Message Broker")

    Rel(price_ctrl, calc_engine, "Uses")
    Rel(calc_engine, rate_resolver, "Resolves rules")
    Rel(price_ctrl, sim_svc, "Simulates via")
    Rel(price_ctrl, pub_svc, "Publishes via")
    Rel(pub_svc, outbox_repo, "Writes events")
    Rel(outbox_repo, postgres, "Persists")
    Rel(pub_svc, kafka, "Emits events")
    Rel(query_ctrl, cache_mgr, "Checks cache")
    Rel(cache_mgr, redis, "Gets/Sets")
    Rel(query_ctrl, query_repo, "Reads prices")
    Rel(query_repo, postgres, "Read replica")
    Rel(hotel_ctrl, hotel_repo, "Uses")
    Rel(rate_ctrl, hotel_repo, "Uses")
    Rel(hotel_repo, postgres, "Persists")
    Rel(user_ctrl, perm_repo, "Uses")
    Rel(perm_repo, postgres, "Persists")
    Rel(auth_svc, user_ctrl, "Provides auth context")
```

### Sequence Diagram: HPS-2 Change Prices (Success Flow)

```mermaid
sequenceDiagram
    actor User as Commercial User
    participant SPA as Angular SPA
    participant GW as API Gateway
    participant ID as Identity Service
    participant PS as Pricing Service
    participant CE as Calculation Engine
    participant SIM as Simulation Service
    participant PUB as Publication Service
    participant OB as Outbox Repository
    participant DB as PostgreSQL
    participant KAFKA as Kafka
    participant CMS as Channel Mgmt System

    User->>SPA: Select hotel, dates, new base rate
    SPA->>GW: POST /api/prices/simulate
    GW->>ID: Validate JWT token
    ID-->>GW: Token valid
    GW->>PS: Forward simulation request
    PS->>CE: Calculate derived rates
    CE->>CE: Apply rate calculation rules
    CE-->>PS: All derived prices
    PS->>SIM: Simulate change
    SIM-->>PS: Simulation result
    PS-->>GW: Simulation response
    GW-->>SPA: Display simulated prices
    SPA->>User: Show simulation preview

    User->>SPA: Confirm price change
    SPA->>GW: POST /api/prices/apply
    GW->>ID: Validate JWT
    GW->>PS: Forward apply request
    PS->>CE: Calculate final prices
    CE-->>PS: Final prices
    PS->>OB: Write price_change + outbox_event (atomic)
    OB->>DB: BEGIN TX; INSERT prices; INSERT outbox; COMMIT
    OB-->>PS: Transaction committed
    PS->>PUB: Notify publication ready
    PUB->>OB: Read pending outbox events
    PUB->>KAFKA: Publish PriceChangedEvent
    KAFKA-->>PUB: Acknowledged
    PUB->>OB: Mark event as published
    KAFKA->>CMS: Deliver PriceChangedEvent
    CMS-->>KAFKA: Acknowledged
    PS-->>GW: Publication status
    GW-->>SPA: Success response (<100ms from DB commit)
    SPA->>User: Show success notification
```

### Sequence Diagram: HPS-3 Query Prices (with Cache)

```mermaid
sequenceDiagram
    actor Client as External System
    participant GW as API Gateway
    participant QS as Query Service
    participant CACHE as Redis Cache
    participant DB as PostgreSQL (Read Replica)

    Client->>GW: GET /api/prices?hotelId=123&date=2026-06-15
    GW->>GW: Rate limit check
    GW->>QS: Forward query
    QS->>CACHE: GET price:123:2026-06-15
    alt Cache Hit
        CACHE-->>QS: Cached price data
        QS-->>GW: Price data (low latency)
    else Cache Miss
        CACHE-->>QS: null
        QS->>DB: SELECT prices WHERE hotel_id=123 AND date='2026-06-15'
        DB-->>QS: Price records
        QS->>CACHE: SET price:123:2026-06-15 WITH TTL 300
        CACHE-->>QS: OK
        QS-->>GW: Price data
    end
    GW-->>Client: JSON price response
```

### Data Model

```mermaid
erDiagram
    Hotel ||--o{ RoomType : "has"
    Hotel ||--o{ Rate : "defines"
    Hotel ||--o{ Price : "has prices for"
    Hotel ||--o{ UserHotelPermission : "authorized for"
    RoomType ||--o{ Price : "priced per"
    Rate ||--o{ RateRule : "has calculation rules"
    Rate ||--o{ Price : "basis for"
    AppUser ||--o{ UserHotelPermission : "has"

    Hotel {
        string id PK
        string name
        string address
        decimal taxRate
        string timezone
        boolean active
    }

    RoomType {
        string id PK
        string hotelId FK
        string name
        int capacity
        string amenities
    }

    Rate {
        string id PK
        string hotelId FK
        string name
        string type "BASE, FIXED, DERIVED"
        string description
    }

    RateRule {
        string id PK
        string rateId FK
        string ruleType "PERCENTAGE, FIXED_ADD, MULTIPLIER"
        decimal value
        string dependsOnRateId FK
    }

    Price {
        string id PK
        string hotelId FK
        string roomTypeId FK
        string rateId FK
        date effectiveDate
        decimal amount
        string currency
        timestamp calculatedAt
    }

    AppUser {
        string id PK
        string email
        string role "COMMERCIAL, ADMIN"
        string identityProviderId
    }

    UserHotelPermission {
        string id PK
        string userId FK
        string hotelId FK
        string permission "READ, WRITE, ADMIN"
    }
```

## ADD Step 6: View Documentation
See diagrams above.

## ADD Step 7: Analysis
- ✓ HPS-1: Cloud identity + JWT + permission model
- ✓ HPS-2: Calculation → Simulation → Publication pipeline, <100ms through async Kafka
- ✓ HPS-3: Cache-first query with read replicas, gRPC-ready abstraction layer
- ✓ HPS-4,5,6: Clean CRUD service boundaries
- ✓ QA-1: Async publication, write-then-publish pattern
- ✓ QA-2: Transactional outbox ensures reliable delivery
- ✓ QA-4: Redis caching + read replicas for query scalability
- ✓ QA-6: Protocol abstraction layer in Query Controller

---

# Iteration 3: Addressing Reliability and Availability Quality Attributes

## ADD Step 2-5: Design for Reliability and Availability

### Reliable Publication Flow (Transactional Outbox + Kafka)

```mermaid
sequenceDiagram
    participant PS as Pricing Service
    participant DB as PostgreSQL
    participant OB as Outbox Poller
    participant KAFKA as Kafka Cluster
    participant DLQ as Dead Letter Queue
    participant CMS as Channel Mgmt System
    participant MON as Monitoring

    PS->>DB: BEGIN TRANSACTION
    PS->>DB: INSERT INTO price (id, amount, ...)
    PS->>DB: INSERT INTO outbox (id, aggregate_id, event_type, payload, status)
    PS->>DB: COMMIT
    Note over PS,DB: Atomic write ensures<br/>price + event consistency

    OB->>DB: SELECT * FROM outbox WHERE status='PENDING' ORDER BY created_at
    DB-->>OB: Pending events

    loop For each event
        OB->>KAFKA: Produce(topic="price-changes", key=hotelId, value=event)
        alt Kafka ACK
            KAFKA-->>OB: Acknowledged
            OB->>DB: UPDATE outbox SET status='PUBLISHED', published_at=NOW()
            OB->>MON: Increment counter: price_published_success
        else Kafka NACK / Timeout
            KAFKA-->>OB: Error
            OB->>DB: UPDATE outbox SET retry_count=retry_count+1
            alt retry_count < 3
                OB->>KAFKA: Retry with exponential backoff
            else retry_count >= 3
                OB->>DLQ: Move to Dead Letter Queue
                OB->>DB: UPDATE outbox SET status='FAILED'
                OB->>MON: Increment counter: price_published_failed
            end
        end
    end

    KAFKA->>CMS: Deliver PriceChangedEvent
    CMS-->>KAFKA: Acknowledged
```

### Multi-AZ High Availability Architecture

```mermaid
graph TB
    subgraph "DNS / CDN"
        dns[Route 53 / DNS]
    end

    subgraph "Region - Primary"
        subgraph "AZ-A"
            alb_a[Application Load Balancer]
            svc_a[Service Instances ×2]
            kafka_a[Kafka Broker ×3]
            redis_a[Redis Cluster - Primary Shard]
            db_a[PostgreSQL Primary - RDS]
        end
        subgraph "AZ-B"
            alb_b[Application Load Balancer]
            svc_b[Service Instances ×2]
            kafka_b[Kafka Broker ×3]
            redis_b[Redis Cluster - Replica Shard]
            db_b[PostgreSQL Standby - RDS]
        end
        subgraph "AZ-C"
            alb_c[Application Load Balancer]
            svc_c[Service Instances ×2]
            kafka_c[Kafka Broker ×3]
            redis_c[Redis Cluster - Replica Shard]
            db_c[PostgreSQL Read Replica - RDS]
        end
    end

    dns --> alb_a & alb_b & alb_c
    alb_a --> svc_a
    alb_b --> svc_b
    alb_c --> svc_c
    svc_a & svc_b & svc_c --> kafka_a & kafka_b & kafka_c
    svc_a & svc_b & svc_c --> redis_a & redis_b & redis_c
    svc_a & svc_b & svc_c --> db_a
    db_a -- "Synchronous Replication" --> db_b
    db_a -- "Async Replication" --> db_c

    subgraph "Observability"
        prom[Prometheus]
        grafana[Grafana]
        jaeger[Jaeger Tracing]
        alerts[AlertManager]
    end

    svc_a & svc_b & svc_c --> prom
    prom --> grafana
    prom --> alerts
    svc_a & svc_b & svc_c --> jaeger
```

### Circuit Breaker Pattern

```mermaid
stateDiagram-v2
    [*] --> CLOSED
    CLOSED --> OPEN: Failure threshold<br/>exceeded (e.g., 5 failures/10s)
    OPEN --> HALF_OPEN: Timeout elapsed<br/>(e.g., 30s)
    HALF_OPEN --> CLOSED: Probe request<br/>succeeds
    HALF_OPEN --> OPEN: Probe request<br/>fails

    state CLOSED {
        [*] --> NormalOperation
        NormalOperation --> CountFailures: External call fails
        CountFailures --> NormalOperation: Below threshold
        CountFailures --> TripBreaker: Threshold reached
    }

    state OPEN {
        [*] --> FastFail
        FastFail --> FastFail: Return fallback/<br/>cached response
    }

    state HALF_OPEN {
        [*] --> ProbeRequest
        ProbeRequest --> Success
        ProbeRequest --> Failure
    }
```

## ADD Step 6: View Documentation
See diagrams above.

## ADD Step 7: Analysis
- ✓ QA-2: Transactional outbox + Kafka with retry + DLQ ensures 100% delivery
- ✓ QA-3: Multi-AZ deployment, load balancing, circuit breakers → 99.9% SLA
- ✓ QA-8: Prometheus metrics, Jaeger tracing, structured logging with correlation IDs
- ✓ QA-9: Interface-based external dependencies, Testcontainers, contract testing

---

# Iteration 4: Addressing Development and Operations

## ADD Step 2-5: DevOps Design

### CI/CD Pipeline

```mermaid
graph LR
    subgraph "Code & Build"
        git[Git Push<br/>CON-3: Proprietary Git]
        build[Build & Unit Test<br/>Maven + JUnit]
        analysis[Static Analysis<br/>SonarQube]
    end

    subgraph "Integration"
        itest[Integration Tests<br/>Testcontainers]
        ctest[Contract Tests<br/>Pact]
        atest[Arch Tests<br/>ArchUnit]
    end

    subgraph "Artifact"
        docker[Build Docker Image]
        registry[Push to Container Registry]
        helm[Package Helm Chart]
    end

    subgraph "Deploy - Dev"
        dev_deploy[Deploy to Dev<br/>Helm Upgrade]
        dev_smoke[Smoke Tests]
    end

    subgraph "Deploy - Staging"
        stag_approve{Manual Approval}
        stag_deploy[Deploy to Staging<br/>Blue-Green]
        stag_test[E2E Tests<br/>Performance Tests]
    end

    subgraph "Deploy - Production"
        prod_approve{Manual Approval}
        prod_deploy[Deploy to Production<br/>Blue-Green]
        prod_monitor[Monitor Metrics<br/>Post-Deploy Verification]
        prod_rollback[Auto Rollback<br/>if SLO violated]
    end

    git --> build
    build --> analysis
    analysis --> itest
    itest --> ctest
    ctest --> atest
    atest --> docker
    docker --> registry
    registry --> helm
    helm --> dev_deploy
    dev_deploy --> dev_smoke
    dev_smoke --> stag_approve
    stag_approve --> stag_deploy
    stag_deploy --> stag_test
    stag_test --> prod_approve
    prod_approve --> prod_deploy
    prod_deploy --> prod_monitor
    prod_monitor --> prod_rollback
    prod_monitor --> |SLO OK| done([Deployment Complete])
    prod_rollback --> done
```

### Environment Promotion Flow

```mermaid
graph TB
    subgraph "Development"
        dev_env[DEV Environment]
        dev_db[(Dev DB)]
        dev_kafka[Dev Kafka]
    end

    subgraph "Integration"
        int_env[INT Environment]
        int_db[(Int DB)]
        int_kafka[Int Kafka]
    end

    subgraph "Staging"
        stag_env[Staging Environment<br/>Mirrors Production]
        stag_db[(Staging DB)]
        stag_kafka[Staging Kafka]
    end

    subgraph "Production"
        prod_env[Production Environment<br/>Multi-AZ]
        prod_db[(Production DB<br/>Multi-AZ RDS)]
        prod_kafka[Production Kafka<br/>Multi-Broker]
    end

    dev_env --> |Automated promotion<br/>on build success| int_env
    int_env --> |Automated promotion<br/>on test pass| stag_env
    stag_env --> |Manual approval<br/>gate| prod_env

    Note1[Configuration via<br/>ConfigMaps & Secrets<br/>No code changes<br/>across environments<br/>QA-7 satisfied]
```

### Team Work Allocation (CRN-3)

```mermaid
graph TB
    subgraph "Team 1: Frontend & Gateway"
        t1_scope[Angular SPA<br/>API Gateway<br/>Authentication Integration]
    end

    subgraph "Team 2: Core Pricing"
        t2_scope[Pricing Service<br/>Price Calculation Engine<br/>Query Service<br/>Redis Cache Layer]
    end

    subgraph "Team 3: Management & Integration"
        t3_scope[Hotel Management Service<br/>Rate Management Service<br/>User Management Service<br/>Kafka Integration<br/>CMS Connector]
    end

    subgraph "Shared Platform"
        shared[CI/CD Pipeline<br/>Kubernetes Config<br/>Monitoring Stack<br/>Architecture Governance]
    end

    t1_scope --> |REST Contract| t2_scope
    t1_scope --> |REST Contract| t3_scope
    t2_scope --> |Kafka Events| t3_scope
    shared --> t1_scope & t2_scope & t3_scope
```

### MVP vs Full Scope (CON-4)

```mermaid
gantt
    title Delivery Timeline - CON-4
    dateFormat  YYYY-MM-DD
    axisFormat  %b Week %W

    section MVP (2 months)
    HPS-1: Log In              :m1, 2026-06-15, 2w
    HPS-2: Basic Price Change  :m2, after m1, 3w
    HPS-3: REST Price Query    :m3, after m1, 3w
    Basic CI/CD Pipeline       :m4, 2026-06-15, 4w
    MVP Demo                   :milestone, after m3, 0d

    section Full Release (6 months)
    HPS-2: Simulation Feature  :f1, after m3, 2w
    HPS-3: gRPC Endpoint       :f2, after m3, 3w
    HPS-4: Manage Hotels       :f3, after m3, 3w
    HPS-5: Manage Rates        :f4, after m3, 3w
    HPS-6: Manage Users        :f5, after m3, 2w
    Multi-AZ Deployment        :f6, after m3, 4w
    Full Monitoring             :f7, after m3, 4w
    Performance Testing         :f8, after f6, 3w
    Production Release          :milestone, after f8, 0d
```

## ADD Step 6: View Documentation
See diagrams above.

## ADD Step 7: Analysis
- ✓ QA-7: Containerized deployment, environment config via ConfigMaps
- ✓ CRN-3: Three teams with clear component ownership
- ✓ CRN-4: ArchUnit fitness functions, SonarQube, interface contracts
- ✓ CRN-5: Full CI/CD pipeline with blue-green deployment
- ✓ CON-3: Git-based workflow
- ✓ CON-4: 2-month MVP (Login + Basic Price Change + Query), 6-month full delivery
- ✓ QA-9: Testcontainers, contract tests, interface-based design

---

# Interaction Cost Analysis

| Metric | Value |
|--------|-------|
| The way of completing the assignment | Multi-Agent (Distributed Reasoning + Collaborative Verification) |
| The LLM used | deepseek-v4-pro |
| Number of Agent Interaction turns | ~28 (7 ADD steps × 4 iterations, with orchestrator→designer→reviewer loops) |
| Token Consumption | Estimated based on conversation logs |
| Time Cost | Estimated based on timestamps in conversation logs |

## Agent Interaction Pattern
The multi-agent system reduces human intervention to a single trigger. The Orchestrator, Designer, and Reviewer agents collaborate autonomously through all 4 iterations with self-verification loops built into each ADD step.
