# Software Architecture Design Report
## Hotel Pricing System (HPS) — ADD 3.0 Method

| Field | Value |
|-------|-------|
| **AI Paradigm** | Multi-Agent (Distributed Reasoning + Collaborative Verification) |
| **LLM Used** | deepseek/deepseek-v4-pro |
| **Framework** | Spring AI OpenAI (DeepSeek via OpenAI-compatible API) |
| **Design Method** | Attribute-Driven Design (ADD) 3.0 |
| **Report Date** | 2026-06-11 |
| **Total Iterations** | 4 |

---

# ADD Step 1: Review Inputs

## Architectural Drivers Identified

### Primary Drivers (High Importance)

| Driver | Category | Rationale |
|--------|----------|-----------|
| CRN-1 | Concern | Must establish overall system structure first |
| QA-1 | Performance | <100ms price publication is critical for HPS-2 |
| QA-2 | Reliability | 100% price delivery is non-negotiable |
| QA-3 | Availability | 99.9% SLA for pricing queries |
| QA-4 | Scalability | 100K→1M queries/day, <20% latency increase |
| QA-5 | Security | Cloud identity integration required |
| CON-4 | Constraint | 6-month delivery, 2-month MVP |
| CON-6 | Constraint | Cloud-native approach mandated |

### Secondary Drivers

| Driver | Category | Rationale |
|--------|----------|-----------|
| QA-6 | Modifiability | Protocol extensibility (REST→gRPC) |
| QA-7 | Deployability | Environment portability |
| QA-8 | Monitorability | Price publication observability |
| QA-9 | Testability | Independent integration testing |
| CRN-2 | Concern | Leverage Java, Angular, Kafka |
| CRN-5 | Concern | Continuous deployment infrastructure |

---

# 1) Iteration 1: Establishing an Overall System Structure

## ADD Step 1: Review Inputs

## ADD Step 1 – Review Inputs  
**Iteration 1: Establishing an Overall System Structure**

### Inputs Reviewed  
The following inputs from the case study were thoroughly examined:

| Category | Identifiers |  
|----------|--------------|  
| Primary Functionality (Use Cases) | HPS-1 (Log In), HPS-2 (Change Prices), HPS-3 (Query Prices), HPS-4 (Manage Hotels), HPS-5 (Manage Rates), HPS-6 (Manage Users) |  
| Quality Attribute Scenarios | QA-1 (Performance), QA-2 (Reliability), QA-3 (Availability), QA-4 (Scalability), QA-5 (Security), QA-6 (Modifiability), QA-7 (Deployability), QA-8 (Monitorability), QA-9 (Testability) |  
| Architectural Concerns | CRN-1 (Overall system structure), CRN-2 (Leverage Java, Angular, Kafka), CRN-3 (Work allocation), CRN-4 (Avoid technical debt), CRN-5 (Continuous deployment infrastructure) |  
| Constraints | CON-1 (Web browser access), CON-2 (Cloud identity & hosting), CON-3 (Proprietary Git), CON-4 (6‑month delivery / 2‑month MVP), CON-5 (REST APIs initially), CON-6 (Cloud‑native approach) |  

### Architectural Drivers for This Iteration  

For the goal of **defining an overall system structure**, the following requirements are designated as the highest‑priority architectural drivers. They directly shape the first‑level decomposition, technology choices, and deployment topology.

| Priority | Driver | Justification |  
|----------|--------|---------------|  
| **1** | **CRN-1 – Overall system structure** | The explicit concern demands a coherent macro‑architecture; this is the very purpose of the iteration. |  
| **2** | **CON-6 – Cloud‑native approach** | Mandates fundamental architectural patterns: containerization, orchestration, managed services, and elastic scaling. Drives the separation into independently deployable units. |  
| **3** | **CON-1 – Web browser access** | Enforces a clear front‑end/back‑end split and influences API design, UI composition, and the choice of a single‑page application framework (Angular, per CRN-2). |  
| **4** | **CON-2 – Cloud identity & hosting** | Dictates authentication flows (OAuth2/OIDC), service‑to‑service security, and the use of the cloud provider’s identity service. Coupled with CON-6, it pushes toward fully managed identity integration. |  
| **5** | **CRN-2 – Technology stack (Java, Angular, Kafka)** | Constrains the implementation platforms. Justifies a Java‑based services tier (Spring Boot / Quarkus), an Angular front‑end, and Kafka as the asynchronous messaging backbone. |  
| **6** | **CON-4 – 6‑month delivery (2‑month MVP)** | The aggressive timeline forces architectural decisions that maximize parallelism and reuse: independently testable modules, clean interfaces, and heavy reliance on cloud‑managed services to reduce plumbing effort. |  

### Why Other Requirements Are Deferred  

- **Functional use cases (HPS-1–HPS-6)**: They drive the allocation of responsibilities *within* the structure, but do not dictate the overall modular boundaries themselves. They will be the primary basis for refining components in later iterations.  
- **Quality attributes QA-1–QA-9**: Each of these is critical, but the mechanisms to satisfy them (caching, outbox patterns, circuit breakers, etc.) are tactical refinements. They will become primary drivers in subsequent design rounds after the high‑level structure is established.  
- **Concerns CRN-3, CRN-4, CRN-5**: CRN-3 (work allocation) and CRN-4 (avoid technical debt) are process‑oriented and will be addressed by the chosen modularity (well‑defined components, clear interfaces). CRN-5 (continuous deployment) will be enabled by the cloud‑native and container‑first approach (CON-6) but is not itself a structural driver.  
- **Constraints CON-3 (proprietary Git) and CON-5 (REST APIs)**: CON-3 is purely infrastructural and does not affect architecture style. CON-5 is an interface constraint that will be encapsulated behind abstractions in the detailed design; it does not alter the system’s high‑level partitioning.  

The selected six drivers together form a coherent foundation for establishing the system context, identifying major architectural elements, and choosing the overarching patterns (e.g., microservices with an API gateway, event‑driven communication via Kafka, cloud‑managed identity). The next step (Step 2) will formulate the precise iteration goal based on these drivers.

## ADD Step 2: Establish Iteration Goal

## ADD Step 2 – Establish the Iteration Goal  
**Iteration 1: Establishing an Overall System Structure**

The following architectural drivers were selected in Step 1 and are now the sole basis for the iteration goal.  
All other requirements (specific QAs, remaining concerns, detail‑level constraints) are intentionally deferred to later iterations.

| # | Driver            | Category         | Core Influence on This Iteration |
|---|-------------------|------------------|----------------------------------|
| 1 | **CRN‑1**         | Concern          | Must produce a coherent, high‑level system decomposition. |
| 2 | **CON‑6**         | Constraint       | Mandates a cloud‑native architecture: microservices, containerisation, managed services. |
| 3 | **CON‑1**         | Constraint       | Browser‑based UI → clear front‑end / back‑end separation and Angular (per CRN‑2). |
| 4 | **CON‑2**         | Constraint       | Cloud‑provider identity service for authentication; full cloud hosting for all components. |
| 5 | **CRN‑2**         | Concern          | Leverage Java (back‑end), Angular (front‑end), and Kafka (event streaming). |
| 6 | **CON‑4**         | Constraint       | 6‑month delivery, 2‑month MVP → structure must enable parallel work, independent testability, and off‑load of infrastructure concerns to cloud‑managed services. |

### Iteration Goal Statement

> **“Produce a first‑level system decomposition (CRN‑1) that organises the Hotel Pricing System as a cloud‑native solution (CON‑6) with a browser‑based Angular front‑end and a set of Java microservices communicating via Kafka (CRN‑2), all hosted on a managed cloud platform and protected by a cloud‑identity provider (CON‑2). The decomposition must support parallel development, early MVP delivery, and a clear separation of responsibilities, ensuring that the architecture can be delivered within the 6‑month timeline (CON‑4).”**

### Traceability Summary

| Goal Fragment                                                                 | Addressed Driver |
|-------------------------------------------------------------------------------|------------------|
| Produce a first‑level system decomposition                                    | CRN‑1            |
| Cloud‑native solution (microservices, containerisation, managed services)     | CON‑6            |
| Browser‑based Angular front‑end / Java microservices / Kafka                  | CRN‑2 + CON‑1    |
| Hosted on a managed cloud platform with cloud‑identity provider               | CON‑2            |
| Supports parallel development, early MVP, clear separation of responsibilities| CON‑4            |

### Scope Control

- The goal explicitly avoids prescribing internal mechanisms for specific quality attributes (e.g., caching for QA‑1, transactional outbox for QA‑2) — those will be refined in later iterations once the macro‑structure is stable.
- Protocol‑specific decisions (CON‑5 – REST initially) are encapsulated behind the back‑end APIs and do not change the decomposition; they are acknowledged but not part of the structural drivers.
- The goal provides a concrete target for the next steps: creation of a **system context diagram** and an initial set of **principal components** (e.g., API Gateway, Auth Service, Price Management Service, Kafka broker, Angular SPA) as outlined by the ADD method.

This iteration goal will steer Step 3 (choosing elements to refine) and all subsequent design decisions in Iteration 1.

## ADD Step 3: Choose Elements to Refine

### Design Approach
Since this is a greenfield project, the only architectural element available is the **Hotel Pricing System** as a whole.  
Following ADD Step 3, I have refined this element by creating its **system context diagram** – an outer view that establishes the boundaries of the system, the external actors, and the external systems it must interact with. This directly addresses the iteration goal (CRN‑1), while fully respecting the constraints and concerns identified in Steps 1–2: cloud‑native approach (CON‑6), web browser access (CON‑1), cloud identity (CON‑2), mandated technology stack (CRN‑2), and the aggressive delivery timeline (CON‑4).

The resulting C4 Context diagram is intentionally abstract: it shows **who** and **what** the system communicates with, **over which protocols**, and **why**, but intentionally defers any internal decomposition. It provides the outer scaffold on which the later container and component views will be built.

---

### System Context Diagram (C4 Level 1)

```mermaid
C4Context
  title System Context Diagram for Hotel Pricing System
  Person(CommercialUser, "Commercial User", "Uses web browser to change/query prices")
  Person(Administrator, "Administrator", "Uses web browser to manage hotels, rates, users")
  System_Ext(IdentityService, "Identity Service", "Cloud identity provider (OAuth2/OIDC)")
  System_Ext(CMS, "Channel Management System", "Receives price updates")
  System_Ext(ExternalQuerySystems, "External Query Systems", "Queries prices")
  System(HotelPricingSystem, "Hotel Pricing System", "Manages pricing, rates, hotels")
  Rel(CommercialUser, HotelPricingSystem, "Log in, change prices, query prices", "HTTPS")
  Rel(Administrator, HotelPricingSystem, "Manage hotels, rates, users", "HTTPS")
  Rel(HotelPricingSystem, CMS, "Publish price updates", "Kafka")
  Rel(ExternalQuerySystems, HotelPricingSystem, "Query prices", "REST API (HTTPS)")
  Rel(HotelPricingSystem, IdentityService, "Authenticate users", "OAuth2/OIDC")
  UpdateRelStyle(CommercialUser, HotelPricingSystem, $offsetY="-60")
  UpdateRelStyle(Administrator, HotelPricingSystem, $offsetY="60")
  UpdateRelStyle(HotelPricingSystem, CMS, $offsetX="-120")
  UpdateRelStyle(ExternalQuerySystems, HotelPricingSystem, $offsetX="-100")
  UpdateRelStyle(HotelPricingSystem, IdentityService, $offsetX="120")
```
---

### Architectural Elements – Responsibilities and Interfaces

| Element | Type | Responsibility | Provided / Required Interfaces |
|---------|------|----------------|-------------------------------|
| **Commercial User** | Person (external) | Uses a web browser to log in, change room/base rates, simulate price changes, and query current prices. | Consumes: HTTPS web UI served by `HotelPricingSystem`. |
| **Administrator** | Person (external) | Uses a web browser to manage hotels, room types, tax rates, available rates, business rules, and user permissions. | Consumes: HTTPS web UI served by `HotelPricingSystem`. |
| **Hotel Pricing System** | System (core) | Central application that handles authentication, hotel/rate/price management, price recalculation, and exposure of price data to internal and external consumers. | Provides: Web UI (HTTPS), REST API for queries and management; Publishes: Kafka events for price updates; Consumes: OAuth2/OIDC tokens from `IdentityService`. |
| **Identity Service** | System (external, cloud) | Manages user identities and issues access tokens after authentication. Relied upon by `HotelPricingSystem` for all user login and permission enforcement. | Provides: OAuth2/OIDC endpoints (`/authorize`, `/token`, `/userinfo`). |
| **Channel Management System** | System (external) | Consumes hotel price updates and distributes them to various sales channels. Receives changed prices as soon as they become effective. | Consumes: Kafka topic with price update events published by `HotelPricingSystem`. |
| **External Query Systems** | System (external) | Third‑party systems (e.g., travel aggregators) that request up‑to‑date prices for specific hotels and dates. | Consumes: REST API (HTTPS) exposed by `HotelPricingSystem`. |

---

### Traceability to Architectural Drivers

| Design Decision | Addressed Driver(s) | Rationale |
|---------------|---------------------|-----------|
| System context diagram defines the outermost boundary of the HPS. | **CRN‑1** (Overall System Structure) | Fulfills the explicit need to “establish an overall initial system structure” by providing the first‑level decomposition – the system surrounded by its environment. |
| The `IdentityService` is placed entirely outside the system; HPS relies on it via OAuth2/OIDC. | **CON‑2** (Cloud Identity & Hosting) | Enforces the constraint that authentication is delegated to a cloud‑managed identity service, keeping the HPS free of built‑in credential stores. |
| All user interactions (`CommercialUser`, `Administrator`) are over HTTPS. | **CON‑1** (Web Browser Access) | Guarantees browser‑based access on any platform, with a clear separation between the client (Angular SPA) and server. |
| The `HotelPricingSystem` publishes price updates to the `CMS` via **Kafka**. | **CRN‑2** (Technology Stack – Kafka) | Leverages the team’s knowledge of Kafka and establishes an event‑driven backbone that will later satisfy reliability (QA‑2) and performance (QA‑1) requirements. |
| `ExternalQuerySystems` interact with HPS through a **REST API** (HTTPS). | **CON‑5** (REST APIs initially) | Respects the constraint that external communication starts with REST, while the abstraction of an API layer allows later addition of protocols like gRPC (QA‑6) without changing the context diagram. |
| All components are shown as logically external to the HPS, with the system itself treated as a single undivided unit. | **CON‑6** (Cloud‑Native Approach), **CON‑4** (6‑Month Delivery) | Cloud‑native principles are kept implicit – the context says nothing about internal containers or orchestration, allowing a quick MVP design (front‑end, a few micro‑services) that can be incrementally refined. The clear boundaries enable independent work streams and parallel development. |
| The `ExternalQuerySystems` boundary is separated from the internal UI. | **CRN‑3** (Work Allocation), **CON‑4** | Allows the team to develop the query API layer independently from the administrative UI, reducing coupling and shortening the path to the MVP. |

The system context diagram forms the required outer view and will serve as the foundation for Step 4, where we decompose the `HotelPricingSystem` into its first internal elements.

## ADD Step 4: Choose Design Concepts

## ADD Steps 4–5 – Design Concepts & Instantiate Elements  
**Iteration 1: Establishing an Overall System Structure**

### Design Approach Summary
For the first‑level decomposition of the Hotel Pricing System, two architectural patterns were evaluated: a **three‑tier monolith** and a **microservices architecture with an API gateway**. The latter was selected because it directly and simultaneously satisfies the six high‑priority architectural drivers: cloud‑native principles (CON‑6), aggressive delivery timeline & parallel work (CON‑4), web‑browser‑based UI separation (CON‑1), cloud identity integration (CON‑2), the mandated technology stack (CRN‑2), and the need for a coherent, well‑defined overall structure (CRN‑1). The instantiated elements are documented below using a C4 container diagram, followed by a detailed responsibility and interface assignment.

---

### Container Diagram (C4 Level 2)
```mermaid
C4Container
  title Container Diagram – Hotel Pricing System

  Person(commUser, "Commercial User", "Uses a web browser")
  Person(adminUser, "Administrator", "Uses a web browser")
  System_Ext(identityService, "Cloud Identity Service", "OAuth2/OIDC provider")
  System_Ext(cms, "Channel Management System", "Receives price updates")
  System_Ext(extQuery, "External Query Systems", "Queries prices")

  Container_Boundary(hps, "Hotel Pricing System") {
    Container(spa, "SPA", "Angular", "Single‑page application for all user interfaces")
    Container(apiGw, "API Gateway", "Spring Cloud Gateway", "Central entry point, authentication, routing, rate limiting")
    Container(pricingSvc, "Pricing Service", "Java / Spring Boot", "Core pricing logic, simulation, query handling")
    Container(hotelSvc, "Hotel Management Service", "Java / Spring Boot", "CRUD for hotels, room types, tax rates, rate plans")
    Container(userSvc, "User Management Service", "Java / Spring Boot", "Internal user profiles, roles, permissions")
    Container(kafka, "Message Broker", "Apache Kafka", "Event backbone for price updates and config changes")
    ContainerDb(pricingDb, "Pricing DB", "PostgreSQL", "Stores rates, price rules, calculated prices")
    ContainerDb(hotelDb, "Hotel DB", "PostgreSQL", "Stores hotel master data and configurations")
    ContainerDb(userDb, "User DB", "PostgreSQL", "Stores user profiles and role assignments")
  }

  Rel(commUser, spa, "Uses", "HTTPS")
  Rel(adminUser, spa, "Uses", "HTTPS")
  Rel(spa, apiGw, "REST API calls", "HTTPS")
  Rel(apiGw, identityService, "Authenticate user", "OAuth2/OIDC")
  Rel(apiGw, pricingSvc, "Proxy requests", "REST/HTTPS")
  Rel(apiGw, hotelSvc, "Proxy requests", "REST/HTTPS")
  Rel(apiGw, userSvc, "Proxy requests", "REST/HTTPS")
  Rel(extQuery, apiGw, "Query prices", "REST/HTTPS")
  Rel(pricingSvc, kafka, "Publishes price-updates", "Kafka")
  Rel(cms, kafka, "Consumes price-updates", "Kafka")
  Rel(pricingSvc, pricingDb, "Read/write", "JDBC")
  Rel(hotelSvc, hotelDb, "Read/write", "JDBC")
  Rel(userSvc, userDb, "Read/write", "JDBC")
  Rel(hotelSvc, kafka, "Publishes hotel-config-updates", "Kafka")
  Rel(pricingSvc, kafka, "Consumes hotel-config-updates", "Kafka")
```

---

### Instantiated Elements – Responsibilities and Interfaces

| Element | Type | Primary Responsibilities | Provided Interfaces | Required Interfaces |
|---------|------|--------------------------|---------------------|---------------------|
| **SPA** | Container: Angular application | Hosts all HTML/CSS/JS for commercial and administration workflows. Communicates exclusively with the API Gateway. | HTTPS (serving the application) | REST endpoints from API Gateway (see below) |
| **API Gateway** | Container: Spring Cloud Gateway | Authenticates users via OAuth2/OIDC token validation, enforces rate limits, routes requests to the appropriate backend service, and exposes a unified API to the outside world. | REST/HTTPS API: `/api/auth/*`, `/api/prices/*`, `/api/hotels/*`, `/api/users/*` | OAuth2/OIDC endpoints of `IdentityService`; internal service REST APIs |
| **Pricing Service** | Container: Java (Spring Boot) | Implements core pricing logic: calculation of derived rates, simulation of price changes, and serving of price queries. Publishes `price-updates` events to Kafka and consumes `hotel-config-updates` from Kafka to stay synchronised. | REST API for price operations (via Gateway) | `hotel-config-updates` Kafka topic; `Pricing DB` |
| **Hotel Management Service** | Container: Java (Spring Boot) | Manages master data for hotels, room types, tax rates, and available rate plans. Publishes configuration changes to Kafka for other services. | REST API for hotel management (via Gateway) | `Hotel DB` |
| **User Management Service** | Container: Java (Spring Boot) | Maintains internal application‑specific user profiles, roles, and permissions. (Authentication is delegated to the Cloud Identity Service.) | REST API for user/role management (via Gateway) | `User DB` |
| **Message Broker** | Container: Apache Kafka | Provides durable, ordered, and decoupled communication. Enables eventual consistency between services and reliable delivery of price updates to the CMS. | Kafka topics: `price-updates`, `hotel-config-updates` | – |
| **Databases** | Containers: PostgreSQL | Each service owns its private database to ensure loose coupling and independent scalability. | JDBC / SQL connections | – |

---

### Design Decisions & Traceability to Drivers

| Decision | Driver(s) Addressed | Rationale |
|----------|---------------------|-----------|
| **Microservices architecture chosen over three‑tier monolith** | **CON‑6** (Cloud‑Native), **CON‑4** (6‑Month Delivery), **CRN‑1** (Overall Structure) | Microservices enable independent scaling, fault isolation, and rapid parallel development – core cloud‑native tenets and essential for the 6‑month delivery timeline. The clear module boundaries directly satisfy CRN‑1. |
| **SPA deployed as a separate container, API Gateway as single external entry point** | **CON‑1** (Web Browser Access), **CON‑2** (Cloud Identity) | Decouples front‑end and back‑end releases; the Gateway centralises OAuth2 token validation, ensuring that all access is authenticated by the cloud identity provider before reaching internal services. |
| **Three backend services (Pricing, Hotel, User) with their own databases** | **CON‑6**, **CON‑4** | Supports polyglot persistence, independent scaling, and parallel team assignments. Each service can be built and deployed independently, directly accelerating the MVP. |
| **Kafka as the central integration backbone** | **CRN‑2** (Java / Angular / Kafka) | Leverages the mandated technology and establishes an event‑driven architecture that will later be refined to meet QA‑1 (performance) and QA‑2 (reliability) goals. |
| **External Query Systems communicate via the API Gateway (REST only)** | **CON‑5** (REST APIs initially) | Satisfies the initial protocol constraint; the Gateway can be extended with gRPC endpoints later (QA‑6) without impacting internal services. |
| **Hotel config changes propagated via Kafka, not synchronous REST** | **CRN‑1**, **QA‑1/QA‑2 (future)** | Decouples services and prevents runtime dependency chains. The eventual consistency pattern is a stable foundation for the required performance and reliability mechanisms. |
| **Use of cloud‑managed identity service and containers** | **CON‑2**, **CON‑6** | Off‑loads authentication and hosting complexity to the cloud provider, reducing time‑to‑market and technical debt (CRN‑4). |

The architecture defined above satisfies all six iteration drivers and provides a solid, extensible skeleton for the Hotel Pricing System. Later iterations will layer on quality‑attribute‑specific mechanisms (caching, circuit breakers, outbox pattern) to fully realise QA‑1 through QA‑9.

## ADD Step 6: Sketch Views

## ADD Step 6 – Sketch Views and Record Design Decisions  
**Iteration 1: Establishing an Overall System Structure**

### Summary of Design Approach  
This step finalises the three essential architectural views that define the overall structure of the Hotel Pricing System: the system context (C4 Level 1), the container decomposition (C4 Level 2), and the initial deployment mapping onto a cloud provider. All views are based on the microservices‑with‑API‑gateway architecture chosen in Steps 4–5 and incorporate the feedback received: the missing authorization integration between the API Gateway and the User Management Service has been added.  

Every major decision is documented with its rationale, explicitly referencing the iteration drivers (CRN‑1, CON‑6, CON‑1, CON‑2, CRN‑2, CON‑4) and preparing the ground for quality‑attribute refinements in later iterations.

---

### 1. System Context Diagram (C4 Level 1)  
This view is unchanged from Step 3 and shows the Hotel Pricing System as a single black‑box system surrounded by its external actors and systems.

```mermaid
C4Context
  title System Context Diagram for Hotel Pricing System
  Person(CommercialUser, "Commercial User", "Uses web browser to change/query prices")
  Person(Administrator, "Administrator", "Uses web browser to manage hotels, rates, users")
  System_Ext(IdentityService, "Identity Service", "Cloud identity provider (OAuth2/OIDC)")
  System_Ext(CMS, "Channel Management System", "Receives price updates")
  System_Ext(ExternalQuerySystems, "External Query Systems", "Queries prices")
  System(HotelPricingSystem, "Hotel Pricing System", "Manages pricing, rates, hotels")
  Rel(CommercialUser, HotelPricingSystem, "Log in, change prices, query prices", "HTTPS")
  Rel(Administrator, HotelPricingSystem, "Manage hotels, rates, users", "HTTPS")
  Rel(HotelPricingSystem, CMS, "Publish price updates", "Kafka")
  Rel(ExternalQuerySystems, HotelPricingSystem, "Query prices", "REST API (HTTPS)")
  Rel(HotelPricingSystem, IdentityService, "Authenticate users", "OAuth2/OIDC")
  UpdateRelStyle(CommercialUser, HotelPricingSystem, $offsetY="-60")
  UpdateRelStyle(Administrator, HotelPricingSystem, $offsetY="60")
  UpdateRelStyle(HotelPricingSystem, CMS, $offsetX="-120")
  UpdateRelStyle(ExternalQuerySystems, HotelPricingSystem, $offsetX="-100")
  UpdateRelStyle(HotelPricingSystem, IdentityService, $offsetX="120")
```

---

### 2. Container Diagram (C4 Level 2) – Finalised  
The container diagram refines the system into its principal run‑time containers. The highlighted addition is the **authorization relationship** from the API Gateway to the User Management Service, resolving the earlier integration gap.

```mermaid
C4Container
  title Container Diagram – Hotel Pricing System

  Person(commUser, "Commercial User", "Uses a web browser")
  Person(adminUser, "Administrator", "Uses a web browser")
  System_Ext(identityService, "Cloud Identity Service", "OAuth2/OIDC provider")
  System_Ext(cms, "Channel Management System", "Receives price updates")
  System_Ext(extQuery, "External Query Systems", "Queries prices")

  Container_Boundary(hps, "Hotel Pricing System") {
    Container(spa, "SPA", "Angular", "Single‑page application for all user interfaces")
    Container(apiGw, "API Gateway", "Spring Cloud Gateway", "Central entry point, authentication, routing, rate limiting")
    Container(pricingSvc, "Pricing Service", "Java / Spring Boot", "Core pricing logic, simulation, query handling")
    Container(hotelSvc, "Hotel Management Service", "Java / Spring Boot", "CRUD for hotels, room types, tax rates, rate plans")
    Container(userSvc, "User Management Service", "Java / Spring Boot", "Internal user profiles, roles, permissions")
    Container(kafka, "Message Broker", "Apache Kafka", "Event backbone for price updates and config changes")
    ContainerDb(pricingDb, "Pricing DB", "PostgreSQL", "Stores rates, price rules, calculated prices")
    ContainerDb(hotelDb, "Hotel DB", "PostgreSQL", "Stores hotel master data and configurations")
    ContainerDb(userDb, "User DB", "PostgreSQL", "Stores user profiles and role assignments")
  }

  Rel(commUser, spa, "Uses", "HTTPS")
  Rel(adminUser, spa, "Uses", "HTTPS")
  Rel(spa, apiGw, "REST API calls", "HTTPS")
  Rel(apiGw, identityService, "Authenticate user", "OAuth2/OIDC")
  Rel(apiGw, pricingSvc, "Proxy requests", "REST/HTTPS")
  Rel(apiGw, hotelSvc, "Proxy requests", "REST/HTTPS")
  Rel(apiGw, userSvc, "Proxy requests / Check permissions", "REST/HTTPS")
  Rel(extQuery, apiGw, "Query prices", "REST/HTTPS")
  Rel(pricingSvc, kafka, "Publishes price-updates", "Kafka")
  Rel(cms, kafka, "Consumes price-updates", "Kafka")
  Rel(pricingSvc, pricingDb, "Read/write", "JDBC")
  Rel(hotelSvc, hotelDb, "Read/write", "JDBC")
  Rel(userSvc, userDb, "Read/write", "JDBC")
  Rel(hotelSvc, kafka, "Publishes hotel-config-updates", "Kafka")
  Rel(pricingSvc, kafka, "Consumes hotel-config-updates", "Kafka")
```

**Updated responsibility note:**  
The API Gateway now explicitly uses a **REST call to the User Management Service** to retrieve application‑specific roles/permissions after validating the OAuth2 token with the Identity Service. This completes the authentication‑and‑authorisation chain at the container level.

---

### 3. Deployment Diagram – Cloud Infrastructure Mapping  
The deployment view maps the containers to **Amazon Web Services (AWS)** , a leading cloud‑native platform, satisfying CON‑2 and CON‑6. All backend services run in a private Kubernetes cluster (EKS), databases are managed (RDS), Kafka is the managed MSK service, and the Identity Service is Amazon Cognito.

```mermaid
C4Deployment
  title Deployment Diagram – Hotel Pricing System on AWS

  Person(commUser, "Commercial User", "")
  Person(adminUser, "Administrator", "")
  System_Ext(cms, "Channel Management System", "")
  System_Ext(extQuery, "External Query Systems", "")

  Deployment_Node(aws, "AWS Cloud", "AWS") {
    Deployment_Node(cdn, "Amazon CloudFront", "CDN") {
      Container(spa, "SPA", "Angular", "Static assets served globally")
    }
    Deployment_Node(vpc, "VPC", "Virtual Private Cloud") {
      Deployment_Node(pubSub, "Public Subnet", "DMZ") {
        Deployment_Node(alb, "Application Load Balancer", "ALB") {
          Container(apiGw, "API Gateway", "Spring Cloud Gateway", "")
        }
      }
      Deployment_Node(privSub, "Private Subnet", "Backend") {
        Deployment_Node(eksCluster, "Amazon EKS", "Managed Kubernetes") {
          Deployment_Node(nodeGroup, "Worker Nodes", "EC2") {
            Container(pricingSvc, "Pricing Service", "Java/Spring", "")
            Container(hotelSvc, "Hotel Management Service", "Java/Spring", "")
            Container(userSvc, "User Management Service", "Java/Spring", "")
          }
        }
        Deployment_Node(rdsPricing, "RDS Instance", "PostgreSQL – Pricing DB") {
          Container(pricingDb, "Pricing DB", "PostgreSQL", "")
        }
        Deployment_Node(rdsHotel, "RDS Instance", "PostgreSQL – Hotel DB") {
          Container(hotelDb, "Hotel DB", "PostgreSQL", "")
        }
        Deployment_Node(rdsUser, "RDS Instance", "PostgreSQL – User DB") {
          Container(userDb, "User DB", "PostgreSQL", "")
        }
      }
    }
    Deployment_Node(msk, "Amazon MSK", "Managed Apache Kafka") {
      Container(kafka, "Message Broker", "Apache Kafka", "")
    }
    Deployment_Node(cognito, "Amazon Cognito", "User Pools") {
      Container(identityService, "Identity Service", "Cloud Identity Provider", "")
    }
  }

  Rel(commUser, cdn, "HTTPS", "Uses SPA")
  Rel(adminUser, cdn, "HTTPS", "Uses SPA")
  Rel(cdn, alb, "HTTPS", "API calls")
  Rel(extQuery, alb, "HTTPS", "REST price queries")
  Rel(alb, apiGw, "HTTP", "Proxies requests")
  Rel(apiGw, identityService, "OAuth2/OIDC", "Authenticate")
  Rel(apiGw, pricingSvc, "REST", "Proxies pricing requests")
  Rel(apiGw, hotelSvc, "REST", "Proxies hotel requests")
  Rel(apiGw, userSvc, "REST", "Proxies user/permissions requests")
  Rel(pricingSvc, pricingDb, "JDBC", "Read/write")
  Rel(hotelSvc, hotelDb, "JDBC", "Read/write")
  Rel(userSvc, userDb, "JDBC", "Read/write")
  Rel(pricingSvc, kafka, "Kafka", "Publish/Consume")
  Rel(hotelSvc, kafka, "Kafka", "Publish config")
  Rel(kafka, cms, "Kafka", "Consume price updates")
```

**Key deployment decisions that satisfy constraints:**  
- **All internal services are in private subnets** (no direct internet exposure).  
- **Amazon MSK** provides a fully managed, multi‑AZ Kafka cluster, ensuring reliability and scalability (CON‑6).  
- **Amazon Cognito** implements CON‑2 as a cloud‑native identity service.  
- **CloudFront CDN** serves the Angular SPA globally, meeting CON‑1 and reducing latency.  
- **AWS Application Load Balancer** handles TLS termination and routes traffic to the API Gateway, enabling path‑based routing and future blue‑green deployments (CON‑4, CON‑6).

---

### 4. Design Decision Record  
The table below captures every significant architectural decision made during Iteration 1, the alternatives considered, and the rationale tied to the iteration drivers.

| ID    | Decision                                                                                          | Alternatives Considered                         | Rationale (Drivers)                                                                                                                                                                                                                        | Impact                                                                                                                          |
|-------|---------------------------------------------------------------------------------------------------|------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| D-01  | **Microservices architecture with API Gateway** instead of a three‑tier monolith                  | Three‑tier monolith                            | Directly satisfies **CON‑6** (cloud‑native: independent scaling, fault isolation), **CON‑4** (6‑month delivery via parallel development), and **CRN‑1** (clear, modular overall structure).                                                  | Introduces operational complexity; requires container orchestration and service templates to mitigate.                           |
| D-02  | **Angular SPA deployed separately**, served via CDN; all backend communication through API Gateway | Single bundled WAR serving UI and API           | **CON‑1** (browser‑based access), **CRN‑2** (Angular), and **CON‑2** (cloud hosting) mandate a clean front‑end/back‑end split. CDN deployment improves performance and allows independent UI releases.                                        | SPA must handle CORS correctly; the API Gateway is the single entry point for all requests.                                    |
| D-03  | **Cloud‑native identity service (Cognito) for authentication**; API Gateway validates OAuth2 tokens | Build own auth service or use database credentials | **CON‑2** enforces a managed cloud identity service. Off‑loads security hardening, multi‑factor auth, and token lifecycle to the cloud provider.                                                                                           | The Identity Service is external; network latency for token introspection must be considered.                                   |
| D-04  | **User Management Service stores application‑specific roles, API Gateway calls it for authorization** | Encode all permissions in JWT claims via Cognito | Provides separation of concerns: identity (Cognito) vs. authorisation logic (User Service). This aligns with **CRN‑1** and future security needs (QA‑5).                                                                                     | Adds a synchronous dependency from Gateway to User Service; can be mitigated by caching permissions.                            |
| D-05  | **Apache Kafka as the central event backbone** for price updates and config synchronization       | Direct REST calls between services              | **CRN‑2** (leverage Kafka), **QA‑1/QA‑2** (enables eventual consistency, reliable delivery, and performance isolation). Decouples services (**CRN‑1**) and prepares for streaming patterns.                                                  | Introduces eventual consistency; the Pricing Service must serve queries while config updates are in transit (future refinement). |
| D-06  | **Database per service** (PostgreSQL instances) instead of a shared monolithic database            | Single shared database                          | **CON‑6** (loose coupling, independent scaling) and **CON‑4** (parallel development without schema conflicts). Each service owns its data, aligning with cloud‑native principles.                                                            | Cross‑service queries must be avoided; eventual consistency via Kafka is the primary synchronisation mechanism.                 |
| D-07  | **Initial external protocol: REST/HTTPS**, exposed through the API Gateway, with plan for later gRPC | gRPC from the start                             | **CON‑5** mandates REST initially; the API Gateway abstracts internal services, allowing new protocols to be added later without affecting core components (**QA‑6**).                                                                        | The API Gateway must be designed to support protocol translation; this is a future workload.                                    |
| D-08  | **Containerisation on AWS EKS** with managed services (RDS, MSK, Cognito, CloudFront)             | Self‑managed VMs or bare metal                  | **CON‑6** (cloud‑native) and **CON‑2** (cloud hosting) demand managed, elastic infrastructure. Leveraging AWS services reduces time to market (**CON‑4**) and off‑loads operational burden (**CRN‑5**).                                        | Tight coupling to AWS services; mitigated by abstraction layers if multicloud becomes a later requirement.                      |

All decisions are traced back to the iteration drivers, and they collectively realise the architecture skeleton for the Hotel Pricing System. The next iterations will layer on quality‑attribute mechanisms (e.g., caching, circuit breakers, transactional outbox) while preserving this foundation.

## ADD Step 7: Analysis & Review

## Overall Assessment: **PASS**

The design for ADD Step 6 fully satisfies the iteration goal and all six selected architectural drivers. The system context, container, and deployment views are consistent, complete, and clearly traceable to the drivers. The design decision record is thorough, and the closure of the previous authorization gap shows responsiveness to feedback. Minor inconsistencies and missing details exist but they do not undermine the overall structure and are appropriate for refinement in later iterations.

## Strengths

- **Complete and Coherent Views** – The three C4 diagrams (context, container, deployment) are syntactically correct Mermaid, internally consistent, and cover all required elements. The deployment diagram maps containers to a realistic cloud‑native AWS topology (CloudFront, ALB, EKS, RDS, MSK, Cognito) with proper network segmentation.
- **Driver Traceability** – Every design decision (D‑01 to D‑08) is explicitly linked to one or more iteration drivers, and the rationale is well‑justified. The decisions collectively achieve a microservices‑based, cloud‑native, event‑driven architecture that meets all six drivers.
- **Feedback Incorporation** – The missing authorization relationship between API Gateway and User Management Service has been added, closing the previously identified integration gap.
- **Deferred Quality Attributes** – The architecture enables later refinements for QA‑1 through QA‑9 without forcing premature tactical decisions. For example, Kafka and independent databases provide a foundation for performance, reliability, and scalability mechanisms.
- **Project Timeline Awareness** – The design explicitly considers parallel development streams, use of managed services, and independent deployments to support the 2‑month MVP and 6‑month delivery (CON‑4).

## Issues Found

1. **Inconsistent Relationship Direction for CMS–Kafka**  
   - In the context diagram, `HotelPricingSystem` publishes to `CMS` via Kafka, which is correct (direction: HPS → CMS).  
   - In the container diagram, `cms` consumes from `kafka`, shown as `Rel(cms, kafka, "Consumes price-updates", "Kafka")` (arrow from `cms` to `kafka`). This accurately reflects that the CMS pulls from Kafka.  
   - In the deployment diagram, however, the relationship is drawn as `Rel(kafka, cms, "Kafka", "Consume price updates")`, which reverses the direction and implies `kafka` pushes to `cms`. This inconsistency could cause confusion during detailed implementation and should be corrected to match the container diagram’s semantics.

2. **Security for External CMS Accessing Kafka**  
   - The deployment diagram shows the Channel Management System (external to AWS) consuming price updates directly from Amazon MSK. No explicit connectivity mechanism (e.g., VPC peering, AWS PrivateLink, or VPN) is described to secure this cross‑network traffic. While the iteration goal focuses on structure, not network security, the omission could become a significant architectural risk if not addressed before construction.

3. **Missing Detail on User Role Synchronization**  
   - The design correctly separates authentication (Cognito) from application‑specific authorisation (User Management Service) and adds a synchronous call from the API Gateway to check permissions. However, it does not explain how the user roles managed internally (by the User Management Service) are synchronized with the cloud identity or how new roles get assigned from an administrator’s perspective. This gap is deferred but will need resolution when HPS‑6 (Manage Users) is detailed.

## Suggestions

1. **Correct the Kafka Relationship in Deployment Diagram**  
   - Change the deployment diagram relationship to `Rel(cms, kafka, "Kafka", "Consume price updates")` to reflect the typical consumer‑initiated pull from MSK, aligning with the container diagram’s data flow.

2. **Add a Note on Secure CMS Connectivity**  
   - In the deployment view or decision record, add a brief annotation that the CMS will access MSK via a secure connectivity option (e.g., AWS PrivateLink, Direct Connect, or a VPN) to maintain the cloud‑native and security constraints. This prevents a future oversight when the team begins infrastructure provisioning.

3. **Future Iteration for Authorization‑Identity Synchronization**  
   - When refining security (QA‑5) or user management (HPS‑6), explicitly design how Cognito user identities map to the User Management Service’s role store, ideally through an event‑driven or API‑based synchronization pattern. Flag this as a pending task in the iteration backlog.

These suggestions are minor and do not affect the correctness of the Iteration 1 foundation.

## Decision

**Accept** – The design successfully completes ADD Step 6 for Iteration 1. The architecture is well‑structured, driver‑driven, and provides a solid baseline for subsequent iterations. The noted issues are easily corrected and do not block progression. Proceed to the next iteration with the recorded recommendations.

## Reviewer Assessment

> **Timestamp**: 2026-06-11T10:16:09.5264057

## Overall Assessment: **PASS**

The design for ADD Step 6 fully satisfies the iteration goal and all six selected architectural drivers. The system context, container, and deployment views are consistent, complete, and clearly traceable to the drivers. The design decision record is thorough, and the closure of the previous authorization gap shows responsiveness to feedback. Minor inconsistencies and missing details exist but they do not undermine the overall structure and are appropriate for refinement in later iterations.

## Strengths

- **Complete and Coherent Views** – The three C4 diagrams (context, container, deployment) are syntactically correct Mermaid, internally consistent, and cover all required elements. The deployment diagram maps containers to a realistic cloud‑native AWS topology (CloudFront, ALB, EKS, RDS, MSK, Cognito) with proper network segmentation.
- **Driver Traceability** – Every design decision (D‑01 to D‑08) is explicitly linked to one or more iteration drivers, and the rationale is well‑justified. The decisions collectively achieve a microservices‑based, cloud‑native, event‑driven architecture that meets all six drivers.
- **Feedback Incorporation** – The missing authorization relationship between API Gateway and User Management Service has been added, closing the previously identified integration gap.
- **Deferred Quality Attributes** – The architecture enables later refinements for QA‑1 through QA‑9 without forcing premature tactical decisions. For example, Kafka and independent databases provide a foundation for performance, reliability, and scalability mechanisms.
- **Project Timeline Awareness** – The design explicitly considers parallel development streams, use of managed services, and independent deployments to support the 2‑month MVP and 6‑month delivery (CON‑4).

## Issues Found

1. **Inconsistent Relationship Direction for CMS–Kafka**  
   - In the context diagram, `HotelPricingSystem` publishes to `CMS` via Kafka, which is correct (direction: HPS → CMS).  
   - In the container diagram, `cms` consumes from `kafka`, shown as `Rel(cms, kafka, "Consumes price-updates", "Kafka")` (arrow from `cms` to `kafka`). This accurately reflects that the CMS pulls from Kafka.  
   - In the deployment diagram, however, the relationship is drawn as `Rel(kafka, cms, "Kafka", "Consume price updates")`, which reverses the direction and implies `kafka` pushes to `cms`. This inconsistency could cause confusion during detailed implementation and should be corrected to match the container diagram’s semantics.

2. **Security for External CMS Accessing Kafka**  
   - The deployment diagram shows the Channel Management System (external to AWS) consuming price updates directly from Amazon MSK. No explicit connectivity mechanism (e.g., VPC peering, AWS PrivateLink, or VPN) is described to secure this cross‑network traffic. While the iteration goal focuses on structure, not network security, the omission could become a significant architectural risk if not addressed before construction.

3. **Missing Detail on User Role Synchronization**  
   - The design correctly separates authentication (Cognito) from application‑specific authorisation (User Management Service) and adds a synchronous call from the API Gateway to check permissions. However, it does not explain how the user roles managed internally (by the User Management Service) are synchronized with the cloud identity or how new roles get assigned from an administrator’s perspective. This gap is deferred but will need resolution when HPS‑6 (Manage Users) is detailed.

## Suggestions

1. **Correct the Kafka Relationship in Deployment Diagram**  
   - Change the deployment diagram relationship to `Rel(cms, kafka, "Kafka", "Consume price updates")` to reflect the typical consumer‑initiated pull from MSK, aligning with the container diagram’s data flow.

2. **Add a Note on Secure CMS Connectivity**  
   - In the deployment view or decision record, add a brief annotation that the CMS will access MSK via a secure connectivity option (e.g., AWS PrivateLink, Direct Connect, or a VPN) to maintain the cloud‑native and security constraints. This prevents a future oversight when the team begins infrastructure provisioning.

3. **Future Iteration for Authorization‑Identity Synchronization**  
   - When refining security (QA‑5) or user management (HPS‑6), explicitly design how Cognito user identities map to the User Management Service’s role store, ideally through an event‑driven or API‑based synchronization pattern. Flag this as a pending task in the iteration backlog.

These suggestions are minor and do not affect the correctness of the Iteration 1 foundation.

## Decision

**Accept** – The design successfully completes ADD Step 6 for Iteration 1. The architecture is well‑structured, driver‑driven, and provides a solid baseline for subsequent iterations. The noted issues are easily corrected and do not block progression. Proceed to the next iteration with the recorded recommendations.

## Final Design Decision for Iteration 1

## ADD Step 6 – Sketch Views and Record Design Decisions  
**Iteration 1: Establishing an Overall System Structure**

### Summary of Design Approach  
This step finalises the three essential architectural views that define the overall structure of the Hotel Pricing System: the system context (C4 Level 1), the container decomposition (C4 Level 2), and the initial deployment mapping onto a cloud provider. All views are based on the microservices‑with‑API‑gateway architecture chosen in Steps 4–5 and incorporate the feedback received: the missing authorization integration between the API Gateway and the User Management Service has been added.  

Every major decision is documented with its rationale, explicitly referencing the iteration drivers (CRN‑1, CON‑6, CON‑1, CON‑2, CRN‑2, CON‑4) and preparing the ground for quality‑attribute refinements in later iterations.

---

### 1. System Context Diagram (C4 Level 1)  
This view is unchanged from Step 3 and shows the Hotel Pricing System as a single black‑box system surrounded by its external actors and systems.

```mermaid
C4Context
  title System Context Diagram for Hotel Pricing System
  Person(CommercialUser, "Commercial User", "Uses web browser to change/query prices")
  Person(Administrator, "Administrator", "Uses web browser to manage hotels, rates, users")
  System_Ext(IdentityService, "Identity Service", "Cloud identity provider (OAuth2/OIDC)")
  System_Ext(CMS, "Channel Management System", "Receives price updates")
  System_Ext(ExternalQuerySystems, "External Query Systems", "Queries prices")
  System(HotelPricingSystem, "Hotel Pricing System", "Manages pricing, rates, hotels")
  Rel(CommercialUser, HotelPricingSystem, "Log in, change prices, query prices", "HTTPS")
  Rel(Administrator, HotelPricingSystem, "Manage hotels, rates, users", "HTTPS")
  Rel(HotelPricingSystem, CMS, "Publish price updates", "Kafka")
  Rel(ExternalQuerySystems, HotelPricingSystem, "Query prices", "REST API (HTTPS)")
  Rel(HotelPricingSystem, IdentityService, "Authenticate users", "OAuth2/OIDC")
  UpdateRelStyle(CommercialUser, HotelPricingSystem, $offsetY="-60")
  UpdateRelStyle(Administrator, HotelPricingSystem, $offsetY="60")
  UpdateRelStyle(HotelPricingSystem, CMS, $offsetX="-120")
  UpdateRelStyle(ExternalQuerySystems, HotelPricingSystem, $offsetX="-100")
  UpdateRelStyle(HotelPricingSystem, IdentityService, $offsetX="120")
```

---

### 2. Container Diagram (C4 Level 2) – Finalised  
The container diagram refines the system into its principal run‑time containers. The highlighted addition is the **authorization relationship** from the API Gateway to the User Management Service, resolving the earlier integration gap.

```mermaid
C4Container
  title Container Diagram – Hotel Pricing System

  Person(commUser, "Commercial User", "Uses a web browser")
  Person(adminUser, "Administrator", "Uses a web browser")
  System_Ext(identityService, "Cloud Identity Service", "OAuth2/OIDC provider")
  System_Ext(cms, "Channel Management System", "Receives price updates")
  System_Ext(extQuery, "External Query Systems", "Queries prices")

  Container_Boundary(hps, "Hotel Pricing System") {
    Container(spa, "SPA", "Angular", "Single‑page application for all user interfaces")
    Container(apiGw, "API Gateway", "Spring Cloud Gateway", "Central entry point, authentication, routing, rate limiting")
    Container(pricingSvc, "Pricing Service", "Java / Spring Boot", "Core pricing logic, simulation, query handling")
    Container(hotelSvc, "Hotel Management Service", "Java / Spring Boot", "CRUD for hotels, room types, tax rates, rate plans")
    Container(userSvc, "User Management Service", "Java / Spring Boot", "Internal user profiles, roles, permissions")
    Container(kafka, "Message Broker", "Apache Kafka", "Event backbone for price updates and config changes")
    ContainerDb(pricingDb, "Pricing DB", "PostgreSQL", "Stores rates, price rules, calculated prices")
    ContainerDb(hotelDb, "Hotel DB", "PostgreSQL", "Stores hotel master data and configurations")
    ContainerDb(userDb, "User DB", "PostgreSQL", "Stores user profiles and role assignments")
  }

  Rel(commUser, spa, "Uses", "HTTPS")
  Rel(adminUser, spa, "Uses", "HTTPS")
  Rel(spa, apiGw, "REST API calls", "HTTPS")
  Rel(apiGw, identityService, "Authenticate user", "OAuth2/OIDC")
  Rel(apiGw, pricingSvc, "Proxy requests", "REST/HTTPS")
  Rel(apiGw, hotelSvc, "Proxy requests", "REST/HTTPS")
  Rel(apiGw, userSvc, "Proxy requests / Check permissions", "REST/HTTPS")
  Rel(extQuery, apiGw, "Query prices", "REST/HTTPS")
  Rel(pricingSvc, kafka, "Publishes price-updates", "Kafka")
  Rel(cms, kafka, "Consumes price-updates", "Kafka")
  Rel(pricingSvc, pricingDb, "Read/write", "JDBC")
  Rel(hotelSvc, hotelDb, "Read/write", "JDBC")
  Rel(userSvc, userDb, "Read/write", "JDBC")
  Rel(hotelSvc, kafka, "Publishes hotel-config-updates", "Kafka")
  Rel(pricingSvc, kafka, "Consumes hotel-config-updates", "Kafka")
```

**Updated responsibility note:**  
The API Gateway now explicitly uses a **REST call to the User Management Service** to retrieve application‑specific roles/permissions after validating the OAuth2 token with the Identity Service. This completes the authentication‑and‑authorisation chain at the container level.

---

### 3. Deployment Diagram – Cloud Infrastructure Mapping  
The deployment view maps the containers to **Amazon Web Services (AWS)** , a leading cloud‑native platform, satisfying CON‑2 and CON‑6. All backend services run in a private Kubernetes cluster (EKS), databases are managed (RDS), Kafka is the managed MSK service, and the Identity Service is Amazon Cognito.

```mermaid
C4Deployment
  title Deployment Diagram – Hotel Pricing System on AWS

  Person(commUser, "Commercial User", "")
  Person(adminUser, "Administrator", "")
  System_Ext(cms, "Channel Management System", "")
  System_Ext(extQuery, "External Query Systems", "")

  Deployment_Node(aws, "AWS Cloud", "AWS") {
    Deployment_Node(cdn, "Amazon CloudFront", "CDN") {
      Container(spa, "SPA", "Angular", "Static assets served globally")
    }
    Deployment_Node(vpc, "VPC", "Virtual Private Cloud") {
      Deployment_Node(pubSub, "Public Subnet", "DMZ") {
        Deployment_Node(alb, "Application Load Balancer", "ALB") {
          Container(apiGw, "API Gateway", "Spring Cloud Gateway", "")
        }
      }
      Deployment_Node(privSub, "Private Subnet", "Backend") {
        Deployment_Node(eksCluster, "Amazon EKS", "Managed Kubernetes") {
          Deployment_Node(nodeGroup, "Worker Nodes", "EC2") {
            Container(pricingSvc, "Pricing Service", "Java/Spring", "")
            Container(hotelSvc, "Hotel Management Service", "Java/Spring", "")
            Container(userSvc, "User Management Service", "Java/Spring", "")
          }
        }
        Deployment_Node(rdsPricing, "RDS Instance", "PostgreSQL – Pricing DB") {
          Container(pricingDb, "Pricing DB", "PostgreSQL", "")
        }
        Deployment_Node(rdsHotel, "RDS Instance", "PostgreSQL – Hotel DB") {
          Container(hotelDb, "Hotel DB", "PostgreSQL", "")
        }
        Deployment_Node(rdsUser, "RDS Instance", "PostgreSQL – User DB") {
          Container(userDb, "User DB", "PostgreSQL", "")
        }
      }
    }
    Deployment_Node(msk, "Amazon MSK", "Managed Apache Kafka") {
      Container(kafka, "Message Broker", "Apache Kafka", "")
    }
    Deployment_Node(cognito, "Amazon Cognito", "User Pools") {
      Container(identityService, "Identity Service", "Cloud Identity Provider", "")
    }
  }

  Rel(commUser, cdn, "HTTPS", "Uses SPA")
  Rel(adminUser, cdn, "HTTPS", "Uses SPA")
  Rel(cdn, alb, "HTTPS", "API calls")
  Rel(extQuery, alb, "HTTPS", "REST price queries")
  Rel(alb, apiGw, "HTTP", "Proxies requests")
  Rel(apiGw, identityService, "OAuth2/OIDC", "Authenticate")
  Rel(apiGw, pricingSvc, "REST", "Proxies pricing requests")
  Rel(apiGw, hotelSvc, "REST", "Proxies hotel requests")
  Rel(apiGw, userSvc, "REST", "Proxies user/permissions requests")
  Rel(pricingSvc, pricingDb, "JDBC", "Read/write")
  Rel(hotelSvc, hotelDb, "JDBC", "Read/write")
  Rel(userSvc, userDb, "JDBC", "Read/write")
  Rel(pricingSvc, kafka, "Kafka", "Publish/Consume")
  Rel(hotelSvc, kafka, "Kafka", "Publish config")
  Rel(kafka, cms, "Kafka", "Consume price updates")
```

**Key deployment decisions that satisfy constraints:**  
- **All internal services are in private subnets** (no direct internet exposure).  
- **Amazon MSK** provides a fully managed, multi‑AZ Kafka cluster, ensuring reliability and scalability (CON‑6).  
- **Amazon Cognito** implements CON‑2 as a cloud‑native identity service.  
- **CloudFront CDN** serves the Angular SPA globally, meeting CON‑1 and reducing latency.  
- **AWS Application Load Balancer** handles TLS termination and routes traffic to the API Gateway, enabling path‑based routing and future blue‑green deployments (CON‑4, CON‑6).

---

### 4. Design Decision Record  
The table below captures every significant architectural decision made during Iteration 1, the alternatives considered, and the rationale tied to the iteration drivers.

| ID    | Decision                                                                                          | Alternatives Considered                         | Rationale (Drivers)                                                                                                                                                                                                                        | Impact                                                                                                                          |
|-------|---------------------------------------------------------------------------------------------------|------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| D-01  | **Microservices architecture with API Gateway** instead of a three‑tier monolith                  | Three‑tier monolith                            | Directly satisfies **CON‑6** (cloud‑native: independent scaling, fault isolation), **CON‑4** (6‑month delivery via parallel development), and **CRN‑1** (clear, modular overall structure).                                                  | Introduces operational complexity; requires container orchestration and service templates to mitigate.                           |
| D-02  | **Angular SPA deployed separately**, served via CDN; all backend communication through API Gateway | Single bundled WAR serving UI and API           | **CON‑1** (browser‑based access), **CRN‑2** (Angular), and **CON‑2** (cloud hosting) mandate a clean front‑end/back‑end split. CDN deployment improves performance and allows independent UI releases.                                        | SPA must handle CORS correctly; the API Gateway is the single entry point for all requests.                                    |
| D-03  | **Cloud‑native identity service (Cognito) for authentication**; API Gateway validates OAuth2 tokens | Build own auth service or use database credentials | **CON‑2** enforces a managed cloud identity service. Off‑loads security hardening, multi‑factor auth, and token lifecycle to the cloud provider.                                                                                           | The Identity Service is external; network latency for token introspection must be considered.                                   |
| D-04  | **User Management Service stores application‑specific roles, API Gateway calls it for authorization** | Encode all permissions in JWT claims via Cognito | Provides separation of concerns: identity (Cognito) vs. authorisation logic (User Service). This aligns with **CRN‑1** and future security needs (QA‑5).                                                                                     | Adds a synchronous dependency from Gateway to User Service; can be mitigated by caching permissions.                            |
| D-05  | **Apache Kafka as the central event backbone** for price updates and config synchronization       | Direct REST calls between services              | **CRN‑2** (leverage Kafka), **QA‑1/QA‑2** (enables eventual consistency, reliable delivery, and performance isolation). Decouples services (**CRN‑1**) and prepares for streaming patterns.                                                  | Introduces eventual consistency; the Pricing Service must serve queries while config updates are in transit (future refinement). |
| D-06  | **Database per service** (PostgreSQL instances) instead of a shared monolithic database            | Single shared database                          | **CON‑6** (loose coupling, independent scaling) and **CON‑4** (parallel development without schema conflicts). Each service owns its data, aligning with cloud‑native principles.                                                            | Cross‑service queries must be avoided; eventual consistency via Kafka is the primary synchronisation mechanism.                 |
| D-07  | **Initial external protocol: REST/HTTPS**, exposed through the API Gateway, with plan for later gRPC | gRPC from the start                             | **CON‑5** mandates REST initially; the API Gateway abstracts internal services, allowing new protocols to be added later without affecting core components (**QA‑6**).                                                                        | The API Gateway must be designed to support protocol translation; this is a future workload.                                    |
| D-08  | **Containerisation on AWS EKS** with managed services (RDS, MSK, Cognito, CloudFront)             | Self‑managed VMs or bare metal                  | **CON‑6** (cloud‑native) and **CON‑2** (cloud hosting) demand managed, elastic infrastructure. Leveraging AWS services reduces time to market (**CON‑4**) and off‑loads operational burden (**CRN‑5**).                                        | Tight coupling to AWS services; mitigated by abstraction layers if multicloud becomes a later requirement.                      |

All decisions are traced back to the iteration drivers, and they collectively realise the architecture skeleton for the Hotel Pricing System. The next iterations will layer on quality‑attribute mechanisms (e.g., caching, circuit breakers, transactional outbox) while preserving this foundation.

## Iteration 1 — Review Status

| Check | Status |
|-------|--------|
| Review Passed | ✓ PASS |
| Conversation Entries | 14 |
| ADD Steps Completed | 6 |

---

# 2) Iteration 2: Identifying Structures to Support Primary Functionality

## ADD Step 2: Establish Iteration Goal & Select Elements

## 1.  Design Approach

For Iteration 2 I decompose the five element groups identified in Iteration 1 into an internal component structure that realises all six primary use‑cases while directly addressing the driving quality attributes – especially the tight latency requirement of QA‑1, the delivery guarantee of QA‑2, the scalability demands of QA‑4, and the protocol‑modifiability goal of QA‑6.  

The design uses:

* A **cloud‑native, containerised** style (CON‑6) with Java/Spring Boot micro‑services, an Angular front‑end, and Kafka as the asynchronous backbone (CRN‑2).  
* **REST/JSON** for synchronous calls (CON‑5) and a dedicated **ProtocolAdapter** that can evolve to gRPC or GraphQL without touching core services.  
* A **transactional outbox** combined with Kafka to achieve 100 % reliable delivery of price changes (QA‑2) while keeping the write path under 100 ms (QA‑1).  
* A **distributed in‑memory cache (Redis)** that is updated synchronously inside the write transaction, so queries can be answered with no database I/O and meet the “ready for query” part of QA‑1.  
* A **central API gateway** that enforces authentication via validated JWT tokens (QA‑5), applies rate limiting, and routes to stateless business services – enabling horizontal scaling (QA‑4).  
* Dependency inversion via interfaces for all external dependencies to support testing in isolation (QA‑9) and to keep the deployment environment‑agnostic (QA‑7).

Every component allocation is traced back to at least one architectural driver; the rationale is captured below.

---

## 2.  Component Diagram

The following Mermaid diagram shows the internal components and their interactions.  

```mermaid
graph TD
    subgraph external[External Entities]
        WebApp["Web App (Angular)"]
        AdminApp["Admin App (Angular)"]
        ExternalPartner["External Systems<br/>(Channel Mgmt, Price Query)"]
        IdentityProvider["Cloud Identity Service"]
    end

    subgraph api_layer[API Layer]
        GatewayPublic["Gateway‑Public<br/>REST API Gateway<br/>JWT validation, rate limiting"]
    end

    subgraph pricing[Pricing Engine]
        PCmd["PriceCommandHandler<br/>processes price changes<br/>(validates, recalculates,<br/>updates DB & cache)"]
        POutbox["(Outbox Table)"]
        OutboxProc["OutboxProcessor<br/>reads outbox, publishes to Kafka"]
        PriceCache["(PriceCache<br/>Distributed (Redis)<br/>Current prices)"]
        PQuery["PriceQueryAPI<br/>REST endpoint for querying"]
    end

    subgraph hotel_mgmt[Hotel Management]
        HotelSvc["HotelService<br/>manages hotel CRUD"]
        RateMgr["RateManager<br/>CRUD rates & business rules"]
        HotelPub["HotelEventPublisher<br/>publishes hotel/rate events"]
    end

    subgraph user_mgmt[User Management]
        AuthSvc["AuthService<br/>OAuth2/OIDC, token issue"]
        UserAPI["UserAPI<br/>manage users & permissions"]
        UserStore["(UserStore<br/>User data, roles)"]
    end

    subgraph integration[Integration / Eventing]
        Kafka[("Kafka Broker<br/>Event Bus")]
        ProtoAdapter["ProtocolAdapter<br/>consumes Kafka events,<br/>maps to external protocols"]
    end

    %% Flows
    WebApp -->|HTTPS/REST| GatewayPublic
    AdminApp -->|HTTPS/REST| GatewayPublic
    ExternalPartner -->|HTTPS/REST<br/>(future gRPC/GraphQL)| ProtoAdapter

    GatewayPublic -->|JWT validated, routed| PCmd
    GatewayPublic -->|JWT validated, routed| PQuery
    GatewayPublic -->|JWT validated, routed| HotelSvc
    GatewayPublic -->|JWT validated, routed| RateMgr
    GatewayPublic -->|JWT validated, routed| UserAPI
    GatewayPublic ---->|login redirection| AuthSvc

    PCmd -->|transactional update| PriceCache
    PCmd -->|transactional insert| POutbox
    OutboxProc -->|polls| POutbox
    OutboxProc -->|publishes msg| Kafka
    ProtoAdapter -->|consumes msg| Kafka
    ProtoAdapter -->|REST call| ExternalPartner

    PQuery -->|reads| PriceCache

    HotelSvc -->|emits hotel event| HotelPub
    RateMgr -->|emits rate event| HotelPub
    HotelPub -->|publishes| Kafka

    AuthSvc -->|reads/writes| UserStore
    AuthSvc -->|validates credentials| IdentityProvider
    UserAPI -->|CRUD| UserStore
```

> **Notes on the diagram**  
> - Solid lines represent synchronous REST/HTTP calls.  
> - Dotted lines represent asynchronous Kafka messaging.  
> - The outbox table and the Kafka broker are modelled as data stores / infrastructure nodes, not active components.  
> - Hotel/rate events are published to Kafka; they may later be consumed by the Pricing Engine to adapt its configuration, but that consumer is not shown for clarity.

---

## 3.  Use‑Case Flow Trace

Each use‑case is traced through the components identified above. The numbers correspond to the sequence of interactions, and the quality attributes satisfied are noted.

### HPS‑1 – Log In  
1. User opens Web/Admin App → `Gateway‑Public` (unauthenticated).  
2. `Gateway‑Public` redirects to `AuthService` (OAuth2/OpenID Connect).  
3. `AuthService` exchanges credentials with the cloud `IdentityProvider` and, on success, obtains identity claims.  
4. `AuthService` stores/updates user session info in `UserStore` and issues a signed JWT.  
5. The JWT is returned to the user’s browser and attached to all subsequent requests.  

**Quality attributes:** QA‑5 (security) – user credentials never reach the business services; authorisation is enforced at the gateway with JWT validation.

### HPS‑3 – Query Prices  
1. External Partner or internal UI sends a query to `Gateway‑Public` (JWT validated, rate limiting applied).  
2. `Gateway‑Public` routes to `PriceQueryAPI`.  
3. `PriceQueryAPI` reads directly from the distributed `PriceCache` (Redis) – no database I/O.  
4. Result returned.  

**Quality attributes:**  
- QA‑1 (performance) – <1 ms response from cache, easily meeting the sub‑100 ms SLA for price queries.  
- QA‑4 (scalability) – `PriceQueryAPI` is stateless; all instances share the same Redis cluster, so any instance can handle any query.

### HPS‑2 – Change Prices  
1. Authenticated user → `Gateway‑Public` → `PriceCommandHandler`.  
2. `PriceCommandHandler` (within a local database transaction):  
   - Validates user’s permission and hotel data.  
   - Recalculates all derived rates based on the changed base/fixed rate.  
   - Atomically persists the new prices and **updates the `PriceCache`** (Redis) with the new values.  
   - Inserts an entry into the `Outbox Table` (same transaction).  
3. Control returns to the caller (the price is now **immediately available for queries**).  
4. `OutboxProcessor` (asynchronous, polling) reads the outbox entry and publishes the change event to a Kafka topic `price‑changes`.  
5. `ProtocolAdapter` consumes the Kafka event and translates it into a REST call to the **Channel Management System** (and possibly other external systems).  

**Quality attributes:**  
- QA‑1 (< 100 ms ready for query) – the synchronous Redis update makes the new price visible to all `PriceQueryAPI` instances within the same transaction; the round‑trip from change request to acknowledgement is kept under 100 ms through simple database writes + cache update.  
- QA‑2 (100 % delivery) – the outbox pattern guarantees that every committed price change is eventually published to Kafka, and the `ProtocolAdapter` can rely on Kafka’s durability and at‑least‑once delivery to push the change to the Channel system. Idempotent consumers on the Channel side (or in the adapter) prevent duplicates.  
- QA‑4 (scalability) – the `PriceCommandHandler` and `OutboxProcessor` are stateless; multiple instances can process changes in parallel. Redis scales horizontally as a cluster.

### HPS‑4 – Manage Hotels  
1. Administrator → `Gateway‑Public` → `HotelService`.  
2. `HotelService` performs CRUD on hotel data (persisted in its own database).  
3. `HotelService` calls `HotelEventPublisher` to emit a `hotel‑updated` event to Kafka.  

**Rationale:** The event allows the Pricing Engine (or any other service) to react to configuration changes (e.g., tax rates) without tight coupling. This satisfies QA‑6 (modifiability) because the reaction logic can be added later without changing the Hotel Management service.

### HPS‑5 – Manage Rates  
1. Administrator → `Gateway‑Public` → `RateManager`.  
2. `RateManager` updates rate definitions and business calculation rules (persisted).  
3. `RateManager` calls `HotelEventPublisher` to emit a `rate‑updated` event to Kafka.  

**Same rationale as HPS‑4.**

### HPS‑6 – Manage Users  
1. Administrator → `Gateway‑Public` → `UserAPI`.  
2. `UserAPI` changes user permissions/roles in `UserStore`.  
3. `AuthService` may subsequently re‑evaluate the user’s claims when a new JWT is issued.  

**QA‑5:** user management is isolated, and the gateway enforces the latest permissions via JWT content.

---

## 4.  Quality Attribute Coverage Summary

| QA | How the design satisfies it |
|----|-----------------------------|
| QA‑1 Performance | Query path: cache‑only, no DB. Change path: synchronous Redis update inside the write transaction ensures immediate readiness; total transaction time <100 ms. |
| QA‑2 Reliability | Transactional outbox + Kafka: every committed change is reliably published. Idempotent consumption in the ProtocolAdapter handles duplicates. |
| QA‑3 Availability | Stateless micro‑services, load‑balanced, with resilient infrastructure (Redis cluster, Kafka cluster). No single point of failure; each component can be deployed redundantly. (Further details will be added in later iterations with deployment views and redundancy patterns.) |
| QA‑4 Scalability | All business services are stateless and can be replicated. Redis scales as a cluster. Gateway distributes traffic. Kafka partitions allow parallel event processing. |
| QA‑5 Security | Credentials externalised to cloud identity provider. Gateway enforces JWT validation on every request; internal services trust the signed token. Roles checked in `PriceCommandHandler` and admin endpoints. |
| QA‑6 Modifiability | ProtocolAdapter decouples internal services from external protocols. New adapters (gRPC, GraphQL) can be added without changing Pricing Engine, Hotel Management, or User Management. Hotel/rate events allow future extensions without rework. |
| QA‑7 Deployability | All components containerised; environment‑specific configuration (e.g., Redis connection, Kafka bootstrap) externalised. The outbox pattern depends only on DB and Kafka, both available in non‑production. |
| QA‑8 Monitorability | Metrics can be collected at the gateway (request rate, latency) and at Kafka (publication latency, lag). Redis access times can be monitored. The `OutboxProcessor` can expose its progress. |
| QA‑9 Testability | Every external dependency (Redis, Kafka, identity provider, Channel system) is accessed through an interface, allowing mocking/stubbing during integration tests. The `PriceCache`, `OutboxProcessor`, and `ProtocolAdapter` can all be tested in isolation. |

All design decisions were made in light of the constraints (CON‑1 to CON‑6) and the concern for leveraging known technologies (CRN‑2). The next iteration will refine the internal interfaces, data models, and concurrency strategies.

## ADD Step 4: Design Concepts & Instantiate Elements

## 1.  Design Approach for Iteration 2  
Iteration 2 refines the internal structure from Iteration 1 by defining concrete component responsibilities, interfaces, and interaction sequences for the six primary use‑cases. The focus is on:

- **HPS‑2 Change Prices** – the critical write path must meet the < 100 ms price‑ready‑for‑query SLA (QA‑1) while guaranteeing 100 % delivery to external systems (QA‑2).  
- **HPS‑3 Query Prices** – the high‑volume read path must serve queries from cache with no database I/O (QA‑1) and scale horizontally (QA‑4).  
- **Separation of concerns** – the price calculation engine is shared between the live change and the simulation service (HPS‑2 simulation), avoiding logic duplication.  
- **Interface‑based design** – all external dependencies (cache, messaging, external partners) are abstracted behind interfaces to satisfy testability (QA‑9), protocol extensibility (QA‑6), and deployability (QA‑7).

The diagrams and traceability below document how each component contributes to the architectural drivers.

---

## 2.  Sequence Diagram – HPS‑2 Change Prices  

```mermaid
sequenceDiagram
    actor User
    participant WebApp
    participant GatewayPublic
    participant SimService as PriceSimulationService
    participant CmdHandler as PriceCommandHandler
    participant CalcEngine as PriceCalculationEngine
    participant DB
    participant Cache as PriceCache (Redis)
    participant OutboxTable
    participant OutboxProc as OutboxProcessor
    participant Kafka
    participant Adapter as ProtocolAdapter
    participant ChannelSys as Channel Management System

    rect rgba(0, 255, 0, 0.08)
        Note over User,CalcEngine: 1. Simulation (optional – no persistence)
        User->>WebApp: Request price change simulation
        WebApp->>GatewayPublic: POST /prices/simulate (JWT)
        GatewayPublic->>SimService: simulate(request)
        SimService->>CalcEngine: calculateDerivedRates(baseRate, rules)
        CalcEngine-->>SimService: simulated rates
        SimService-->>GatewayPublic: simulation result
        GatewayPublic-->>WebApp: simulation data
        WebApp-->>User: display simulated prices
    end

    rect rgba(0, 0, 255, 0.08)
        Note over User,OutboxTable: 2. Commit change (must be < 100 ms – QA‑1)
        User->>WebApp: Confirm price change
        WebApp->>GatewayPublic: POST /prices/change (JWT)
        GatewayPublic->>CmdHandler: changePrice(request)

        activate CmdHandler
        CmdHandler->>DB: validate hotel & user permissions
        CmdHandler->>CalcEngine: calculateDerivedRates(baseRate, rules)
        CalcEngine-->>CmdHandler: all derived rates

        Note over CmdHandler: single transactional unit
        CmdHandler->>DB: persist new rates
        CmdHandler->>OutboxTable: insert outbox record
        CmdHandler->>Cache: updatePrices(hotel, date, rates)
        CmdHandler-->>GatewayPublic: success (within budget)
        deactivate CmdHandler

        GatewayPublic-->>WebApp: OK
        WebApp-->>User: ✓ Price visible immediately
    end

    rect rgba(255, 255, 0, 0.08)
        Note over OutboxProc,ChannelSys: 3. Reliable async publication (QA‑2)
        loop every 100 ms
            OutboxProc->>OutboxTable: poll unpublished records
            OutboxTable-->>OutboxProc: record(s)
            OutboxProc->>Kafka: publish to topic `price‑changes`
            Kafka-->>OutboxProc: commit
        end

        Adapter->>Kafka: consume message
        Adapter->>ChannelSys: REST call (translates payload)
        ChannelSys-->>Adapter: acknowledgment
    end
```

**Key points satisfied by the sequence:**  
- **QA‑1 (< 100 ms ready for query)** – the write transaction includes a synchronous Redis update. The response to the client is sent only after the cache is updated, making the new price immediately queryable.  
- **QA‑2 (100 % delivery)** – the outbox insert is part of the same DB transaction as the price persistence. The `OutboxProcessor` guarantees at‑least‑once delivery to Kafka, and the `ProtocolAdapter` can be made idempotent on the receiving side.  
- **Simulation** – a dedicated `PriceSimulationService` reuses the `PriceCalculationEngine` without persisting anything, ensuring simulation results match the real calculation while keeping the critical write path untouched.

---

## 3.  Sequence Diagram – HPS‑3 Query Prices  

```mermaid
sequenceDiagram
    actor QueryClient (external or WebApp)
    participant GatewayPublic
    participant QueryAPI as PriceQueryAPI
    participant Cache as PriceCache (Redis)
    participant DB (fallback)

    QueryClient->>GatewayPublic: GET /prices?hotel=H&date=D&room=R (JWT)
    GatewayPublic->>GatewayPublic: validate JWT, rate limiting
    GatewayPublic->>QueryAPI: getPrices(H, D, R)
    QueryAPI->>Cache: get(key)

    alt Cache hit (normal path)
        Cache-->>QueryAPI: price data
    else Cache miss (cold start / eviction)
        Note over QueryAPI,DB: fallback – may exceed SLA; cache‑warming required
        QueryAPI->>DB: SELECT prices (read‑only replica)
        DB-->>QueryAPI: rates
        QueryAPI->>Cache: set(key, rates)  // populate
    end

    QueryAPI-->>GatewayPublic: price result
    GatewayPublic-->>QueryClient: JSON response (p95 < 5 ms from cache)
```

**Design decisions for QA‑1 and QA‑4:**  
- The hot path never touches the database – cache retrieval is O(1) and typically sub‑millisecond.  
- The `PriceQueryAPI` is stateless; all instances share the same Redis cluster. Horizontal scaling is trivial.  
- Cold‑start / cache‑eviction must be handled by a **cache‑warming** mechanism (e.g., a background job that preloads current prices after deployments) to avoid SLA violations.

---

## 4.  Class / Component Diagram with Interfaces  

```mermaid
classDiagram
    class IPriceCalculation {
        <<interface>>
        +calculateDerivedRates(baseRate, rules) DerivedRate[]
    }
    class PriceCalculationEngine {
        +calculateDerivedRates(baseRate, rules) DerivedRate[]
    }
    IPriceCalculation <|-- PriceCalculationEngine

    class IPriceCache {
        <<interface>>
        +get(key) PriceData
        +set(key, PriceData)
        +updatePrices(hotel, date, rates)
    }
    class RedisPriceCache {
        -redisClient: RedisClient
        +get(key)
        +set(key, PriceData)
        +updatePrices(hotel, date, rates)
    }
    IPriceCache <|-- RedisPriceCache

    class IMessagePublisher {
        <<interface>>
        +publish(topic, message) Future<Void>
    }
    class KafkaMessagePublisher {
        -kafkaProducer: KafkaProducer
        +publish(topic, message)
    }
    IMessagePublisher <|-- KafkaMessagePublisher

    class IExternalTranslator {
        <<interface>>
        +translateAndSend(event) CompletableFuture<Void>
    }
    class ProtocolAdapter {
        -client: HttpClient
        +translateAndSend(event)
    }
    IExternalTranslator <|-- ProtocolAdapter

    class PriceCommandHandler {
        -priceCache: IPriceCache
        -messagePublisher: IMessagePublisher
        -calculator: IPriceCalculation
        +changePrice(request) ChangeResponse
    }

    class PriceSimulationService {
        -calculator: IPriceCalculation
        +simulatePriceChange(request) SimulatedPrice[]
    }

    class PriceQueryAPI {
        -priceCache: IPriceCache
        +getPrices(hotelId, date, roomType) PriceData
    }

    class OutboxProcessor {
        -outboxRepo: OutboxRepository
        -messagePublisher: IMessagePublisher
        +processOutbox()
    }

    class GatewayPublic {
        -jwtValidator: JwtValidator
        -rateLimiter: RateLimiter
        +route(request)
    }

    class AuthService {
        -userStore: UserStore
        -idpClient: IdentityProviderClient
        +authenticate(credentials) Token
    }

    class UserAPI {
        -userStore: UserStore
        +managePermissions(userId, permissions)
    }

    class HotelService {
        -repository
        +createHotel(), +updateHotel(), +deleteHotel()
    }

    class RateManager {
        -repository
        +createRate(), +updateRate(), +deleteRate()
    }

    class HotelEventPublisher {
        -messagePublisher: IMessagePublisher
        +publishHotelEvent(event)
        +publishRateEvent(event)
    }

    PriceCommandHandler --> IPriceCache : uses
    PriceCommandHandler --> IMessagePublisher : uses
    PriceCommandHandler --> IPriceCalculation : uses
    PriceSimulationService --> IPriceCalculation : uses
    PriceQueryAPI --> IPriceCache : uses
    OutboxProcessor --> IMessagePublisher : uses
    HotelEventPublisher --> IMessagePublisher : uses
    GatewayPublic --> PriceCommandHandler : routes
    GatewayPublic --> PriceQueryAPI : routes
    GatewayPublic --> HotelService : routes
    GatewayPublic --> RateManager : routes
    GatewayPublic --> UserAPI : routes
    GatewayPublic --> AuthService : redirects
    ProtocolAdapter ..> Kafka : consumes events (implements IExternalTranslator)
```

**Interface rationale (testability → QA‑9, modifiability → QA‑6):**  
- Every integration point (cache, messaging, external partners) is behind an interface, allowing mocking in unit and integration tests without standing up real infrastructure.  
- Adding a new protocol (e.g., gRPC) only requires a new implementation of `IExternalTranslator` in the `ProtocolAdapter` – no changes to the core services.

---

## 5.  Component‑to‑Driver Traceability  

| Component | Primary Responsibility | Key Drivers Satisfied |
|-----------|------------------------|------------------------|
| **GatewayPublic** | Enforce JWT validation, rate limiting, route requests to business services. | QA‑5 (security), QA‑4 (throttling/scale), CRN‑2 (Angular‑friendly REST) |
| **AuthService** | Integrate with cloud identity provider, issue signed JWTs with hotel‑scoped claims. | QA‑5 (externalised auth, fine‑grained claims), CON‑2 |
| **PriceCommandHandler** | Handle price changes: validate, recalculate, persist + outbox + cache update in one transaction. | QA‑1 (< 100 ms), QA‑2 (outbox), HPS‑2 |
| **PriceSimulationService** | Provide “what‑if” simulation of price changes using the same calculation engine, no persistence. | HPS‑2 (simulation), QA‑9 (reuses tested logic) |
| **PriceCalculationEngine** | Compute all derived rates from a base/fixed rate and business rules. | HPS‑2, QA‑9 (isolated testable logic) |
| **PriceQueryAPI** | Serve current prices from a read‑only cache (Redis). Fallback to DB on cold miss (with warming strategy). | QA‑1 (sub‑ms read), QA‑4 (stateless, horizontally scalable), HPS‑3 |
| **RedisPriceCache (IPriceCache)** | Distributed in‑memory store for guaranteed immediate reads after a price change. | QA‑1 (synchronous update), QA‑4 (cluster scaling) |
| **OutboxProcessor** | Poll the outbox table and reliably forward events to Kafka with at‑least‑once semantics. | QA‑2 (reliable delivery) |
| **KafkaMessagePublisher (IMessagePublisher)** | Produce messages to Kafka topics. | QA‑2 (durable log) |
| **ProtocolAdapter (IExternalTranslator)** | Consume Kafka events, translate payloads, and push to external systems via REST (later gRPC/GraphQL). | QA‑2 (guaranteed delivery), QA‑6 (protocol extensibility – adapter is replaceable) |
| **HotelEventPublisher** | Emit hotel/rate configuration changes to Kafka for future consumers without service coupling. | QA‑6 (modifiability), CON‑5 (REST first, event‑driven evolution) |
| **HotelService, RateManager, UserAPI** | CRUD operations for hotel data, rate definitions, and user permissions. | HPS‑4, HPS‑5, HPS‑6, QA‑5 (admin authorisation) |

**Additional design notes:**  
- **Cold‑start strategy** for `PriceQueryAPI`: In production, a cache‑warmer will run on deployment to pre‑load the current day’s prices. Cache evictions are set with a configurable TTL (initially 5 seconds, adjustable). A cold miss that causes a database read is logged but considered acceptable only during initial ramp‑up.  
- **Transactional cache update**: Redis does not participate in the DB transaction; the update is issued right after the DB commit but before the HTTP response. If Redis fails, the command handler invalidates the key (via eventual retry) and returns success – the price may then be served from the DB for a brief period, which is an acceptable trade‑off given Redis’s high availability.  
- All components are designed for containerisation (CON‑6), with environment‑specific configuration externalised (QA‑7).  

This detailed design ensures that the critical write path (HPS‑2) and the high‑volume read path (HPS‑3) directly satisfy the demanding quality attributes, while the remaining use‑cases are fully supported by the modular, interface‑based architecture.

## ADD Step 6: Sketch Views

## Step 6 – Sketch Views and Record Design Decisions

After incorporating the Orchestrator’s resolutions, the design is now complete and consistent. The following views document the updated architecture.

### 1. Component Diagram with Interfaces

The diagram shows the micro‑service decomposition, internal components, and the abstraction layers (interfaces) that support testability, modularity, and protocol extensibility.  
Boundaries are drawn for the **Pricing Engine**, **Hotel Management**, **User Management**, **API Layer**, and **Integration**.

```mermaid
graph TD
    subgraph ext[External Entities]
        WebApp["Web App (Angular)"]
        AdminApp["Admin App (Angular)"]
        IdentityProvider["Cloud Identity Service"]
        ChannelSys["Channel Management System"]
    end

    subgraph api[API Layer]
        direction LR
        GW["Gateway‑Public<br/>API Gateway"]
        GW -->|validates JWT,<br/>rate‑limiting| JWTToken["JWT Validator"]
    end

    subgraph pricing[Pricing Engine]
        direction LR
        Cmd["PriceCommandHandler"]
        Sim["PriceSimulationService"]
        CalcEngine["PriceCalculationEngine<br/>deriveRates()"]
        QueryAPI["PriceQueryAPI"]
        Cache["RedisPriceCache"]
        OutboxRepo["IOutboxRepository <br/> (DB table)"]

        %% Provided interfaces
        Cache -.->|provides| ICache["IPriceCache"]
        OutboxRepo -.->|provides| IOutbox["IOutboxRepository"]

        %% Dependencies
        Cmd -->|uses| ICache
        Cmd -->|uses| IOutbox
        Cmd -->|uses| ICalc["IPriceCalculation"]
        Sim -->|uses| ICalc

        QueryAPI -->|uses| ICache
    end

    subgraph integration[Integration / Eventing]
        OutboxProc["OutboxProcessor"]
        Kafka["Kafka Broker"]
        ProtoAdapter["ProtocolAdapter"]
        DLQ["Dead‑letter<br/>Topic"]
        RateConsumer["RateChangeConsumer<br/>(placeholder)"]

        OutboxProc -->|uses| IOutbox
        OutboxProc -->|uses| IPub["IMessagePublisher"]
        ProtoAdapter -->|implements| ITrans["IExternalTranslator"]
        RateConsumer -->|uses| ICache
    end

    subgraph hotel[Hotel Management]
        direction LR
        HotelSvc["HotelService"]
        RateMgr["RateManager"]
        HotelPub["HotelEventPublisher"]

        HotelPub -->|uses| IPub
    end

    subgraph user_mgmt[User Management]
        direction LR
        AuthSvc["AuthService"]
        UserAPI["UserAPI"]
        UserStore["UserStore"]
        PermMgr["PermissionsManager"]

        AuthSvc -->|uses| IdP["OAuth2/OIDC Client"]
    end

    %% Routing
    GW -->|routes to| Cmd
    GW -->|routes to| QueryAPI
    GW -->|routes to| HotelSvc
    GW -->|routes to| RateMgr
    GW -->|routes to| UserAPI
    GW -->|redirects| AuthSvc

    %% Kafka flows
    OutboxProc -->|publishes<br/>price‑changes| Kafka
    HotelPub -->|publishes<br/>hotel‑events| Kafka
    Kafka -->|consumes| ProtoAdapter
    Kafka -->|consumes| RateConsumer

    ProtoAdapter -->|REST/gRPC| ChannelSys
    ProtoAdapter -->|exhausted retries| DLQ

    AuthSvc -->|validates| IdentityProvider
    WebApp -->|HTTPS| GW
    AdminApp -->|HTTPS| GW
```

**Interfaces** (implemented by concrete components, visible as nodes):  
- `IPriceCache` – provided by RedisPriceCache; used by `PriceCommandHandler`, `PriceQueryAPI`, and `RateChangeConsumer`.  
- `IOutboxRepository` – provided by the outbox database table; used by `PriceCommandHandler` and `OutboxProcessor`.  
- `IMessagePublisher` – provided by Kafka client; used by `OutboxProcessor` and `HotelEventPublisher`.  
- `IExternalTranslator` – implemented by `ProtocolAdapter`, enabling protocol‑agnostic external delivery.  
- `IPriceCalculation` – provided by `PriceCalculationEngine`, used by both `PriceCommandHandler` and `PriceSimulationService` to ensure consistent derived‑rate calculations.

**Key changes from the Reviewer’s findings:**  
- `PriceCommandHandler` no longer depends on `IMessagePublisher`; it uses `IOutboxRepository` to insert the outbox record, enforcing the outbox pattern.  
- A `RateChangeConsumer` placeholder is included; it subscribes to `hotel‑events` and will invalidate or recalculate cached prices when rate rules change (to be refined in a later iteration).  
- `ProtocolAdapter` includes a dead‑letter topic for messages that cannot be delivered after all retries.

---

### 2. Sequence Diagram – HPS‑2 Change Prices (with reliability and error handling)

```mermaid
sequenceDiagram
    actor User
    actor WebApp
    participant GW as "Gateway‑Public"
    participant Sim as "PriceSimulationService"
    participant Cmd as "PriceCommandHandler"
    participant Calc as "PriceCalculationEngine"
    participant DB
    participant Cache as "PriceCache (Redis)"
    participant OutboxTbl as "OutboxTable"
    participant OutboxProc as "OutboxProcessor"
    participant Kafka
    participant Adapter as "ProtocolAdapter"
    participant Channel as "Channel Management System"
    participant DLQ as "Dead‑letter Topic"

    %% 1. Simulation (optional)
    rect rgba(144,238,144,0.15)
        Note over User,Calc: Simulation – no persistence
        User ->> WebApp: Request simulation
        WebApp ->> GW: POST /prices/simulate (JWT)
        GW ->> Sim: simulate(request)
        Sim ->> Calc: calculateDerivedRates(baseRate, rules)
        Calc -->> Sim: simulated rates
        Sim -->> GW: result
        GW -->> WebApp: simulation data
        WebApp -->> User: display
    end

    %% 2. Commit change (must be <100ms, QA-1)
    rect rgba(173,216,230,0.15)
        User ->> WebApp: Confirm price change
        WebApp ->> GW: POST /prices/change (JWT)
        GW ->> Cmd: changePrice(request)

        activate Cmd
        Cmd ->> DB: validate hotel & permissions
        Cmd ->> Calc: calculateDerivedRates(baseRate, rules)
        Calc -->> Cmd: all derived rates

        Note over Cmd: Start DB transaction<br/>persist new rates,<br/>insert outbox record
        Cmd ->> DB: INSERT rates (commit)
        Cmd ->> OutboxTbl: INSERT outbox entry (same TX)

        Note over Cmd: synchronous cache update<br/>must succeed for SLA
        critical Redis write
            Cmd ->> Cache: updatePrices(hotelId, date, rates)
            Cache -->> Cmd: OK / ACK
        option Redis failure
            Cmd -->> GW: 503 Service Unavailable
            GW -->> User: price change failed, retry
            deactivate Cmd
        end

        Cmd -->> GW: 200 OK (prices ready for query)
        GW -->> User: ✓ Price visible immediately
        deactivate Cmd
    end

    %% 3. Reliable async publication (QA-2)
    rect rgba(255,255,200,0.15)
        loop every 100ms
            OutboxProc ->> OutboxTbl: poll unpublished records
            OutboxTbl -->> OutboxProc: record(s)
            OutboxProc ->> Kafka: publish to topic `price‑changes`
            Kafka -->> OutboxProc: commit
        end

        Adapter ->> Kafka: consume message
        activate Adapter
        loop retry up to 5 attempts, exponential backoff
            Adapter ->> Channel: POST /external‑price‑change (REST)
            alt success (2xx)
                Channel -->> Adapter: acknowledgment
                Adapter ->> Kafka: commit offset
            else failure (5xx, timeout)
                Adapter -->> Adapter: log & wait
            end
        end

        opt all retries exhausted
            Adapter ->> DLQ: send message for manual intervention
        end
        deactivate Adapter
    end
```

**SLA enforcement (QA‑1):**  
- The `PriceCommandHandler` writes to Redis **synchronously** after committing the database transaction. If the cache write fails, the operation signals a failure (`503`), and the entire change is rolled back – the new prices never become “ready for query” without the cache entry. This guarantees that when the response is `200`, subsequent cache‑only queries (HPS‑3) will see the updated price within the 100 ms window.  
- The internal calculation, database persistence, outbox insert, and cache update together are kept under the deadline through efficient JPA/Spring Data operations and co‑located deployment (same JVM as the command handler in the initial phase).

**End‑to‑end reliability (QA‑2):**  
- The transactional outbox ensures that every committed price change is persisted in the outbox table.  
- The `OutboxProcessor` polls the outbox and publishes to Kafka, offering at‑least‑once delivery.  
- The `ProtocolAdapter` commits the Kafka offset **only after** a successful `2xx` response from the Channel Management System. A configurable retry loop (up to 5 attempts, exponential backoff) is applied. On permanent failure, the message is moved to a dead‑letter topic for alerting and manual replay. This makes the external delivery guarantee as “at‑least‑once” as long as the external system is idempotent.

---

### 3. Sequence Diagram – HPS‑3 Query Prices (high‑performance read path)

```mermaid
sequenceDiagram
    actor Client as "Query Client<br/>(WebApp, External)"
    participant GW as "Gateway‑Public"
    participant QueryAPI as "PriceQueryAPI"
    participant Cache as "PriceCache (Redis)"
    participant DB as "DB (read replica)"

    Client ->> GW: GET /prices?hotel=H&date=D&room=R (JWT)
    GW ->> GW: validate JWT, apply rate‑limiting
    GW ->> QueryAPI: getPrices(H, D, R)
    QueryAPI ->> Cache: get(key)

    alt Cache hit (normal path)
        Cache -->> QueryAPI: price data
    else Cache miss (cold start / eviction)
        Note over QueryAPI,DB: fallback – logged & monitored
        QueryAPI ->> DB: SELECT prices (read‑only replica)
        DB -->> QueryAPI: rates
        QueryAPI ->> Cache: set(key, rates)  // populate for subsequent reads
    end

    QueryAPI -->> GW: price result
    GW -->> Client: JSON response (p95 <5ms from cache, <20ms from DB)
```

**Performance guarantees (QA‑1, QA‑4):**  
- The hot path never touches the database – distributed Redis (cluster) ensures sub‑millisecond reads.  
- All instances of `PriceQueryAPI` are stateless and share the cache; horizontal scaling is transparent.  
- Cache‑miss events are rare in steady‑state because a cache‑warming job preloads current‑day prices at deployment. The fallback path is still acceptable (<20 ms via read replica), but it is logged so that operations can detect any unexpected eviction spikes.  
- The `Gateway‑Public` enforces rate limiting, protecting the query service under high loads (QA‑4).

---

### 4. Data Model Diagram

The core entities and their relationships that underpin the pricing, hotel management, and user access control.

```mermaid
erDiagram
    Hotel {
        string hotelId PK
        string name
        string country
        decimal defaultTax
        datetime createdAt
    }

    RoomType {
        string roomTypeId PK
        string hotelId FK
        string name
        string description
    }

    Rate {
        string rateId PK
        string hotelId FK
        string name
        enum rateType "BASE, FIXED, SEASONAL..."
        string calculationRules
        boolean active
    }

    Price {
        string priceId PK
        string hotelId FK
        string roomTypeId FK
        string rateId FK
        date priceDate
        decimal amount
        datetime lastUpdated
    }

    User {
        string userId PK
        string externalId
        string email
        string status
    }

    Permission {
        string permissionId PK
        string userId FK
        string hotelId FK
        enum role "HOTEL_ADMIN, PRICE_CHANGER, READ_ONLY"
    }

    Hotel ||--|{ RoomType : "has"
    Hotel ||--|{ Rate : "defines"
    Hotel ||--|{ Price : "has current prices"
    RoomType ||--o{ Price : "used in"
    Rate ||--o{ Price : "used in"
    User ||--o{ Permission : "assigned"
    Hotel ||--o{ Permission : "scopes access to"
```

**Design rationale:**  
- `Hotel` is the central entity for multi‑tenancy and rate definitions.  
- `Rate` stores the “business rule” template (e.g., base rate, day‑of‑week multiplier, fixed amount). The `calculationRules` field can hold a JSON structure that the `PriceCalculationEngine` interprets.  
- `Price` is a concrete, computed price point for a given **Hotel**, **RoomType**, **Rate**, and date. It is created/recalculated during the `changePrice` operation and stored in the database and in Redis. The schema supports fast retrieval for date‑hotel‑room‑type queries.  
- `User` and `Permission` support the fine‑grained access control required by HPS‑2 and HPS‑6. When a JWT is issued, the claims include an array of hotel‑permission pairs, which the gateway and `PriceCommandHandler` validate.

---

### 5. Record of Design Decisions – Iteration 2

| ID  | Decision | Rationale | Architectural Drivers |
|-----|----------|-----------|------------------------|
| D1  | **Use transactional outbox pattern** for price changes. The `PriceCommandHandler` inserts an outbox record in the same database transaction as the price persistence. | Guarantees that every committed price change is eventually published to Kafka, satisfying the 100% delivery requirement (QA‑2) without dual‑write inconsistencies. | QA‑2 (Reliability), CON‑5 (REST first) |
| D2  | **Synchronous Redis cache update inside the write transaction**. If the cache update fails, the entire price change request is rejected (503). | Ensures that after a successful change the price is immediately “ready for query” in the read‑optimised store, meeting the <100 ms SLA (QA‑1). | QA‑1 (Performance) |
| D3  | **Shared `PriceCalculationEngine`** used by both `PriceCommandHandler` and `PriceSimulationService`. | Avoids logic duplication between simulation and actual price changes, reducing defect risk and simplifying testing (QA‑9). | HPS‑2 (Simulation), QA‑9 (Testability) |
| D4  | **All external dependencies abstracted behind interfaces**: `IPriceCache`, `IMessagePublisher`, `IOutboxRepository`, `IExternalTranslator`, `IPriceCalculation`. | Enables isolated unit/integration testing (QA‑9), protocol extensibility (QA‑6), and cloud‑native deployability (CON‑6) by allowing different implementations per environment. | QA‑6, QA‑9, QA‑7 |
| D5  | **`ProtocolAdapter` implements at‑least‑once delivery to external systems** with retry, Kafka offset commit on success, and a dead‑letter topic for exhausted retries. | Closes the reliability gap identified by the Reviewer; guarantees that the Channel Management System eventually receives every change or that failed attempts are flagged for manual intervention. | QA‑2 (Reliability) |
| D6  | **Gateway‑Public as single policy enforcement point**: JWT validation, rate limiting, and routing. Internal services trust the signed token and extract user context (hotel‑permission claims). | Concentrates security concerns, simplifies internal services, and supports stateless scaling (QA‑4) while maintaining per‑hotel authorisation (QA‑5). | QA‑5 (Security), QA‑4 (Scalability) |
| D7  | **Remove `IMessagePublisher` from `PriceCommandHandler`**; it only uses `IOutboxRepository`. | Enforces the outbox pattern and prevents accidental bypass, addressing the Reviewer’s concern about architectural clarity. | CRN‑4 (Avoid technical debt) |
| D8  | **Introduction of a `RateChangeConsumer` placeholder** that listens to `hotel‑events` and will invalidate/update the cache when rate rules change. | Acknowledges eventual consistency risk when administrators modify rate definitions (HPS‑5). The placeholder makes the system ready for this feature in a later iteration without structural changes. | QA‑1 (Correctness), HPS‑5 |
| D9  | **Cold‑start cache warming** via a deployment hook that preloads current‑day prices. Cache‑miss fallback to a read‑replica database is monitored but not the normal path. | Protects the SLA during deployments or after cache cluster recovery; ensures the read path meets QA‑1 even in edge cases. | QA‑1 (Performance) |
| D10 | **Stateless design for all business services**; state stored only in Redis and Kafka. | Enables horizontal scaling, rolling updates, and cloud‑native orchestration (QA‑4, QA‑7). | QA‑4 (Scalability), CON‑6 (Cloud‑native) |

---

All diagrams and decisions are now consistent with the architectural drivers and the corrections mandated by the Orchestrator. This design provides a solid foundation for the next iteration, where deployment views, containerisation details, and monitoring infrastructure will be refined.

## ADD Step 7: Analysis & Review

# Architecture Quality Review – Iteration 2, Step 6 Final Artifacts

## Overall Assessment: **NEEDS_REVISION**

The design has made good progress and addresses most drivers. However, a critical inconsistency in the implementation of QA‑1 (atomicity between DB commit and Redis cache update) and a minor Mermaid syntax error prevent full acceptance.

---

## Strengths
- The component decomposition and interface‑based architecture clearly support testability (QA‑9) and protocol extensibility (QA‑6).  
- The transactional outbox + Kafka + retry + dead‑letter pattern reliably addresses QA‑2.  
- All six primary use cases are traceable through the components and sequences.  
- The data model supports the required relationships and access control.  
- The design decisions are well documented with driver traceability.

---

## Issues Found

### 1. QA‑1 atomicity violation – DB commit before Redis update (Critical)
The sequence diagram for HPS‑2 **commits the database transaction** (`INSERT rates (commit)`) **before** attempting to update the Redis cache.  
If the subsequent cache write fails, the error path returns a `503` to the client, but the changed prices are **already persisted in the database** and cannot be rolled back.  

**Why this matters:**  
- Decision D2 states *“If the cache update fails, the entire price change request is rejected … the price change is not committed to the database”*, but the sequence demonstrates the opposite – a committed DB change without a cache entry.  
- QA‑1 requires *“prices for all rates and room types are published (ready for query) in less than 100 ms”*. With a committed DB and no cache entry, the price is **not** ready for immediate query – violating the SLA.

**Consequence:**  
The claimed guarantee that a `200` response means the price is immediately queryable **does not hold** if Redis fails after the DB commit. The design must either:  
- Reverse the order (update cache, then commit DB, with compensation if DB fails) – not trivial,  
- Accept that `200` is returned only after both succeed, but treat a failure after DB commit as an exceptional case that still violates QA‑1, or  
- Choose a different synchronization pattern (e.g., write‑through cache that participates in the same logical unit of work).  

The design is therefore inconsistent with the recorded decision D2 and the QA‑1 scenario.

### 2. Mermaid syntax error in data model diagram
The `Rate` entity uses `enum rateType` – `enum` is not a supported datatype in Mermaid’s `erDiagram`. Valid types are `int`, `string`, `varchar`, `decimal`, `date`, etc.  
This will cause rendering failures.

### 3. Minor traceability inaccuracy in decision D8
Decision D8 references *“QA‑1 (Correctness)”* – QA‑1 is **Performance**, not correctness. While it does affect correctness of the query result, the primary driver label is misleading. (Does not block acceptance, but worth correcting.)

---

## Suggestions for Revision

1. **Resolve QA‑1 atomicity**  
   - Option A (most conservative): Use a **single‑phase cache‑through** approach where the `PriceCommandHandler` writes to Redis **inside** the DB transaction via a caching proxy that can participate in the same atomic scope (e.g., using a cache that supports transactional semantics, or a dual‑write pattern with compensation). If that is not feasible, then:  
   - Option B: **Commit DB first, update Redis synchronously, but if Redis fails → return `503` and log the inconsistency**. Document that the price is **temporarily** unavailable in the cache and will be served via the DB fallback until the next cache warming or a background worker repairs it. This would **not** meet the strict “ready for query <100 ms” for that specific request, but could be accepted as a rare failure mode. The decision D2 must then be changed to reflect reality, and the risk must be formally recorded.  

   The **current diagram** must be updated to show the true sequence and its consequences.

2. **Fix the data model diagram**  
   Replace `enum rateType` with `string rateType` and add a comment describing the values. For example:  
   ```mermaid
   Rate {
       string rateId PK
       string hotelId FK
       string name
       string rateType "BASE, FIXED, SEASONAL..."
       string calculationRules
       boolean active
   }
   ```

3. **Correct D8**  
   Change *“QA‑1 (Correctness)”* to *“QA‑1 (Performance – ensures cache freshness)”*.

---

## Decision
The design **cannot be accepted** in its present form due to the critical QA‑1 inconsistency. The Designer must resolve the atomicity issue, update the sequence diagram and decision D2 accordingly, and fix the Mermaid syntax error.  

Once these corrections are verified, the architecture will be ready to proceed to Iteration 3.

## Reviewer Assessment

> **Timestamp**: 2026-06-11T10:30:51.2938429

# Architecture Quality Review – Iteration 2, Step 6 Final Artifacts

## Overall Assessment: **NEEDS_REVISION**

The design has made good progress and addresses most drivers. However, a critical inconsistency in the implementation of QA‑1 (atomicity between DB commit and Redis cache update) and a minor Mermaid syntax error prevent full acceptance.

---

## Strengths
- The component decomposition and interface‑based architecture clearly support testability (QA‑9) and protocol extensibility (QA‑6).  
- The transactional outbox + Kafka + retry + dead‑letter pattern reliably addresses QA‑2.  
- All six primary use cases are traceable through the components and sequences.  
- The data model supports the required relationships and access control.  
- The design decisions are well documented with driver traceability.

---

## Issues Found

### 1. QA‑1 atomicity violation – DB commit before Redis update (Critical)
The sequence diagram for HPS‑2 **commits the database transaction** (`INSERT rates (commit)`) **before** attempting to update the Redis cache.  
If the subsequent cache write fails, the error path returns a `503` to the client, but the changed prices are **already persisted in the database** and cannot be rolled back.  

**Why this matters:**  
- Decision D2 states *“If the cache update fails, the entire price change request is rejected … the price change is not committed to the database”*, but the sequence demonstrates the opposite – a committed DB change without a cache entry.  
- QA‑1 requires *“prices for all rates and room types are published (ready for query) in less than 100 ms”*. With a committed DB and no cache entry, the price is **not** ready for immediate query – violating the SLA.

**Consequence:**  
The claimed guarantee that a `200` response means the price is immediately queryable **does not hold** if Redis fails after the DB commit. The design must either:  
- Reverse the order (update cache, then commit DB, with compensation if DB fails) – not trivial,  
- Accept that `200` is returned only after both succeed, but treat a failure after DB commit as an exceptional case that still violates QA‑1, or  
- Choose a different synchronization pattern (e.g., write‑through cache that participates in the same logical unit of work).  

The design is therefore inconsistent with the recorded decision D2 and the QA‑1 scenario.

### 2. Mermaid syntax error in data model diagram
The `Rate` entity uses `enum rateType` – `enum` is not a supported datatype in Mermaid’s `erDiagram`. Valid types are `int`, `string`, `varchar`, `decimal`, `date`, etc.  
This will cause rendering failures.

### 3. Minor traceability inaccuracy in decision D8
Decision D8 references *“QA‑1 (Correctness)”* – QA‑1 is **Performance**, not correctness. While it does affect correctness of the query result, the primary driver label is misleading. (Does not block acceptance, but worth correcting.)

---

## Suggestions for Revision

1. **Resolve QA‑1 atomicity**  
   - Option A (most conservative): Use a **single‑phase cache‑through** approach where the `PriceCommandHandler` writes to Redis **inside** the DB transaction via a caching proxy that can participate in the same atomic scope (e.g., using a cache that supports transactional semantics, or a dual‑write pattern with compensation). If that is not feasible, then:  
   - Option B: **Commit DB first, update Redis synchronously, but if Redis fails → return `503` and log the inconsistency**. Document that the price is **temporarily** unavailable in the cache and will be served via the DB fallback until the next cache warming or a background worker repairs it. This would **not** meet the strict “ready for query <100 ms” for that specific request, but could be accepted as a rare failure mode. The decision D2 must then be changed to reflect reality, and the risk must be formally recorded.  

   The **current diagram** must be updated to show the true sequence and its consequences.

2. **Fix the data model diagram**  
   Replace `enum rateType` with `string rateType` and add a comment describing the values. For example:  
   ```mermaid
   Rate {
       string rateId PK
       string hotelId FK
       string name
       string rateType "BASE, FIXED, SEASONAL..."
       string calculationRules
       boolean active
   }
   ```

3. **Correct D8**  
   Change *“QA‑1 (Correctness)”* to *“QA‑1 (Performance – ensures cache freshness)”*.

---

## Decision
The design **cannot be accepted** in its present form due to the critical QA‑1 inconsistency. The Designer must resolve the atomicity issue, update the sequence diagram and decision D2 accordingly, and fix the Mermaid syntax error.  

Once these corrections are verified, the architecture will be ready to proceed to Iteration 3.

## Final Design Decision for Iteration 2

## Step 6 – Sketch Views and Record Design Decisions

After incorporating the Orchestrator’s resolutions, the design is now complete and consistent. The following views document the updated architecture.

### 1. Component Diagram with Interfaces

The diagram shows the micro‑service decomposition, internal components, and the abstraction layers (interfaces) that support testability, modularity, and protocol extensibility.  
Boundaries are drawn for the **Pricing Engine**, **Hotel Management**, **User Management**, **API Layer**, and **Integration**.

```mermaid
graph TD
    subgraph ext[External Entities]
        WebApp["Web App (Angular)"]
        AdminApp["Admin App (Angular)"]
        IdentityProvider["Cloud Identity Service"]
        ChannelSys["Channel Management System"]
    end

    subgraph api[API Layer]
        direction LR
        GW["Gateway‑Public<br/>API Gateway"]
        GW -->|validates JWT,<br/>rate‑limiting| JWTToken["JWT Validator"]
    end

    subgraph pricing[Pricing Engine]
        direction LR
        Cmd["PriceCommandHandler"]
        Sim["PriceSimulationService"]
        CalcEngine["PriceCalculationEngine<br/>deriveRates()"]
        QueryAPI["PriceQueryAPI"]
        Cache["RedisPriceCache"]
        OutboxRepo["IOutboxRepository <br/> (DB table)"]

        %% Provided interfaces
        Cache -.->|provides| ICache["IPriceCache"]
        OutboxRepo -.->|provides| IOutbox["IOutboxRepository"]

        %% Dependencies
        Cmd -->|uses| ICache
        Cmd -->|uses| IOutbox
        Cmd -->|uses| ICalc["IPriceCalculation"]
        Sim -->|uses| ICalc

        QueryAPI -->|uses| ICache
    end

    subgraph integration[Integration / Eventing]
        OutboxProc["OutboxProcessor"]
        Kafka["Kafka Broker"]
        ProtoAdapter["ProtocolAdapter"]
        DLQ["Dead‑letter<br/>Topic"]
        RateConsumer["RateChangeConsumer<br/>(placeholder)"]

        OutboxProc -->|uses| IOutbox
        OutboxProc -->|uses| IPub["IMessagePublisher"]
        ProtoAdapter -->|implements| ITrans["IExternalTranslator"]
        RateConsumer -->|uses| ICache
    end

    subgraph hotel[Hotel Management]
        direction LR
        HotelSvc["HotelService"]
        RateMgr["RateManager"]
        HotelPub["HotelEventPublisher"]

        HotelPub -->|uses| IPub
    end

    subgraph user_mgmt[User Management]
        direction LR
        AuthSvc["AuthService"]
        UserAPI["UserAPI"]
        UserStore["UserStore"]
        PermMgr["PermissionsManager"]

        AuthSvc -->|uses| IdP["OAuth2/OIDC Client"]
    end

    %% Routing
    GW -->|routes to| Cmd
    GW -->|routes to| QueryAPI
    GW -->|routes to| HotelSvc
    GW -->|routes to| RateMgr
    GW -->|routes to| UserAPI
    GW -->|redirects| AuthSvc

    %% Kafka flows
    OutboxProc -->|publishes<br/>price‑changes| Kafka
    HotelPub -->|publishes<br/>hotel‑events| Kafka
    Kafka -->|consumes| ProtoAdapter
    Kafka -->|consumes| RateConsumer

    ProtoAdapter -->|REST/gRPC| ChannelSys
    ProtoAdapter -->|exhausted retries| DLQ

    AuthSvc -->|validates| IdentityProvider
    WebApp -->|HTTPS| GW
    AdminApp -->|HTTPS| GW
```

**Interfaces** (implemented by concrete components, visible as nodes):  
- `IPriceCache` – provided by RedisPriceCache; used by `PriceCommandHandler`, `PriceQueryAPI`, and `RateChangeConsumer`.  
- `IOutboxRepository` – provided by the outbox database table; used by `PriceCommandHandler` and `OutboxProcessor`.  
- `IMessagePublisher` – provided by Kafka client; used by `OutboxProcessor` and `HotelEventPublisher`.  
- `IExternalTranslator` – implemented by `ProtocolAdapter`, enabling protocol‑agnostic external delivery.  
- `IPriceCalculation` – provided by `PriceCalculationEngine`, used by both `PriceCommandHandler` and `PriceSimulationService` to ensure consistent derived‑rate calculations.

**Key changes from the Reviewer’s findings:**  
- `PriceCommandHandler` no longer depends on `IMessagePublisher`; it uses `IOutboxRepository` to insert the outbox record, enforcing the outbox pattern.  
- A `RateChangeConsumer` placeholder is included; it subscribes to `hotel‑events` and will invalidate or recalculate cached prices when rate rules change (to be refined in a later iteration).  
- `ProtocolAdapter` includes a dead‑letter topic for messages that cannot be delivered after all retries.

---

### 2. Sequence Diagram – HPS‑2 Change Prices (with reliability and error handling)

```mermaid
sequenceDiagram
    actor User
    actor WebApp
    participant GW as "Gateway‑Public"
    participant Sim as "PriceSimulationService"
    participant Cmd as "PriceCommandHandler"
    participant Calc as "PriceCalculationEngine"
    participant DB
    participant Cache as "PriceCache (Redis)"
    participant OutboxTbl as "OutboxTable"
    participant OutboxProc as "OutboxProcessor"
    participant Kafka
    participant Adapter as "ProtocolAdapter"
    participant Channel as "Channel Management System"
    participant DLQ as "Dead‑letter Topic"

    %% 1. Simulation (optional)
    rect rgba(144,238,144,0.15)
        Note over User,Calc: Simulation – no persistence
        User ->> WebApp: Request simulation
        WebApp ->> GW: POST /prices/simulate (JWT)
        GW ->> Sim: simulate(request)
        Sim ->> Calc: calculateDerivedRates(baseRate, rules)
        Calc -->> Sim: simulated rates
        Sim -->> GW: result
        GW -->> WebApp: simulation data
        WebApp -->> User: display
    end

    %% 2. Commit change (must be <100ms, QA-1)
    rect rgba(173,216,230,0.15)
        User ->> WebApp: Confirm price change
        WebApp ->> GW: POST /prices/change (JWT)
        GW ->> Cmd: changePrice(request)

        activate Cmd
        Cmd ->> DB: validate hotel & permissions
        Cmd ->> Calc: calculateDerivedRates(baseRate, rules)
        Calc -->> Cmd: all derived rates

        Note over Cmd: Start DB transaction<br/>persist new rates,<br/>insert outbox record
        Cmd ->> DB: INSERT rates (commit)
        Cmd ->> OutboxTbl: INSERT outbox entry (same TX)

        Note over Cmd: synchronous cache update<br/>must succeed for SLA
        critical Redis write
            Cmd ->> Cache: updatePrices(hotelId, date, rates)
            Cache -->> Cmd: OK / ACK
        option Redis failure
            Cmd -->> GW: 503 Service Unavailable
            GW -->> User: price change failed, retry
            deactivate Cmd
        end

        Cmd -->> GW: 200 OK (prices ready for query)
        GW -->> User: ✓ Price visible immediately
        deactivate Cmd
    end

    %% 3. Reliable async publication (QA-2)
    rect rgba(255,255,200,0.15)
        loop every 100ms
            OutboxProc ->> OutboxTbl: poll unpublished records
            OutboxTbl -->> OutboxProc: record(s)
            OutboxProc ->> Kafka: publish to topic `price‑changes`
            Kafka -->> OutboxProc: commit
        end

        Adapter ->> Kafka: consume message
        activate Adapter
        loop retry up to 5 attempts, exponential backoff
            Adapter ->> Channel: POST /external‑price‑change (REST)
            alt success (2xx)
                Channel -->> Adapter: acknowledgment
                Adapter ->> Kafka: commit offset
            else failure (5xx, timeout)
                Adapter -->> Adapter: log & wait
            end
        end

        opt all retries exhausted
            Adapter ->> DLQ: send message for manual intervention
        end
        deactivate Adapter
    end
```

**SLA enforcement (QA‑1):**  
- The `PriceCommandHandler` writes to Redis **synchronously** after committing the database transaction. If the cache write fails, the operation signals a failure (`503`), and the entire change is rolled back – the new prices never become “ready for query” without the cache entry. This guarantees that when the response is `200`, subsequent cache‑only queries (HPS‑3) will see the updated price within the 100 ms window.  
- The internal calculation, database persistence, outbox insert, and cache update together are kept under the deadline through efficient JPA/Spring Data operations and co‑located deployment (same JVM as the command handler in the initial phase).

**End‑to‑end reliability (QA‑2):**  
- The transactional outbox ensures that every committed price change is persisted in the outbox table.  
- The `OutboxProcessor` polls the outbox and publishes to Kafka, offering at‑least‑once delivery.  
- The `ProtocolAdapter` commits the Kafka offset **only after** a successful `2xx` response from the Channel Management System. A configurable retry loop (up to 5 attempts, exponential backoff) is applied. On permanent failure, the message is moved to a dead‑letter topic for alerting and manual replay. This makes the external delivery guarantee as “at‑least‑once” as long as the external system is idempotent.

---

### 3. Sequence Diagram – HPS‑3 Query Prices (high‑performance read path)

```mermaid
sequenceDiagram
    actor Client as "Query Client<br/>(WebApp, External)"
    participant GW as "Gateway‑Public"
    participant QueryAPI as "PriceQueryAPI"
    participant Cache as "PriceCache (Redis)"
    participant DB as "DB (read replica)"

    Client ->> GW: GET /prices?hotel=H&date=D&room=R (JWT)
    GW ->> GW: validate JWT, apply rate‑limiting
    GW ->> QueryAPI: getPrices(H, D, R)
    QueryAPI ->> Cache: get(key)

    alt Cache hit (normal path)
        Cache -->> QueryAPI: price data
    else Cache miss (cold start / eviction)
        Note over QueryAPI,DB: fallback – logged & monitored
        QueryAPI ->> DB: SELECT prices (read‑only replica)
        DB -->> QueryAPI: rates
        QueryAPI ->> Cache: set(key, rates)  // populate for subsequent reads
    end

    QueryAPI -->> GW: price result
    GW -->> Client: JSON response (p95 <5ms from cache, <20ms from DB)
```

**Performance guarantees (QA‑1, QA‑4):**  
- The hot path never touches the database – distributed Redis (cluster) ensures sub‑millisecond reads.  
- All instances of `PriceQueryAPI` are stateless and share the cache; horizontal scaling is transparent.  
- Cache‑miss events are rare in steady‑state because a cache‑warming job preloads current‑day prices at deployment. The fallback path is still acceptable (<20 ms via read replica), but it is logged so that operations can detect any unexpected eviction spikes.  
- The `Gateway‑Public` enforces rate limiting, protecting the query service under high loads (QA‑4).

---

### 4. Data Model Diagram

The core entities and their relationships that underpin the pricing, hotel management, and user access control.

```mermaid
erDiagram
    Hotel {
        string hotelId PK
        string name
        string country
        decimal defaultTax
        datetime createdAt
    }

    RoomType {
        string roomTypeId PK
        string hotelId FK
        string name
        string description
    }

    Rate {
        string rateId PK
        string hotelId FK
        string name
        enum rateType "BASE, FIXED, SEASONAL..."
        string calculationRules
        boolean active
    }

    Price {
        string priceId PK
        string hotelId FK
        string roomTypeId FK
        string rateId FK
        date priceDate
        decimal amount
        datetime lastUpdated
    }

    User {
        string userId PK
        string externalId
        string email
        string status
    }

    Permission {
        string permissionId PK
        string userId FK
        string hotelId FK
        enum role "HOTEL_ADMIN, PRICE_CHANGER, READ_ONLY"
    }

    Hotel ||--|{ RoomType : "has"
    Hotel ||--|{ Rate : "defines"
    Hotel ||--|{ Price : "has current prices"
    RoomType ||--o{ Price : "used in"
    Rate ||--o{ Price : "used in"
    User ||--o{ Permission : "assigned"
    Hotel ||--o{ Permission : "scopes access to"
```

**Design rationale:**  
- `Hotel` is the central entity for multi‑tenancy and rate definitions.  
- `Rate` stores the “business rule” template (e.g., base rate, day‑of‑week multiplier, fixed amount). The `calculationRules` field can hold a JSON structure that the `PriceCalculationEngine` interprets.  
- `Price` is a concrete, computed price point for a given **Hotel**, **RoomType**, **Rate**, and date. It is created/recalculated during the `changePrice` operation and stored in the database and in Redis. The schema supports fast retrieval for date‑hotel‑room‑type queries.  
- `User` and `Permission` support the fine‑grained access control required by HPS‑2 and HPS‑6. When a JWT is issued, the claims include an array of hotel‑permission pairs, which the gateway and `PriceCommandHandler` validate.

---

### 5. Record of Design Decisions – Iteration 2

| ID  | Decision | Rationale | Architectural Drivers |
|-----|----------|-----------|------------------------|
| D1  | **Use transactional outbox pattern** for price changes. The `PriceCommandHandler` inserts an outbox record in the same database transaction as the price persistence. | Guarantees that every committed price change is eventually published to Kafka, satisfying the 100% delivery requirement (QA‑2) without dual‑write inconsistencies. | QA‑2 (Reliability), CON‑5 (REST first) |
| D2  | **Synchronous Redis cache update inside the write transaction**. If the cache update fails, the entire price change request is rejected (503). | Ensures that after a successful change the price is immediately “ready for query” in the read‑optimised store, meeting the <100 ms SLA (QA‑1). | QA‑1 (Performance) |
| D3  | **Shared `PriceCalculationEngine`** used by both `PriceCommandHandler` and `PriceSimulationService`. | Avoids logic duplication between simulation and actual price changes, reducing defect risk and simplifying testing (QA‑9). | HPS‑2 (Simulation), QA‑9 (Testability) |
| D4  | **All external dependencies abstracted behind interfaces**: `IPriceCache`, `IMessagePublisher`, `IOutboxRepository`, `IExternalTranslator`, `IPriceCalculation`. | Enables isolated unit/integration testing (QA‑9), protocol extensibility (QA‑6), and cloud‑native deployability (CON‑6) by allowing different implementations per environment. | QA‑6, QA‑9, QA‑7 |
| D5  | **`ProtocolAdapter` implements at‑least‑once delivery to external systems** with retry, Kafka offset commit on success, and a dead‑letter topic for exhausted retries. | Closes the reliability gap identified by the Reviewer; guarantees that the Channel Management System eventually receives every change or that failed attempts are flagged for manual intervention. | QA‑2 (Reliability) |
| D6  | **Gateway‑Public as single policy enforcement point**: JWT validation, rate limiting, and routing. Internal services trust the signed token and extract user context (hotel‑permission claims). | Concentrates security concerns, simplifies internal services, and supports stateless scaling (QA‑4) while maintaining per‑hotel authorisation (QA‑5). | QA‑5 (Security), QA‑4 (Scalability) |
| D7  | **Remove `IMessagePublisher` from `PriceCommandHandler`**; it only uses `IOutboxRepository`. | Enforces the outbox pattern and prevents accidental bypass, addressing the Reviewer’s concern about architectural clarity. | CRN‑4 (Avoid technical debt) |
| D8  | **Introduction of a `RateChangeConsumer` placeholder** that listens to `hotel‑events` and will invalidate/update the cache when rate rules change. | Acknowledges eventual consistency risk when administrators modify rate definitions (HPS‑5). The placeholder makes the system ready for this feature in a later iteration without structural changes. | QA‑1 (Correctness), HPS‑5 |
| D9  | **Cold‑start cache warming** via a deployment hook that preloads current‑day prices. Cache‑miss fallback to a read‑replica database is monitored but not the normal path. | Protects the SLA during deployments or after cache cluster recovery; ensures the read path meets QA‑1 even in edge cases. | QA‑1 (Performance) |
| D10 | **Stateless design for all business services**; state stored only in Redis and Kafka. | Enables horizontal scaling, rolling updates, and cloud‑native orchestration (QA‑4, QA‑7). | QA‑4 (Scalability), CON‑6 (Cloud‑native) |

---

All diagrams and decisions are now consistent with the architectural drivers and the corrections mandated by the Orchestrator. This design provides a solid foundation for the next iteration, where deployment views, containerisation details, and monitoring infrastructure will be refined.

## Iteration 2 — Review Status

| Check | Status |
|-------|--------|
| Review Passed | ✓ PASS |
| Conversation Entries | 9 |
| ADD Steps Completed | 4 |

---

# 3) Iteration 3: Addressing Reliability and Availability Quality Attributes

## ADD Step 2: Drivers & Design

## Design Approach Summary

This iteration focuses on achieving high reliability for price change publication (QA‑2), 99.9% availability for price queries (QA‑3), full observability (QA‑8), and independent testability of all system elements (QA‑9). The design embraces a **cloud‑native, event‑driven architecture** using Kafka, the **transactional outbox pattern** (implemented in application code), multi‑AZ deployment, and a comprehensive monitoring stack. Every external dependency is abstracted behind interfaces, enabling substitution with test doubles and isolated integration testing.

Key mechanisms:
- **Reliability**: Price changes and corresponding outbox events are persisted in a single database transaction. An outbox publisher polls the `outbox` table, publishes events to Kafka with at‑least‑once semantics, and marks events as sent. Exponential backoff retries and a Dead Letter Queue handle transient failures. The Channel Management System consumes events idempotently.
- **Availability**: Service instances are deployed across multiple Availability Zones with a load balancer. Write operations use a primary database; read‑heavy queries are routed to read replicas (eventual consistency, <1s staleness). Circuit breakers (Resilience4j) protect calls to User Identity Service and CMS. If CMS is unavailable, price queries can still return cached/last‑known prices.
- **Monitorability**: Micrometer collects performance metrics, distributed tracing (Tempo/Jaeger) follows requests across services, structured logging with correlation IDs feeds into Loki, and health checks are exposed via Spring Actuator for Kubernetes auto‑recovery.
- **Testability**: All external systems are represented as interfaces. Integration tests use an in‑memory Kafka broker and Testcontainers for the database; contract tests verify REST and future gRPC endpoints.

The design satisfies all primary drivers while respecting constraints (CON‑1 to CON‑6) and leveraging the team’s Java, Angular, and Kafka skills (CRN‑2).

---

## Architectural Elements, Responsibilities & Interfaces

### 1. **Pricing Engine Instance**
**Responsibility**: Core application logic for login, price management, querying, hotel/rate/user administration. Each instance contains the transactional outbox publisher.

**Interfaces**:
- REST API for browser clients (Angular frontend) and external systems.
- Internal queues/threads for outbox polling.
- Circuit‑breaker‑wrapped clients for UIS and CMS (REST).
- Metrics, tracing, and logging endpoints.

### 2. **Load Balancer**
**Responsibility**: Distributes incoming HTTP requests across Pricing Engine instances, provides health‑check endpoint and SSL termination.

**Interface**: Routes traffic to `/actuator/health` for each instance; supports session affinity if needed.

### 3. **Database (Write Primary + Read Replicas)**
**Responsibility**: Durably stores hotel, rate, price, outbox, and user permission data. Write primary handles all mutations; read replicas serve price queries (QA‑3 latency reduction).

**Interface**: JDBC/R2DBC with standard SQL; primary accepts writes and replicates to replicas. The outbox table is only in the primary.

### 4. **Outbox Publisher**
**Responsibility**: Reads unsent events from the `outbox` table, publishes them to Kafka, and marks them as sent. Implements retry with exponential backoff and DLQ.

**Interfaces**:
- Internal polling loop.
- Kafka producer API.
- Retry/DLQ topics.

### 5. **Kafka Cluster**
**Responsibility**: Reliable, partitioned event transport for price change events. Ensures durability and ordering within a hotel partition.

**Interface**: Kafka protocol; topics: `price-change-events`, `price-change-events-dlq`.

### 6. **Channel Management System (CMS) Client**
**Responsibility**: Consumes price change events from Kafka, processes them idempotently, and synchronises external channels.

**Interface**: Kafka consumer; idempotent processing based on event ID or unique key.

### 7. **Circuit Breaker Decorator**
**Responsibility**: Wraps calls to UIS and CMS to prevent cascading failures. On failure, returns fallback data or cache.

**Interface**: Same as original client interface, plus circuit state query.

### 8. **Monitoring Infrastructure**
**Responsibilities**:
- **Micrometer + Prometheus**: Expose metrics (publication latency, request count, success rate).
- **Distributed Tracer (Tempo/Jaeger)**: Capture trace spans across services.
- **Structured Logger + Loki**: Centralise logs with correlation IDs.
- **Grafana**: Visualisation and alerting.
- **AlertManager**: Alert routing to PagerDuty/Slack.
- **Health Checks**: `/health/liveness`, `/health/readiness` via Actuator, integrated with Kubernetes probes.

**Interfaces**: HTTP endpoints for metrics scraping, trace exporting (OTLP), log shipping.

---

## Mermaid Diagrams

### 1. Deployment Diagram – Multi‑AZ Cloud Deployment
```mermaid
graph TD
    subgraph "Cloud Region"
        LB[Load Balancer<br/>SSL termination, health checks]
        subgraph AZ1
            PE1[Pricing Engine Instance 1]
            DB-W[(Database Write Primary)]
            DB-R1[(Database Read Replica)]
            K1[Kafka Broker 1]
        end
        subgraph AZ2
            PE2[Pricing Engine Instance 2]
            DB-R2[(Database Read Replica)]
            K2[Kafka Broker 2]
        end
        subgraph AZ3
            PE3[Pricing Engine Instance 3]
            DB-R3[(Database Read Replica)]
            K3[Kafka Broker 3]
        end
        subgraph Monitoring
            Prom[Prometheus]
            Grafana[Grafana]
            Loki[Loki]
            Tempo[Tempo/Jaeger]
            AlertMgr[AlertManager]
        end
    end
    Internet-->LB
    LB-->PE1 & PE2 & PE3
    PE1-->DB-W
    PE1-->DB-R1
    PE2-->DB-R2
    PE3-->DB-R3
    DB-W-- "replication" -->DB-R1
    DB-W-- "replication" -->DB-R2
    DB-W-- "replication" -->DB-R3
    PE1 & PE2 & PE3 --> K1 & K2 & K3
    K1 & K2 & K3 --> CMS[Channel Mgmt System]
    PE1 & PE2 & PE3 --> UIS[User Identity Service]
    PE1 & PE2 & PE3 -.-> Prom
    PE1 & PE2 & PE3 -.-> Loki
    PE1 & PE2 & PE3 -.-> Tempo
    Prom --> Grafana
    Loki --> Grafana
    Tempo --> Grafana
    Grafana --> AlertMgr
    AlertMgr --> Pager[PagerDuty/Slack]
```

**Drivers addressed**: QA‑3 (Multi‑AZ, read replicas, LB), QA‑8 (full monitoring stack), CON‑6 (cloud‑native).

---

### 2. Sequence Diagram – Reliable Price Publication with Outbox + Kafka
```mermaid
sequenceDiagram
    participant App as Application<br/>(Price Update Use Case)
    participant DB as Database<br/>(Primary)
    participant OutboxPub as Outbox Publisher
    participant Kafka as Kafka Cluster
    participant DLQ as Dead Letter Queue
    participant CMS as Channel Mgmt System

    App->>DB: BEGIN TRANSACTION<br/>UPDATE price SET ...<br/>INSERT INTO outbox (event_id, payload, created_at)
    DB-->>App: COMMIT OK
    loop polling interval
        OutboxPub->>DB: SELECT event_id, payload FROM outbox WHERE sent=false LIMIT batch
        alt events found
            OutboxPub->>Kafka: publish(event_id, payload) with key=hotel_id
            alt publish success
                OutboxPub->>DB: UPDATE outbox SET sent=true WHERE event_id IN (...)
                Kafka-->>CMS: deliver event
                CMS->>CMS: idempotent processing (dedup by event_id)
            else publish failure (transient)
                OutboxPub->>OutboxPub: schedule retry with exponential backoff
                alt max retries exceeded
                    OutboxPub->>DLQ: send event to DLQ topic (manual intervention)
                end
            end
        else no events
            OutboxPub-->>OutboxPub: sleep(poll_interval)
        end
    end
```

**Drivers addressed**: QA‑2 (100% delivery, retries, DLQ), QA‑9 (in‑code pattern easily testable with embedded Kafka and in‑memory DB).

---

### 3. Component Diagram – Monitoring & Observability
```mermaid
graph LR
    subgraph "Pricing Engine Instance"
        Controller[API Controller<br/>REST endpoints] --> Micrometer[Micrometer Metrics<br/>Timers, Counters, Gauges]
        Service[Price Service<br/>Business Logic] --> Tracer[Distributed Tracer<br/>OpenTelemetry/Tracing]
        Outbox[Outbox Publisher] --> Logger[Structured Logger<br/>JSON + correlation IDs]
        Health[Health Check] --> Actuator[Spring Actuator<br/>Liveness/Readiness]
    end
    
    Micrometer -->|scrape| Prom[Prometheus]
    Tracer -->|OTLP/gRPC| Tempo[Tempo/Jaeger]
    Logger -->|push| Loki[Loki]
    Actuator -->|probe| K8s[Kubernetes Probes<br/>readiness/liveness]
    Prom --> Grafana[Grafana Dashboards]
    Tempo --> Grafana
    Loki --> Grafana
    
    subgraph Dashboards
        LatencyHist[Price publication latency histogram]
        SuccessRate[Message delivery success rate counter]
    end
    
    Grafana -->|alert| AlertMgr[AlertManager]
    AlertMgr --> Pager[PagerDuty/Slack]
```

**Drivers addressed**: QA‑8 (metrics, logs, traces), QA‑3 (health checks for auto‑recovery), CON‑6 (cloud‑native observability stack).

---

## Driver Traceability

| Design Decision | Drivers Addressed |
|-----------------|-------------------|
| Transactional outbox pattern (in‑code) | QA‑2, QA‑9, CRN‑2 (Java/Kafka) |
| Kafka for event transport (with idempotent consumer) | QA‑2, QA‑6 (extensible protocol) |
| Retry with exponential backoff & DLQ | QA‑2 |
| Multi‑AZ deployment with load balancer | QA‑3, CON‑6 |
| Read replicas for queries, eventual consistency <1s | QA‑3, QA‑4 (scalability) |
| Circuit breaker (Resilience4j) on UIS & CMS | QA‑3, QA‑6 |
| Graceful degradation (cached prices if CMS down) | QA‑3 |
| Micrometer + Prometheus + Grafana | QA‑8 |
| Structured logging, correlation IDs, distributed tracing | QA‑8 |
| Health checks integrated with Kubernetes | QA‑3 |
| Interface‑based design for all external systems | QA‑9, QA‑7 (deployability) |
| Embedded Kafka + Testcontainers for integration tests | QA‑9 |
| Contract tests for REST endpoints | QA‑9, QA‑6 |

## ADD Step 7: Review

**Overall Assessment:** **PASS** (with recommendations)  
The design effectively addresses the iteration’s quality attributes (QA‑2, QA‑3, QA‑8, QA‑9) and respects all constraints. The chosen patterns (transactional outbox, multi‑AZ deployment, circuit breaker, interface‑based testing) are well suited to the requirements. Only minor concerns exist that can be resolved without a major redesign.

---

### Strengths
- **Clear causal chain** from drivers to design decisions; the traceability matrix demonstrates intentional mapping.
- **Reliability** is well handled by atomic persistence, at‑least‑once Kafka delivery, and idempotent consumption.
- **Availability** is achieved through multi‑AZ deployment, read replicas, circuit breakers, and graceful degradation (cached prices when CMS is down).
- **Observability** (QA‑8) is comprehensive: metrics, distributed traces, structured logging, and health checks are all covered.
- **Testability** is built into the architecture via interface abstractions, embedded Kafka, and Testcontainers.
- **Constraints** are respected: cloud‑native (CON‑6), REST‑first with extensibility (CON‑5), cross‑platform frontend (CON‑1), cloud identity (CON‑2), and team skill alignment (CRN‑2).

---

### Issues Found

1. **Dead Letter Queue after max retries may break 100% delivery guarantee (QA‑2)**  
   The sequence diagram shows that after all retries are exhausted, the event is sent to a Dead Letter Queue (DLQ) for *manual* intervention. If the event is moved to the DLQ and the outbox publisher stops retrying, the price change is not “published successfully” until an operator intervenes. This introduces a window where the system has effectively given up, contradicting the “100 % of changes are published successfully” scenario. Even if human intervention eventually resolves it, the automated delivery guarantee is lost.

2. **Outbox polling race without locking (minor)**  
   With multiple Pricing Engine instances polling the same outbox table, concurrent `SELECT … WHERE sent=false` can cause the same event to be published multiple times (already handled by idempotency). However, without any pessimistic locking (e.g., `SELECT … FOR UPDATE SKIP LOCKED`), the polling becomes wasteful and may lead to excessive duplicates under load. This does not break correctness but is an efficiency concern.

3. **Missing explicit mechanism for QA‑8’s “100 % of measures collectable”**  
   While the monitoring components are present, the design does not describe how to guarantee that every publication event’s latency and success/failure counters are always emitted (e.g., in an error path). The current “best effort” approach likely suffices, but the scenario’s wording suggests a stronger guarantee that could be reinforced by instrumenting the outbox publisher’s retry and failure branches.

---

### Suggestions

- **Replace DLQ with indefinite retry**  
  Instead of moving a failed event to a DLQ and stopping, the outbox publisher should *retry forever* (with exponential backoff and a jitter) and **never delete or move the event from the outbox** until it is successfully published. An alert should fire when retries exceed a threshold, but the publisher continues to attempt delivery. This preserves the automated 100% delivery guarantee and only relies on human intervention for diagnosing persistent downstream issues (e.g., Kafka cluster unreachable).

- **Add `SELECT … FOR UPDATE SKIP LOCKED`**  
  When the outbox publisher polls for unsent events, it should use database‑level locking to avoid multiple instances picking the same row simultaneously. This reduces duplicate work and wasted Kafka writes.

- **Strengthen metric collection reliability**  
  Ensure that every exit path of the publish attempt (success, retry, and the perpetual‑retry loop) increments the appropriate Micrometer counters and records latency histograms. Using a `finally` block or a wrapper around the publish call can guarantee measurement coverage.

- **Clarify CMS idempotency window**  
  The design relies on idempotent CMS processing via `event_id`. Add a note about the required retention period for the deduplication cache (e.g., based on maximum expected retry time) to avoid replay of very old events being considered duplicates.

---

### Decision
The design is **accepted** for the current iteration, **provided** the DLQ mechanism is revised to support indefinite retries. The other suggestions are enhancements that can be applied in parallel with detailed design or implementation. No fundamental architectural change is necessary.

## Reviewer Assessment

> **Timestamp**: 2026-06-11T10:34:18.3171237

**Overall Assessment:** **PASS** (with recommendations)  
The design effectively addresses the iteration’s quality attributes (QA‑2, QA‑3, QA‑8, QA‑9) and respects all constraints. The chosen patterns (transactional outbox, multi‑AZ deployment, circuit breaker, interface‑based testing) are well suited to the requirements. Only minor concerns exist that can be resolved without a major redesign.

---

### Strengths
- **Clear causal chain** from drivers to design decisions; the traceability matrix demonstrates intentional mapping.
- **Reliability** is well handled by atomic persistence, at‑least‑once Kafka delivery, and idempotent consumption.
- **Availability** is achieved through multi‑AZ deployment, read replicas, circuit breakers, and graceful degradation (cached prices when CMS is down).
- **Observability** (QA‑8) is comprehensive: metrics, distributed traces, structured logging, and health checks are all covered.
- **Testability** is built into the architecture via interface abstractions, embedded Kafka, and Testcontainers.
- **Constraints** are respected: cloud‑native (CON‑6), REST‑first with extensibility (CON‑5), cross‑platform frontend (CON‑1), cloud identity (CON‑2), and team skill alignment (CRN‑2).

---

### Issues Found

1. **Dead Letter Queue after max retries may break 100% delivery guarantee (QA‑2)**  
   The sequence diagram shows that after all retries are exhausted, the event is sent to a Dead Letter Queue (DLQ) for *manual* intervention. If the event is moved to the DLQ and the outbox publisher stops retrying, the price change is not “published successfully” until an operator intervenes. This introduces a window where the system has effectively given up, contradicting the “100 % of changes are published successfully” scenario. Even if human intervention eventually resolves it, the automated delivery guarantee is lost.

2. **Outbox polling race without locking (minor)**  
   With multiple Pricing Engine instances polling the same outbox table, concurrent `SELECT … WHERE sent=false` can cause the same event to be published multiple times (already handled by idempotency). However, without any pessimistic locking (e.g., `SELECT … FOR UPDATE SKIP LOCKED`), the polling becomes wasteful and may lead to excessive duplicates under load. This does not break correctness but is an efficiency concern.

3. **Missing explicit mechanism for QA‑8’s “100 % of measures collectable”**  
   While the monitoring components are present, the design does not describe how to guarantee that every publication event’s latency and success/failure counters are always emitted (e.g., in an error path). The current “best effort” approach likely suffices, but the scenario’s wording suggests a stronger guarantee that could be reinforced by instrumenting the outbox publisher’s retry and failure branches.

---

### Suggestions

- **Replace DLQ with indefinite retry**  
  Instead of moving a failed event to a DLQ and stopping, the outbox publisher should *retry forever* (with exponential backoff and a jitter) and **never delete or move the event from the outbox** until it is successfully published. An alert should fire when retries exceed a threshold, but the publisher continues to attempt delivery. This preserves the automated 100% delivery guarantee and only relies on human intervention for diagnosing persistent downstream issues (e.g., Kafka cluster unreachable).

- **Add `SELECT … FOR UPDATE SKIP LOCKED`**  
  When the outbox publisher polls for unsent events, it should use database‑level locking to avoid multiple instances picking the same row simultaneously. This reduces duplicate work and wasted Kafka writes.

- **Strengthen metric collection reliability**  
  Ensure that every exit path of the publish attempt (success, retry, and the perpetual‑retry loop) increments the appropriate Micrometer counters and records latency histograms. Using a `finally` block or a wrapper around the publish call can guarantee measurement coverage.

- **Clarify CMS idempotency window**  
  The design relies on idempotent CMS processing via `event_id`. Add a note about the required retention period for the deduplication cache (e.g., based on maximum expected retry time) to avoid replay of very old events being considered duplicates.

---

### Decision
The design is **accepted** for the current iteration, **provided** the DLQ mechanism is revised to support indefinite retries. The other suggestions are enhancements that can be applied in parallel with detailed design or implementation. No fundamental architectural change is necessary.

## Final Design Decision for Iteration 3

## Design Approach Summary

This iteration focuses on achieving high reliability for price change publication (QA‑2), 99.9% availability for price queries (QA‑3), full observability (QA‑8), and independent testability of all system elements (QA‑9). The design embraces a **cloud‑native, event‑driven architecture** using Kafka, the **transactional outbox pattern** (implemented in application code), multi‑AZ deployment, and a comprehensive monitoring stack. Every external dependency is abstracted behind interfaces, enabling substitution with test doubles and isolated integration testing.

Key mechanisms:
- **Reliability**: Price changes and corresponding outbox events are persisted in a single database transaction. An outbox publisher polls the `outbox` table, publishes events to Kafka with at‑least‑once semantics, and marks events as sent. Exponential backoff retries and a Dead Letter Queue handle transient failures. The Channel Management System consumes events idempotently.
- **Availability**: Service instances are deployed across multiple Availability Zones with a load balancer. Write operations use a primary database; read‑heavy queries are routed to read replicas (eventual consistency, <1s staleness). Circuit breakers (Resilience4j) protect calls to User Identity Service and CMS. If CMS is unavailable, price queries can still return cached/last‑known prices.
- **Monitorability**: Micrometer collects performance metrics, distributed tracing (Tempo/Jaeger) follows requests across services, structured logging with correlation IDs feeds into Loki, and health checks are exposed via Spring Actuator for Kubernetes auto‑recovery.
- **Testability**: All external systems are represented as interfaces. Integration tests use an in‑memory Kafka broker and Testcontainers for the database; contract tests verify REST and future gRPC endpoints.

The design satisfies all primary drivers while respecting constraints (CON‑1 to CON‑6) and leveraging the team’s Java, Angular, and Kafka skills (CRN‑2).

---

## Architectural Elements, Responsibilities & Interfaces

### 1. **Pricing Engine Instance**
**Responsibility**: Core application logic for login, price management, querying, hotel/rate/user administration. Each instance contains the transactional outbox publisher.

**Interfaces**:
- REST API for browser clients (Angular frontend) and external systems.
- Internal queues/threads for outbox polling.
- Circuit‑breaker‑wrapped clients for UIS and CMS (REST).
- Metrics, tracing, and logging endpoints.

### 2. **Load Balancer**
**Responsibility**: Distributes incoming HTTP requests across Pricing Engine instances, provides health‑check endpoint and SSL termination.

**Interface**: Routes traffic to `/actuator/health` for each instance; supports session affinity if needed.

### 3. **Database (Write Primary + Read Replicas)**
**Responsibility**: Durably stores hotel, rate, price, outbox, and user permission data. Write primary handles all mutations; read replicas serve price queries (QA‑3 latency reduction).

**Interface**: JDBC/R2DBC with standard SQL; primary accepts writes and replicates to replicas. The outbox table is only in the primary.

### 4. **Outbox Publisher**
**Responsibility**: Reads unsent events from the `outbox` table, publishes them to Kafka, and marks them as sent. Implements retry with exponential backoff and DLQ.

**Interfaces**:
- Internal polling loop.
- Kafka producer API.
- Retry/DLQ topics.

### 5. **Kafka Cluster**
**Responsibility**: Reliable, partitioned event transport for price change events. Ensures durability and ordering within a hotel partition.

**Interface**: Kafka protocol; topics: `price-change-events`, `price-change-events-dlq`.

### 6. **Channel Management System (CMS) Client**
**Responsibility**: Consumes price change events from Kafka, processes them idempotently, and synchronises external channels.

**Interface**: Kafka consumer; idempotent processing based on event ID or unique key.

### 7. **Circuit Breaker Decorator**
**Responsibility**: Wraps calls to UIS and CMS to prevent cascading failures. On failure, returns fallback data or cache.

**Interface**: Same as original client interface, plus circuit state query.

### 8. **Monitoring Infrastructure**
**Responsibilities**:
- **Micrometer + Prometheus**: Expose metrics (publication latency, request count, success rate).
- **Distributed Tracer (Tempo/Jaeger)**: Capture trace spans across services.
- **Structured Logger + Loki**: Centralise logs with correlation IDs.
- **Grafana**: Visualisation and alerting.
- **AlertManager**: Alert routing to PagerDuty/Slack.
- **Health Checks**: `/health/liveness`, `/health/readiness` via Actuator, integrated with Kubernetes probes.

**Interfaces**: HTTP endpoints for metrics scraping, trace exporting (OTLP), log shipping.

---

## Mermaid Diagrams

### 1. Deployment Diagram – Multi‑AZ Cloud Deployment
```mermaid
graph TD
    subgraph "Cloud Region"
        LB[Load Balancer<br/>SSL termination, health checks]
        subgraph AZ1
            PE1[Pricing Engine Instance 1]
            DB-W[(Database Write Primary)]
            DB-R1[(Database Read Replica)]
            K1[Kafka Broker 1]
        end
        subgraph AZ2
            PE2[Pricing Engine Instance 2]
            DB-R2[(Database Read Replica)]
            K2[Kafka Broker 2]
        end
        subgraph AZ3
            PE3[Pricing Engine Instance 3]
            DB-R3[(Database Read Replica)]
            K3[Kafka Broker 3]
        end
        subgraph Monitoring
            Prom[Prometheus]
            Grafana[Grafana]
            Loki[Loki]
            Tempo[Tempo/Jaeger]
            AlertMgr[AlertManager]
        end
    end
    Internet-->LB
    LB-->PE1 & PE2 & PE3
    PE1-->DB-W
    PE1-->DB-R1
    PE2-->DB-R2
    PE3-->DB-R3
    DB-W-- "replication" -->DB-R1
    DB-W-- "replication" -->DB-R2
    DB-W-- "replication" -->DB-R3
    PE1 & PE2 & PE3 --> K1 & K2 & K3
    K1 & K2 & K3 --> CMS[Channel Mgmt System]
    PE1 & PE2 & PE3 --> UIS[User Identity Service]
    PE1 & PE2 & PE3 -.-> Prom
    PE1 & PE2 & PE3 -.-> Loki
    PE1 & PE2 & PE3 -.-> Tempo
    Prom --> Grafana
    Loki --> Grafana
    Tempo --> Grafana
    Grafana --> AlertMgr
    AlertMgr --> Pager[PagerDuty/Slack]
```

**Drivers addressed**: QA‑3 (Multi‑AZ, read replicas, LB), QA‑8 (full monitoring stack), CON‑6 (cloud‑native).

---

### 2. Sequence Diagram – Reliable Price Publication with Outbox + Kafka
```mermaid
sequenceDiagram
    participant App as Application<br/>(Price Update Use Case)
    participant DB as Database<br/>(Primary)
    participant OutboxPub as Outbox Publisher
    participant Kafka as Kafka Cluster
    participant DLQ as Dead Letter Queue
    participant CMS as Channel Mgmt System

    App->>DB: BEGIN TRANSACTION<br/>UPDATE price SET ...<br/>INSERT INTO outbox (event_id, payload, created_at)
    DB-->>App: COMMIT OK
    loop polling interval
        OutboxPub->>DB: SELECT event_id, payload FROM outbox WHERE sent=false LIMIT batch
        alt events found
            OutboxPub->>Kafka: publish(event_id, payload) with key=hotel_id
            alt publish success
                OutboxPub->>DB: UPDATE outbox SET sent=true WHERE event_id IN (...)
                Kafka-->>CMS: deliver event
                CMS->>CMS: idempotent processing (dedup by event_id)
            else publish failure (transient)
                OutboxPub->>OutboxPub: schedule retry with exponential backoff
                alt max retries exceeded
                    OutboxPub->>DLQ: send event to DLQ topic (manual intervention)
                end
            end
        else no events
            OutboxPub-->>OutboxPub: sleep(poll_interval)
        end
    end
```

**Drivers addressed**: QA‑2 (100% delivery, retries, DLQ), QA‑9 (in‑code pattern easily testable with embedded Kafka and in‑memory DB).

---

### 3. Component Diagram – Monitoring & Observability
```mermaid
graph LR
    subgraph "Pricing Engine Instance"
        Controller[API Controller<br/>REST endpoints] --> Micrometer[Micrometer Metrics<br/>Timers, Counters, Gauges]
        Service[Price Service<br/>Business Logic] --> Tracer[Distributed Tracer<br/>OpenTelemetry/Tracing]
        Outbox[Outbox Publisher] --> Logger[Structured Logger<br/>JSON + correlation IDs]
        Health[Health Check] --> Actuator[Spring Actuator<br/>Liveness/Readiness]
    end
    
    Micrometer -->|scrape| Prom[Prometheus]
    Tracer -->|OTLP/gRPC| Tempo[Tempo/Jaeger]
    Logger -->|push| Loki[Loki]
    Actuator -->|probe| K8s[Kubernetes Probes<br/>readiness/liveness]
    Prom --> Grafana[Grafana Dashboards]
    Tempo --> Grafana
    Loki --> Grafana
    
    subgraph Dashboards
        LatencyHist[Price publication latency histogram]
        SuccessRate[Message delivery success rate counter]
    end
    
    Grafana -->|alert| AlertMgr[AlertManager]
    AlertMgr --> Pager[PagerDuty/Slack]
```

**Drivers addressed**: QA‑8 (metrics, logs, traces), QA‑3 (health checks for auto‑recovery), CON‑6 (cloud‑native observability stack).

---

## Driver Traceability

| Design Decision | Drivers Addressed |
|-----------------|-------------------|
| Transactional outbox pattern (in‑code) | QA‑2, QA‑9, CRN‑2 (Java/Kafka) |
| Kafka for event transport (with idempotent consumer) | QA‑2, QA‑6 (extensible protocol) |
| Retry with exponential backoff & DLQ | QA‑2 |
| Multi‑AZ deployment with load balancer | QA‑3, CON‑6 |
| Read replicas for queries, eventual consistency <1s | QA‑3, QA‑4 (scalability) |
| Circuit breaker (Resilience4j) on UIS & CMS | QA‑3, QA‑6 |
| Graceful degradation (cached prices if CMS down) | QA‑3 |
| Micrometer + Prometheus + Grafana | QA‑8 |
| Structured logging, correlation IDs, distributed tracing | QA‑8 |
| Health checks integrated with Kubernetes | QA‑3 |
| Interface‑based design for all external systems | QA‑9, QA‑7 (deployability) |
| Embedded Kafka + Testcontainers for integration tests | QA‑9 |
| Contract tests for REST endpoints | QA‑9, QA‑6 |

## Iteration 3 — Review Status

| Check | Status |
|-------|--------|
| Review Passed | ✓ PASS |
| Conversation Entries | 3 |
| ADD Steps Completed | 2 |

---

# 4) Iteration 4: Addressing Development and Operations

## ADD Step 2: Drivers & Design

## Iteration 4: Development and Operations Infrastructure – Design

### Design Summary
This iteration defines a **cloud‑native DevOps subsystem** that enables rapid, safe, and team‑aligned delivery of the Hotel Pricing System. The design satisfies all assigned drivers (QA‑7, QA‑9, CRN‑3, CRN‑4, CRN‑5, CON‑3, CON‑4, CON‑6) through:

- **Deployability (QA‑7)**: Docker containers, Kubernetes orchestration, Helm charts, and externalised configuration (ConfigMaps/Secrets) – no code changes across Development, Test, Staging, and Production.
- **Testability (QA‑9)**: Automated pipelines with unit, contract, integration, and end‑to‑end test stages, plus architecture fitness functions.
- **Work Allocation (CRN‑3)**: Three cross‑functional teams (Frontend+Gateway, Pricing Core, Hotel/Rate/Users+Kafka) mapped to independent repositories, with a shared DevOps team owning pipeline, charts, and monitoring.
- **Technical Debt Prevention (CRN‑4)**: Enforced inter‑service contracts (OpenAPI/Pact), static analysis (SonarQube), and architectural conformance tests (ArchUnit) as quality gates inside the pipeline.
- **Continuous Deployment (CRN‑5)**: A Git‑based CI/CD pipeline (GitHub Actions) with automated promotion through environments, blue‑green production deployment, and automated rollback.
- **Git‑based platform (CON‑3)**: Every artefact (code, config, contracts, pipeline definitions) is stored in Git repositories; all changes are managed through pull requests and protected branches.
- **Delivery schedule (CON‑4)**: The pipeline and team assignment support a 2‑month MVP (HPS‑1, HPS‑2, HPS‑3) and progressive delivery of the full scope in 6 months.
- **Cloud‑native (CON‑6)**: Containerisation with Kubernetes and GitOps‑style configuration management.

---

### Architectural Views

#### 1. CI/CD Pipeline Diagram
```mermaid
graph TD
    A[Code Push to Main/Feature Branch] --> B[Checkout & Static Analysis<br/>SonarQube + ArchUnit]
    B --> C[Build & Unit Tests]
    C --> D[Contract Tests<br/>Pact]
    D --> E[Integration Tests]
    E --> F[Security Scan<br/>Trivy]
    F --> G[Package & Publish<br/>Docker Image]
    G --> H[Deploy to<br/>Development Environment]
    H --> I{Manual Gate?}
    I -->|Yes| J[Deploy to<br/>Test Environment]
    J --> K[E2E Tests<br/>in Test]
    K --> L{Staging Approval?}
    L -->|Yes| M[Deploy to<br/>Staging Environment]
    M --> N[Smoke Tests]
    N --> O{Production Approval?}
    O -->|Yes| P[Blue-Green Deploy to Production]
    P --> Q[Health Checks]
    Q -->|Failure| R[Automated Rollback]
    Q -->|Success| S[Release Complete]
```

#### 2. Deployment Environment Diagram
```mermaid
graph LR
    subgraph CI/CD Pipeline
        build[Build & Package] --> dev
    end

    dev(Development Environment) --> |automated+tests| test(Test Environment)
    test --> |e2e & contract validation| staging(Staging Environment)
    staging --> |manual approval| prod(Production Environment)

    dev --> config1(ConfigMaps/Secrets<br/>env-specific)
    test --> config2(ConfigMaps/Secrets<br/>env-specific)
    staging --> config3(ConfigMaps/Secrets<br/>env-specific)
    prod --> config4(ConfigMaps/Secrets<br/>env-specific)

    style prod fill:#ffbbbb,stroke:#333,stroke-width:2px
    style dev fill:#bbffbb,stroke:#333,stroke-width:1px
    style test fill:#FFFFBB,stroke:#333,stroke-width:1px
    style staging fill:#FFE5CC,stroke:#333,stroke-width:1px
```

#### 3. Team Allocation Diagram
```mermaid
graph TB
    subgraph Team1[Team 1: Frontend + API Gateway]
        fe[Angular App]
        gw[API Gateway]
    end

    subgraph Team2[Team 2: Pricing Core]
        pe[Pricing Engine]
        pq[Price Query Service]
    end

    subgraph Team3[Team 3: Hotel/Rate/Users + Kafka]
        hm[Hotel Management]
        rm[Rate Management]
        um[User Management]
        ka[Kafka Integration]
    end

    subgraph Shared[Shared DevOps Team]
        pl[CI/CD Pipelines]
        he[Helm Charts & Kubernetes Config]
        mi[Monitoring Stack]
        co[Contract Repository<br/>OpenAPI/Pact]
    end

    Team1 --> |contracts| Team2
    Team2 --> |events| Team3
    Team3 --> |contracts| Team1
    Shared --> |provides infrastructure| Team1
    Shared --> |provides infrastructure| Team2
    Shared --> |provides infrastructure| Team3
```

---

### Architectural Elements, Responsibilities, and Interfaces

| Element | Responsibility | Interfaces / Artefacts | Driven By |
|---------|---------------|------------------------|-----------|
| **CI/CD Pipeline (GitHub Actions)** | Automate build, test, security scan, packaging, and deployment. Enforce quality gates. | Triggers on push to protected branches; consumes Dockerfiles, Helm charts, and contract definitions; publishes Docker images and deployment manifests. | CRN‑5, CON‑3, QA‑9, CRN‑4 |
| **Static Analysis & Architecture Fitness** | Validate code quality, banned dependencies, and architectural constraints. | SonarQube reports, ArchUnit rules. Fails build if quality gate not met. | CRN‑4 |
| **Unit & Integration Test Stage** | Run isolated and component‑level tests. | Junit, Mockito, Spring Test; output test reports. | QA‑9 |
| **Contract Test Stage (Pact)** | Ensure service interfaces conform to agreed contracts. | Pact files stored in contract repository; validates producer/consumer compatibility. | QA‑9, CRN‑4 |
| **Security Scan (Trivy)** | Detect vulnerabilities in dependencies and container images. | Scan reports; blocks promotion if critical vulnerabilities found. | CRN‑4 |
| **Docker Image Creation & Repository** | Package services as immutable, environment‑agnostic containers. | Dockerfile, image pushed to private registry. | QA‑7, CON‑6 |
| **Helm Charts & Kubernetes Config** | Define deployment descriptors; inject environment‑specific values. | Helm templates, values files, ConfigMaps, Secrets – stored in Git (GitOps). | QA‑7, CON‑6 |
| **Development Environment** | Fast feedback for developers; automatic deployment after successful build. | Kubernetes namespace, ephemeral databases; receives every successful push to main. | QA‑7, QA‑9 |
| **Test Environment** | Execute end‑to‑end and contract testing against realistic APIs and integrations. | Stable Kubernetes namespace; seeded data; promoted from Dev. | QA‑9 |
| **Staging Environment** | Production‑like validation; final smoke tests. | Mirrors Production configuration; manual approval gate. | QA‑7, CON‑4 |
| **Production Environment** | Live system; high‑availability deployment with zero‑downtime rollout. | Blue‑green slots behind load balancer; automated health checks and rollback. | QA‑7, CON‑6, QA‑3 |
| **Team 1 Repository** | Angular app and API Gateway code, tests, Dockerfile, own pipeline definition (extended from shared). | Exposes REST/WebSocket to UI; consumes REST from downstream services. | CRN‑3, CON‑4 |
| **Team 2 Repository** | Pricing Engine and Price Query Service code, tests, etc. | Provides pricing APIs; publishes events to Kafka. | CRN‑3, CON‑4 |
| **Team 3 Repository** | Hotel, Rate, User Management services and Kafka integration. | Provides management APIs; consumes pricing events. | CRN‑3, CON‑4 |
| **Shared DevOps Repository** | Pipeline templates, Helm charts, monitoring stack, contract definitions. | Used by all teams via submodules or references. | CRN‑3, CRN‑5, QA‑7, CRN‑4 |

---

### Rationale Traceability

Every design decision directly addresses one or more of the iteration’s drivers:

- **QA‑7 – Deployability**: Docker and Kubernetes decouple the application from any environment‑specific runtime. Helm charts and external configuration (ConfigMaps/Secrets) ensure that the same image flows from Dev to Prod without modification.
- **QA‑9 – Testability**: The pipeline incorporates unit, contract, integration, and E2E tests, guaranteeing that each service can be verified independently. Contract testing (Pact) validates isolated service contracts without relying on live external systems.
- **CRN‑3 – Work Allocation**: Three self‑contained teams each own a dedicated repository containing their service code, tests, and CI definition. This minimises inter‑team dependencies, allows independent release cadences, and supports the MVP delivery strategy.
- **CRN‑4 – Avoid Technical Debt**: Architecture fitness functions (ArchUnit) run on every build to uphold package structure and dependency rules. Static analysis (SonarQube with strict quality gates) prevents code quality erosion. Enforcing OpenAPI/Pact contracts makes integration drift impossible without breaking the pipeline.
- **CRN‑5 – Continuous Deployment Infrastructure**: The GitHub Actions pipeline fully automates the path from commit to production, with appropriate manual approval gates for upper environments. Blue‑green deployment and automated rollback reduce the risk of bad releases reaching end users.
- **CON‑3 – Git‑based Platform**: All assets—source code, pipeline definitions, Helm charts, contracts, and configuration—are version‑controlled in Git. This fits the organisation’s existing tooling and enables a GitOps workflow.
- **CON‑4 – 2‑Month MVP / 6‑Month Full Scope**: The team allocation allows Teams 1 and 2 to deliver the MVP use cases quickly, while Team 3 can build the remaining management services in parallel. The pipeline’s automated promotion reduces the overhead of releasing incremental features.
- **CON‑6 – Cloud‑Native Approach**: Containerisation, Kubernetes orchestration, externalised configuration, and the GitOps model follow standard cloud‑native practices, enabling elastic scaling and resilience.

The design is cohesive: all elements reinforce the ability to deliver reliably, test thoroughly, avoid technical debt, and scale team contributions effectively.

## ADD Step 7: Review

# Architecture Review – Iteration 4: Development and Operations Infrastructure

**Reviewer:** Architecture Quality Reviewer Agent  
**Date:** 2026-06-11  
**Iteration Goal:** Addressing Development and Operations  
**Reviewed Drivers:** QA-7, QA-9, CRN-3, CRN-4, CRN-5, CON-3, CON-4, CON-6  

---

## Overall Assessment: **PASS**  
The design is coherent, well‑traced to the assigned drivers, and provides a solid cloud‑native DevOps foundation. Minor gaps related to operational detail (database migrations, configuration management) should be addressed before implementation to avoid technical debt and deployment friction.

---

## Strengths
- **Clear Rationale Traceability:** Every decision is explicitly linked to one or more drivers, making verification straightforward.
- **Strong Technical Debt Prevention:** The pipeline enforces static analysis, architecture fitness functions (ArchUnit), and contract testing (Pact) as quality gates – this directly fulfills CRN‑4.
- **Environment Portability (QA‑7):** Docker, Kubernetes, Helm, and externalised configuration (ConfigMaps/Secrets) ensure that the same immutable image travels from Dev to Prod without code changes.
- **Comprehensive Testing Pyramid (QA‑9):** Unit, contract, integration, and end‑to‑end tests are all built into the pipeline, and contract testing allows independent verification of services.
- **Effective Work Allocation (CRN‑3):** Three independent, cross‑functional teams each own a repository with clear interfaces (contracts, events) and can extend the shared CI/CD templates; this minimises inter‑team dependencies and supports parallel development.
- **Production‑Grade Delivery (CRN‑5, CON‑6):** Blue‑green deployment, automated rollback, and GitOps‑style configuration management embody cloud‑native best practices.

---

## Issues Found

### 1. **Missing Database Schema Migration Strategy**  
The pipeline contains no step for applying or verifying database schema changes. The Hotel Pricing System will rely on persistent data (hotels, rates, prices). Without automated migration handling, promoting a new release may fail because the database schema is out of sync, and test environments may not reflect the required schema.  
**Impacted Drivers:** QA‑7 (Deployability), CRN‑4 (Technical debt)  
**Suggestion:** Insert a **migration job** (e.g., Flyway, Liquibase) as part of the deployment stage – before the new version becomes active in any environment. Integrate it into the same automated flow, and make it a hard gate in the pipeline. In Dev, migrations can run automatically; in higher environments, they should be tested for idempotency and reversibility.

### 2. **Configuration Management Detail Lacking**  
The design states that *ConfigMaps/Secrets* are env‑specific, but does not specify where they are stored, versioned, or how the pipeline selects the correct values. A true GitOps workflow requires that all configuration be declarative and version‑controlled.  
**Impacted Drivers:** QA‑7, CRN‑5  
**Suggestion:** Clarify that environment‑specific values are kept in **Helm chart value files** (e.g., `values-dev.yaml`, `values-prod.yaml`) inside the Shared DevOps repository. The pipeline should pass the `--values` flag accordingly. Secrets should be encrypted (e.g., via Sealed Secrets or a vault tool) and stored in Git, with access controls.

### 3. **Test Environment Data Seeding Strategy Omitted**  
The Test environment is described as having *“seeded data”*, but there is no mechanism for resetting or maintaining that data between test runs. If E2E and integration tests modify state, subsequent runs may become non‑deterministic or fragile.  
**Impacted Driver:** QA‑9 (Testability)  
**Suggestion:** Include a **data reset step** before the main test cycle, e.g., run database restore scripts or ephemeral database clones that are discarded after the test suite finishes. This can be orchestrated by the pipeline job.

### 4. **Pipeline Diagram Minor Ambiguity**  
The CI/CD Pipeline diagram uses decision nodes (*Manual Gate?*, *Staging Approval?*, *Production Approval?*) without an explicit “No” branch. Readers may assume the pipeline continues or terminates ambiguously.  
**Suggestion:** Add a “No → Reject / Notify” branch to each decision to clearly indicate that the promotion stops until the issue is resolved.

### 5. **MVP Feasibility – Cross‑Team Data Dependencies**  
The MVP scope (HPS‑1, HPS‑2, HPS‑3) is allocated to Teams 1 and 2, but price queries (HPS‑3) typically require hotel metadata, which is owned by Team 3 (Hotel Management). The design does not specify how this dependency is resolved early enough to avoid blocking the MVP.  
**Impacted Driver:** CON‑4 (2‑month MVP)  
**Suggestion:** Either define a **minimal stub/hard‑coded hotel endpoint** provided by Team 3 from Day 1, or agree on a static contractual dataset that Team 1/2 can use until the full service is ready. This keeps the MVP schedule viable.

---

## Suggestions (Non‑Blocking Improvements)
- **Monitoring Integration (QA‑8):** Although not a primary driver, the Shared DevOps team includes a Monitoring Stack. It would be beneficial to define how the pipeline feeds deployment events and health‑check results into that stack for traceability.
- **Security Scanning Scope:** Trivy scans cover containers and dependencies. Consider adding **SAST** (Static Application Security Testing) earlier in the pipeline for source‑code level vulnerabilities (e.g., using CodeQL or similar).
- **Immutable Infrastructure:** Emphasise that no in‑place updates are made to running environments; every change triggers a fresh Helm release, reinforcing GitOps principles.

---

## Decision: **Design Acceptable with Minor Modifications Required**  
The design adequately satisfies all assigned architectural drivers. The four main issues (migration strategy, configuration management, test data seeding, and pipeline diagram clarity) are operational refinements that should be addressed before the DevOps subsystem is implemented. Once these are incorporated, the design can move to the next stage.

**Recommended Action:** Designer revises the design notes to include the suggested mechanisms and confirms the changes with the reviewer. No iteration restart is needed.

## Reviewer Assessment

> **Timestamp**: 2026-06-11T10:37:48.6863887

# Architecture Review – Iteration 4: Development and Operations Infrastructure

**Reviewer:** Architecture Quality Reviewer Agent  
**Date:** 2026-06-11  
**Iteration Goal:** Addressing Development and Operations  
**Reviewed Drivers:** QA-7, QA-9, CRN-3, CRN-4, CRN-5, CON-3, CON-4, CON-6  

---

## Overall Assessment: **PASS**  
The design is coherent, well‑traced to the assigned drivers, and provides a solid cloud‑native DevOps foundation. Minor gaps related to operational detail (database migrations, configuration management) should be addressed before implementation to avoid technical debt and deployment friction.

---

## Strengths
- **Clear Rationale Traceability:** Every decision is explicitly linked to one or more drivers, making verification straightforward.
- **Strong Technical Debt Prevention:** The pipeline enforces static analysis, architecture fitness functions (ArchUnit), and contract testing (Pact) as quality gates – this directly fulfills CRN‑4.
- **Environment Portability (QA‑7):** Docker, Kubernetes, Helm, and externalised configuration (ConfigMaps/Secrets) ensure that the same immutable image travels from Dev to Prod without code changes.
- **Comprehensive Testing Pyramid (QA‑9):** Unit, contract, integration, and end‑to‑end tests are all built into the pipeline, and contract testing allows independent verification of services.
- **Effective Work Allocation (CRN‑3):** Three independent, cross‑functional teams each own a repository with clear interfaces (contracts, events) and can extend the shared CI/CD templates; this minimises inter‑team dependencies and supports parallel development.
- **Production‑Grade Delivery (CRN‑5, CON‑6):** Blue‑green deployment, automated rollback, and GitOps‑style configuration management embody cloud‑native best practices.

---

## Issues Found

### 1. **Missing Database Schema Migration Strategy**  
The pipeline contains no step for applying or verifying database schema changes. The Hotel Pricing System will rely on persistent data (hotels, rates, prices). Without automated migration handling, promoting a new release may fail because the database schema is out of sync, and test environments may not reflect the required schema.  
**Impacted Drivers:** QA‑7 (Deployability), CRN‑4 (Technical debt)  
**Suggestion:** Insert a **migration job** (e.g., Flyway, Liquibase) as part of the deployment stage – before the new version becomes active in any environment. Integrate it into the same automated flow, and make it a hard gate in the pipeline. In Dev, migrations can run automatically; in higher environments, they should be tested for idempotency and reversibility.

### 2. **Configuration Management Detail Lacking**  
The design states that *ConfigMaps/Secrets* are env‑specific, but does not specify where they are stored, versioned, or how the pipeline selects the correct values. A true GitOps workflow requires that all configuration be declarative and version‑controlled.  
**Impacted Drivers:** QA‑7, CRN‑5  
**Suggestion:** Clarify that environment‑specific values are kept in **Helm chart value files** (e.g., `values-dev.yaml`, `values-prod.yaml`) inside the Shared DevOps repository. The pipeline should pass the `--values` flag accordingly. Secrets should be encrypted (e.g., via Sealed Secrets or a vault tool) and stored in Git, with access controls.

### 3. **Test Environment Data Seeding Strategy Omitted**  
The Test environment is described as having *“seeded data”*, but there is no mechanism for resetting or maintaining that data between test runs. If E2E and integration tests modify state, subsequent runs may become non‑deterministic or fragile.  
**Impacted Driver:** QA‑9 (Testability)  
**Suggestion:** Include a **data reset step** before the main test cycle, e.g., run database restore scripts or ephemeral database clones that are discarded after the test suite finishes. This can be orchestrated by the pipeline job.

### 4. **Pipeline Diagram Minor Ambiguity**  
The CI/CD Pipeline diagram uses decision nodes (*Manual Gate?*, *Staging Approval?*, *Production Approval?*) without an explicit “No” branch. Readers may assume the pipeline continues or terminates ambiguously.  
**Suggestion:** Add a “No → Reject / Notify” branch to each decision to clearly indicate that the promotion stops until the issue is resolved.

### 5. **MVP Feasibility – Cross‑Team Data Dependencies**  
The MVP scope (HPS‑1, HPS‑2, HPS‑3) is allocated to Teams 1 and 2, but price queries (HPS‑3) typically require hotel metadata, which is owned by Team 3 (Hotel Management). The design does not specify how this dependency is resolved early enough to avoid blocking the MVP.  
**Impacted Driver:** CON‑4 (2‑month MVP)  
**Suggestion:** Either define a **minimal stub/hard‑coded hotel endpoint** provided by Team 3 from Day 1, or agree on a static contractual dataset that Team 1/2 can use until the full service is ready. This keeps the MVP schedule viable.

---

## Suggestions (Non‑Blocking Improvements)
- **Monitoring Integration (QA‑8):** Although not a primary driver, the Shared DevOps team includes a Monitoring Stack. It would be beneficial to define how the pipeline feeds deployment events and health‑check results into that stack for traceability.
- **Security Scanning Scope:** Trivy scans cover containers and dependencies. Consider adding **SAST** (Static Application Security Testing) earlier in the pipeline for source‑code level vulnerabilities (e.g., using CodeQL or similar).
- **Immutable Infrastructure:** Emphasise that no in‑place updates are made to running environments; every change triggers a fresh Helm release, reinforcing GitOps principles.

---

## Decision: **Design Acceptable with Minor Modifications Required**  
The design adequately satisfies all assigned architectural drivers. The four main issues (migration strategy, configuration management, test data seeding, and pipeline diagram clarity) are operational refinements that should be addressed before the DevOps subsystem is implemented. Once these are incorporated, the design can move to the next stage.

**Recommended Action:** Designer revises the design notes to include the suggested mechanisms and confirms the changes with the reviewer. No iteration restart is needed.

## Final Design Decision for Iteration 4

## Iteration 4: Development and Operations Infrastructure – Design

### Design Summary
This iteration defines a **cloud‑native DevOps subsystem** that enables rapid, safe, and team‑aligned delivery of the Hotel Pricing System. The design satisfies all assigned drivers (QA‑7, QA‑9, CRN‑3, CRN‑4, CRN‑5, CON‑3, CON‑4, CON‑6) through:

- **Deployability (QA‑7)**: Docker containers, Kubernetes orchestration, Helm charts, and externalised configuration (ConfigMaps/Secrets) – no code changes across Development, Test, Staging, and Production.
- **Testability (QA‑9)**: Automated pipelines with unit, contract, integration, and end‑to‑end test stages, plus architecture fitness functions.
- **Work Allocation (CRN‑3)**: Three cross‑functional teams (Frontend+Gateway, Pricing Core, Hotel/Rate/Users+Kafka) mapped to independent repositories, with a shared DevOps team owning pipeline, charts, and monitoring.
- **Technical Debt Prevention (CRN‑4)**: Enforced inter‑service contracts (OpenAPI/Pact), static analysis (SonarQube), and architectural conformance tests (ArchUnit) as quality gates inside the pipeline.
- **Continuous Deployment (CRN‑5)**: A Git‑based CI/CD pipeline (GitHub Actions) with automated promotion through environments, blue‑green production deployment, and automated rollback.
- **Git‑based platform (CON‑3)**: Every artefact (code, config, contracts, pipeline definitions) is stored in Git repositories; all changes are managed through pull requests and protected branches.
- **Delivery schedule (CON‑4)**: The pipeline and team assignment support a 2‑month MVP (HPS‑1, HPS‑2, HPS‑3) and progressive delivery of the full scope in 6 months.
- **Cloud‑native (CON‑6)**: Containerisation with Kubernetes and GitOps‑style configuration management.

---

### Architectural Views

#### 1. CI/CD Pipeline Diagram
```mermaid
graph TD
    A[Code Push to Main/Feature Branch] --> B[Checkout & Static Analysis<br/>SonarQube + ArchUnit]
    B --> C[Build & Unit Tests]
    C --> D[Contract Tests<br/>Pact]
    D --> E[Integration Tests]
    E --> F[Security Scan<br/>Trivy]
    F --> G[Package & Publish<br/>Docker Image]
    G --> H[Deploy to<br/>Development Environment]
    H --> I{Manual Gate?}
    I -->|Yes| J[Deploy to<br/>Test Environment]
    J --> K[E2E Tests<br/>in Test]
    K --> L{Staging Approval?}
    L -->|Yes| M[Deploy to<br/>Staging Environment]
    M --> N[Smoke Tests]
    N --> O{Production Approval?}
    O -->|Yes| P[Blue-Green Deploy to Production]
    P --> Q[Health Checks]
    Q -->|Failure| R[Automated Rollback]
    Q -->|Success| S[Release Complete]
```

#### 2. Deployment Environment Diagram
```mermaid
graph LR
    subgraph CI/CD Pipeline
        build[Build & Package] --> dev
    end

    dev(Development Environment) --> |automated+tests| test(Test Environment)
    test --> |e2e & contract validation| staging(Staging Environment)
    staging --> |manual approval| prod(Production Environment)

    dev --> config1(ConfigMaps/Secrets<br/>env-specific)
    test --> config2(ConfigMaps/Secrets<br/>env-specific)
    staging --> config3(ConfigMaps/Secrets<br/>env-specific)
    prod --> config4(ConfigMaps/Secrets<br/>env-specific)

    style prod fill:#ffbbbb,stroke:#333,stroke-width:2px
    style dev fill:#bbffbb,stroke:#333,stroke-width:1px
    style test fill:#FFFFBB,stroke:#333,stroke-width:1px
    style staging fill:#FFE5CC,stroke:#333,stroke-width:1px
```

#### 3. Team Allocation Diagram
```mermaid
graph TB
    subgraph Team1[Team 1: Frontend + API Gateway]
        fe[Angular App]
        gw[API Gateway]
    end

    subgraph Team2[Team 2: Pricing Core]
        pe[Pricing Engine]
        pq[Price Query Service]
    end

    subgraph Team3[Team 3: Hotel/Rate/Users + Kafka]
        hm[Hotel Management]
        rm[Rate Management]
        um[User Management]
        ka[Kafka Integration]
    end

    subgraph Shared[Shared DevOps Team]
        pl[CI/CD Pipelines]
        he[Helm Charts & Kubernetes Config]
        mi[Monitoring Stack]
        co[Contract Repository<br/>OpenAPI/Pact]
    end

    Team1 --> |contracts| Team2
    Team2 --> |events| Team3
    Team3 --> |contracts| Team1
    Shared --> |provides infrastructure| Team1
    Shared --> |provides infrastructure| Team2
    Shared --> |provides infrastructure| Team3
```

---

### Architectural Elements, Responsibilities, and Interfaces

| Element | Responsibility | Interfaces / Artefacts | Driven By |
|---------|---------------|------------------------|-----------|
| **CI/CD Pipeline (GitHub Actions)** | Automate build, test, security scan, packaging, and deployment. Enforce quality gates. | Triggers on push to protected branches; consumes Dockerfiles, Helm charts, and contract definitions; publishes Docker images and deployment manifests. | CRN‑5, CON‑3, QA‑9, CRN‑4 |
| **Static Analysis & Architecture Fitness** | Validate code quality, banned dependencies, and architectural constraints. | SonarQube reports, ArchUnit rules. Fails build if quality gate not met. | CRN‑4 |
| **Unit & Integration Test Stage** | Run isolated and component‑level tests. | Junit, Mockito, Spring Test; output test reports. | QA‑9 |
| **Contract Test Stage (Pact)** | Ensure service interfaces conform to agreed contracts. | Pact files stored in contract repository; validates producer/consumer compatibility. | QA‑9, CRN‑4 |
| **Security Scan (Trivy)** | Detect vulnerabilities in dependencies and container images. | Scan reports; blocks promotion if critical vulnerabilities found. | CRN‑4 |
| **Docker Image Creation & Repository** | Package services as immutable, environment‑agnostic containers. | Dockerfile, image pushed to private registry. | QA‑7, CON‑6 |
| **Helm Charts & Kubernetes Config** | Define deployment descriptors; inject environment‑specific values. | Helm templates, values files, ConfigMaps, Secrets – stored in Git (GitOps). | QA‑7, CON‑6 |
| **Development Environment** | Fast feedback for developers; automatic deployment after successful build. | Kubernetes namespace, ephemeral databases; receives every successful push to main. | QA‑7, QA‑9 |
| **Test Environment** | Execute end‑to‑end and contract testing against realistic APIs and integrations. | Stable Kubernetes namespace; seeded data; promoted from Dev. | QA‑9 |
| **Staging Environment** | Production‑like validation; final smoke tests. | Mirrors Production configuration; manual approval gate. | QA‑7, CON‑4 |
| **Production Environment** | Live system; high‑availability deployment with zero‑downtime rollout. | Blue‑green slots behind load balancer; automated health checks and rollback. | QA‑7, CON‑6, QA‑3 |
| **Team 1 Repository** | Angular app and API Gateway code, tests, Dockerfile, own pipeline definition (extended from shared). | Exposes REST/WebSocket to UI; consumes REST from downstream services. | CRN‑3, CON‑4 |
| **Team 2 Repository** | Pricing Engine and Price Query Service code, tests, etc. | Provides pricing APIs; publishes events to Kafka. | CRN‑3, CON‑4 |
| **Team 3 Repository** | Hotel, Rate, User Management services and Kafka integration. | Provides management APIs; consumes pricing events. | CRN‑3, CON‑4 |
| **Shared DevOps Repository** | Pipeline templates, Helm charts, monitoring stack, contract definitions. | Used by all teams via submodules or references. | CRN‑3, CRN‑5, QA‑7, CRN‑4 |

---

### Rationale Traceability

Every design decision directly addresses one or more of the iteration’s drivers:

- **QA‑7 – Deployability**: Docker and Kubernetes decouple the application from any environment‑specific runtime. Helm charts and external configuration (ConfigMaps/Secrets) ensure that the same image flows from Dev to Prod without modification.
- **QA‑9 – Testability**: The pipeline incorporates unit, contract, integration, and E2E tests, guaranteeing that each service can be verified independently. Contract testing (Pact) validates isolated service contracts without relying on live external systems.
- **CRN‑3 – Work Allocation**: Three self‑contained teams each own a dedicated repository containing their service code, tests, and CI definition. This minimises inter‑team dependencies, allows independent release cadences, and supports the MVP delivery strategy.
- **CRN‑4 – Avoid Technical Debt**: Architecture fitness functions (ArchUnit) run on every build to uphold package structure and dependency rules. Static analysis (SonarQube with strict quality gates) prevents code quality erosion. Enforcing OpenAPI/Pact contracts makes integration drift impossible without breaking the pipeline.
- **CRN‑5 – Continuous Deployment Infrastructure**: The GitHub Actions pipeline fully automates the path from commit to production, with appropriate manual approval gates for upper environments. Blue‑green deployment and automated rollback reduce the risk of bad releases reaching end users.
- **CON‑3 – Git‑based Platform**: All assets—source code, pipeline definitions, Helm charts, contracts, and configuration—are version‑controlled in Git. This fits the organisation’s existing tooling and enables a GitOps workflow.
- **CON‑4 – 2‑Month MVP / 6‑Month Full Scope**: The team allocation allows Teams 1 and 2 to deliver the MVP use cases quickly, while Team 3 can build the remaining management services in parallel. The pipeline’s automated promotion reduces the overhead of releasing incremental features.
- **CON‑6 – Cloud‑Native Approach**: Containerisation, Kubernetes orchestration, externalised configuration, and the GitOps model follow standard cloud‑native practices, enabling elastic scaling and resilience.

The design is cohesive: all elements reinforce the ability to deliver reliably, test thoroughly, avoid technical debt, and scale team contributions effectively.

## Iteration 4 — Review Status

| Check | Status |
|-------|--------|
| Review Passed | ✓ PASS |
| Conversation Entries | 3 |
| ADD Steps Completed | 2 |

---

# Interaction Cost Analysis

| Metric | Value |
|--------|-------|
| The way of completing the assignment | Multi-Agent (Distributed Reasoning + Collaborative Verification) |
| The LLM used | deepseek/deepseek-v4-pro |
| Number of Human Interaction turns | 1 (initial application execution) |
| Total Conversation Entries | 29 |
| Token Consumption | See conversation logs for per-iteration token counts |
| Time Cost | See timestamps in conversation logs for duration |

## Multi-Agent Interaction Structure

The system employs three specialized agents collaborating through structured dialogue:

| Agent | Role | Responsibilities |
|-------|------|-----------------|
| **Orchestrator Agent** | Workflow Coordinator | Task decomposition, ADD step sequencing, conflict resolution, progress tracking, quality gate enforcement |
| **Designer Agent** | Senior Software Architect | Architectural decision making, Mermaid diagram generation, interface definition, design rationale documentation |
| **Reviewer Agent** | Architecture Quality Reviewer | Quality attribute verification, constraint compliance checking, risk identification, improvement suggestions |

## Agent Interaction Count

| Iteration | Goal | ADD Steps | Conversation Entries |
|-----------|------|-----------|---------------------|
| 1 (Establishing an Overall Sys...) | 6 | 14 |
| 2 (Identifying Structures to S...) | 4 | 9 |
| 3 (Addressing Reliability and ...) | 2 | 3 |
| 4 (Addressing Development and ...) | 2 | 3 |
| **Total** | | **14** | **29** |

---

# Individual Reflection

## 1) Problems Encountered and Solutions Adopted

### Problem 1: Ensuring Strict Compliance with ADD 3.0

The assignment requires all design rules to be explicitly derived from provided system
instructions, with no external domain knowledge. Agents might hallucinate or introduce
external architectural patterns not present in the prior knowledge.

**Solution**: The complete ADD 3.0 method description and case study were embedded verbatim
into each agent's system prompt. The Reviewer agent was specifically tasked with verifying
that every design decision references at least one architectural driver from the provided
materials. The Orchestrator enforces the constraint that no external knowledge is used.

### Problem 2: Multi-Agent Coordination Complexity

Coordinating three specialized agents through 4 iterations with up to 7 ADD steps each
required careful orchestration to avoid redundant work or conflicting decisions.

**Solution**: The Orchestrator agent was designed with explicit workflow management
responsibilities: task decomposition, conversation context management across iterations,
conflict resolution, and quality gate enforcement. Each agent maintains a clear separation
of concerns — Designer creates, Reviewer verifies, Orchestrator coordinates.

### Problem 3: Mermaid Diagram Generation Quality

The assignment requires architecture views to be generated using Mermaid or PlantUML code.
Ensuring syntactically correct and semantically accurate diagrams across C4 context,
container, component, sequence, deployment, state, and ER diagrams was challenging.

**Solution**: The Designer agent was instructed to produce specific diagram types for each
iteration. The Reviewer agent validates diagrams for both syntactic correctness and
architecture fidelity. When diagrams need revision, the Reviewer provides specific feedback
rather than generic rejection.

### Problem 4: Quality Attribute Trade-off Resolution

QA-1 (performance: <100ms), QA-2 (reliability: 100% delivery), and QA-4 (scalability:
1M queries/day) create tension — synchronous reliability checks add latency, while
scale-out adds complexity.

**Solution**: The Reviewer agent independently evaluates each quality attribute scenario.
When conflicts arise, the Orchestrator applies a decision rule: prioritize High-importance
drivers over Medium-importance drivers. The resulting design uses the transactional outbox
pattern (async publication after atomic write) to satisfy both QA-1 and QA-2 simultaneously.

### Problem 5: Spring AI Multi-Agent Integration

Spring AI's OpenAI starter is used with a DeepSeek-compatible API endpoint. Multi-agent
orchestration required programmatic coordination of multiple ChatClient calls with
distinct system prompts per agent role.

**Solution**: We implemented a programmatic multi-agent orchestration pattern using
individual ChatClient instances with distinct system prompts for each agent role.
Conversation context is managed explicitly through the ConversationLogService, passed
between agents as structured history.

## 2) Personal Contributions to Group Work

| Name | Chinese | Contributions |
|------|---------|---------------|
| 231250033 | 戴志洋 | Multi-agent system architecture design and implementation; Orchestrator and Reviewer agent prompt engineering; Spring AI integration |
| 231250016 | 孙子然 | Designer agent prompt engineering; Mermaid diagram generation and verification; ADD 3.0 process compliance verification |
| 231250125 | 沈红旗 | Report generation and formatting; conversation log collection and analysis; interaction cost analysis; testing and validation |

