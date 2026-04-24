# ImageTrend Demo — May 4th Kickoff (2 Hours)

> **Audience:** ImageTrend engineering leadership and team leads.
> **Framing:** This is not a product tour of GitHub Copilot. This is a guided decision-making session — each section presents a decision ImageTrend needs to make, explains the options and trade-offs, recommends an approach, and then demonstrates what the recommended approach looks like in practice using the OctoCAT Supply demo repo as a working example.
>
> The demo repo exists solely to make these concepts tangible. Every feature shown here is something ImageTrend will need to implement for its own codebase.

---

## Agenda & Timing Overview

| # | Topic | Time | Category |
|---|-------|------|----------|
| — | [Copilot Basics Warm-Up](#copilot-basics-warm-up) | 15 min | Tech |
| 1 | [Product Vision & Requirements](#1-product-get-big-picture-vision-and-requirements-in-shape) | 10 min | Product |
| 2 | [Repo in GitHub — Architecture, FE, BE, Patterns](#2-repo-in-github--architecture-patterns-fe-be-microservices) | 10 min | Tech |
| 3 | [Coding Tool Selection](#3-coding-tool-selection-copilot--llm-cursor--claude-or-others) | 10 min | Tech |
| 4 | [Define Sub-Agents for Plan, Develop, Test](#4-define-sub-agents-for-plan-develop-test-and-so-forth) | 15 min | Tech |
| 5 | [Protocols for Working with AI Tools](#5-protocols-for-and-working-with-ai-tools-and-overlap) | 10 min | Tech |
| 6 | [PR Review Rules](#6-pr-review-rules-at-least-2-devs-approve-every-auto-generated-pr) | 10 min | Tech |
| 7 | [Version Control Everything](#7-version-control-everything-prompts-instructions-md-files-artifacts) | 10 min | Tech |
| 8 | [Prototyping Tools & Requirements Capture](#8-prototyping-tools-capturing-comprehensive-requirements) | 10 min | Tech & Product |
| 9 | [Ingest ImageTrend Design System & Shared Component Library](#9-ingest-imagetrend-design-system-and-shared-component-library) | 10 min | Tech & UX |
| 10 | [CICD Toolchain, Production-Ready Code & Deployment](#10-set-up-tech-toolchain-for-cicd-production-ready-code-and-deployment) | 10 min | Tech |
| 11 | [Test Agents: Unit, Integration, Security & Regression](#11-test-agents-unit-integration-security-and-regressionautomation) | 10 min | Tech |
| | **Total** | **~130 min** | |

> Buffer: ~10 min for Q&A / transitions between sections.

---

<br><br>

```
============================================================
  COPILOT BASICS WARM-UP
============================================================
```

---

<br><br>

# Copilot Basics Warm-Up

> Before we dive into the decisions, let's make sure everyone has a shared understanding of what Copilot can do out of the box. These quick demos show the daily developer experience — the foundation that everything else builds on. If your team hasn't seen Copilot in action, this is the "why should we care" section.

---

## Demo: Code Completion (Ghost Text)

- **What to show:** Copilot's inline code suggestions as you type — the "ghost text" experience.
- **Why:** This is the most common daily interaction with Copilot. It shows how Copilot predicts your intent from context.

### How

1. Open [`SupplierController.java`](../../api/src/main/java/com/octodemo/octocatsupply/controller/SupplierController.java) in the API.
2. Start typing a new method signature inside the class, for example:

   ```java
   @GetMapping("/active")
   ```

3. Pause and show the ghost text suggestion appear — Copilot should suggest a method that returns active suppliers.
4. Press **Tab** to accept, or **Esc** to dismiss. Use **Alt+]** / **Alt+[** to cycle through alternatives.
5. *(Optional)* Open [`Products.tsx`](../../frontend/src/components/entity/product/Products.tsx) and start typing a new helper function to show completions in TypeScript/React as well.

> **Key Takeaway:** Copilot reads surrounding code — imports, patterns, naming — and suggests contextually relevant completions without any prompting.

---

## Demo: Inline Chat (Ctrl+I)

- **What to show:** Quick, targeted edits using inline Copilot Chat without leaving the editor.
- **Why:** Demonstrate that Copilot isn't just autocomplete — you can have a conversation right in the code.

### How

1. Open [`Product.java`](../../api/src/main/java/com/octodemo/octocatsupply/model/Product.java) (the Product entity model).
2. Select the class body and press **Ctrl+I** to open inline chat.
3. Type:

   ```txt
   Add a stockQuantity integer field with a default of 0
   ```

4. Show the diff preview inline — Copilot adds the field, annotation, and getter/setter.
5. Click **Accept** or **Discard**.
6. *(Optional)* In [`Navigation.tsx`](../../frontend/src/components/Navigation.tsx), select the nav links section, press **Ctrl+I**, and type:

   ```txt
   Add a Suppliers link between Products and About
   ```

> **Key Takeaway:** Inline chat lets you make surgical edits with natural language — no context switching to a side panel.

---

## Demo: Copilot Chat — Ask & Explain

- **What to show:** Using the Chat panel to ask questions about the codebase and get explanations.
- **Why:** Show Copilot as a learning and exploration tool, not just a code generator.

### How

1. Open Copilot Chat and switch to **Ask** mode.
2. Type:

   ```txt
   @workspace How is the relationship between Orders, OrderDetails, and Deliveries modeled?
   ```

3. Show how Copilot explains the entity relationships, referencing actual files in the codebase.
4. Follow up with:

   ```txt
   What would break if I deleted the OrderDetailDelivery junction table?
   ```

5. Show that Copilot understands cascading impacts across the data model.
6. *(Optional)* Open any file, select a block of code, right-click → **Copilot** → **Explain This** to show the context menu shortcut.

> **Key Takeaway:** Copilot Chat understands your whole workspace — use `@workspace` to ask architectural questions, not just line-level ones.

---

## Demo: Quick Fix & Error Resolution

- **What to show:** Copilot's ability to diagnose and fix errors directly from the editor.
- **Why:** Show that Copilot accelerates the debugging loop — from red squiggle to working code.

### How

1. Open any controller (e.g., [`ProductController.java`](../../api/src/main/java/com/octodemo/octocatsupply/controller/ProductController.java)).
2. Introduce a deliberate error — for example, change a return type from `List<Product>` to `List<String>`.
3. Show the red squiggle appear.
4. Hover over the error → click the lightbulb → select **Fix using Copilot**.
5. Show the suggested fix and accept it.
6. *(Alternative)* Open Copilot Chat and paste the error message — show Copilot explaining the root cause and suggesting a fix.

> **Key Takeaway:** Copilot integrates with the editor's diagnostic system — it doesn't just generate code, it helps you fix it.

---

## Demo: Generate Commit Messages & PR Descriptions

- **What to show:** Copilot generating meaningful commit messages and pull request summaries.
- **Why:** Demonstrate that Copilot assists across the full development lifecycle, not just in the editor.

### How

1. Make a small change to any file (e.g., add a comment or tweak a constant).
2. Open the **Source Control** panel (`Ctrl+Shift+G`).
3. Click the **sparkle icon** (✨) in the commit message box.
4. Show the generated commit message — it summarizes the staged changes.
5. *(Optional)* If pushing to a branch, show the **Generate PR Description** option when creating a pull request.

> **Key Takeaway:** Copilot saves time on the mundane parts of the workflow — commit messages, PR descriptions, changelogs — so you can focus on the code.

---

<br><br>

```
============================================================
  KICKOFF ACTIVITY DEMOS
============================================================
```

---

<br><br>

# 1. Product: Get Big Picture Vision and Requirements in Shape

- **What to show:** How the product vision, requirements hierarchy, and project tracking flow from high-level goals into actionable, trackable work items using GitHub Issues and Projects.
- **Why:** Before any code is written, the team needs alignment on _what_ we're building. This section establishes the product foundation that every subsequent technical demo builds on.

### Talk Track — Vision → Requirements → Execution

Every successful project starts with a clear product vision. Before we talk about coding tools, sub-agents, or CI/CD, we need to answer three questions:

1. **What are we building?** — The product vision describes the problem space, the target users, and the value proposition. This should exist as a living document — not a slide deck that gets archived after one meeting.
2. **What does "done" look like?** — Requirements need to be specific enough to implement but flexible enough to iterate on. We use GitHub Issues with issue types (Epic, Feature, Task) to create a natural hierarchy.
3. **How do we track progress?** — GitHub Projects provides the board views, roadmap timelines, and sprint tracking that keep everyone aligned without leaving the platform where code lives.

**The requirements hierarchy:**

| Level | GitHub Issue Type | Example |
|-------|------------------|---------|
| **Theme** | Label / Milestone | "Supply Chain Visibility" |
| **Epic** | Epic issue | "Real-time delivery tracking" |
| **Feature** | Feature issue | "Show delivery status on order detail page" |
| **Task** | Task issue | "Add `status` field to Delivery entity" |

**Why this matters for ImageTrend:** Requirements captured in GitHub Issues become directly assignable to developers _or_ to the Copilot Coding Agent. A well-written issue is the input that drives autonomous implementation — the better the requirements, the better the AI output.

### How

#### Part 1: Product Vision Document (3 min)

1. Open the product vision document (one-pager or `docs/product-vision.md`).

   > **Pre-requisite:** Create this document before the demo. It should cover: problem statement, target users, key capabilities, success metrics, and non-goals.

2. Walk through the key sections. Emphasize that this is a living document — version-controlled in the repo, updated via PRs, reviewed by the team.

3. Point out that Copilot can _read_ this document when asked about the project:

   ```txt
   @workspace Based on our product vision, what entities would we need in the data model?
   ```

#### Part 2: Issues and Requirements Hierarchy (4 min)

1. Navigate to the repository's **Issues** tab on GitHub.
2. Show the existing issue types — **Epic**, **Feature**, **Task**, **Bug** — and how they create a natural hierarchy.
3. Open an Epic issue (e.g., `Compliance Requirements`) and show:
   - **Sub-issues** nested beneath it (Feature and Task types)
   - **Issue dependencies** — blocked-by / blocking relationships
   - **Custom fields** — Severity, Root Cause Category, Affected Users (defined at the org level for consistency)
4. Create a new Feature issue live:

   ```
   Title: Add delivery vehicle tracking
   Type: Feature
   Body: Track delivery vehicles assigned to each branch, including vehicle type,
         capacity, and current status. See product vision doc for context.
   ```

5. Show how this issue can later be assigned to a developer or to the Copilot Coding Agent.

#### Part 3: GitHub Projects Board (3 min)

1. Navigate to the linked **Project** board.
2. Show the pre-configured views:
   - **Team backlog** — grouped by Squad, showing Status and Priority
   - **Sprint Board** — board view of current sprint
   - **Roadmap** — timeline view of upcoming sprints
3. Show custom fields: **Priority**, **Sprint**, **Estimate** (story points), **Squad**.
4. Demonstrate drag-and-drop prioritization and how the board reflects the requirements hierarchy.

> **Decision for ImageTrend:** Adopt GitHub Issues with Epic/Feature/Task issue types as your single source of truth for requirements. Write Issues as if an AI agent will read them — because it will. Invest in a product vision document in `docs/` that Copilot can reference with `@workspace`. Set up a GitHub Project board with Sprint, Priority, and Squad fields before May 4th.

---

# 2. Repo in GitHub — Architecture Patterns, FE, BE, Microservices

- **The Decision:** How do you structure your repository, define your architecture patterns, and organize frontend/backend/infra so that both developers and AI agents can work effectively?
- **Why This Matters Now:** Copilot's effectiveness is directly proportional to the consistency and predictability of your codebase. If every developer (or every service) follows different patterns, the AI produces inconsistent output. Locking in architecture patterns _before_ writing code is the highest-leverage decision you'll make.

### Talk Track — Architecture Decisions That Affect AI Effectiveness

ImageTrend needs to make three architecture decisions before the kickoff:

**Decision 1: Monorepo vs. Multi-Repo**

| Option | AI Impact | When to Choose |
|--------|-----------|----------------|
| **Monorepo** | Copilot sees the full context (FE + BE + infra) in one workspace. Skills and instructions apply everywhere. Simpler setup. | Single application, small-to-medium team, early stages |
| **Multi-repo** | Each service has its own focused instructions and skills. Requires cross-repo orchestration skills for CCA. More setup, more flexibility at scale. | Microservices, large teams, independent deployment cadences |
| **Hybrid** | Platform repo orchestrates, service repos implement. Best of both. | Multiple teams, shared architecture, need coordination |

**Decision 2: Architecture Pattern Consistency**

The single most important thing for AI-assisted development is **repeatable patterns**. If every REST endpoint follows the same Controller → Repository → Database structure, Copilot can learn the pattern once and replicate it perfectly for every new entity. If each endpoint is a snowflake, every generation requires manual correction.

**Decision 3: Service Bus / Event-Driven?**

For async operations (order processing, delivery tracking, notifications), you'll eventually need an event bus. The question is whether to design for it now or bolt it on later. Copilot can generate event handlers and message schemas from specifications — but the architecture decision needs to be made by humans.

**Let me show you what a well-structured repo looks like, and why it makes AI-assisted development so much more effective:**

### How

#### Part 1: Repository Structure (3 min)

1. Show the repo in VS Code — expand the top-level folders.
2. Run from the terminal:

   ```sh
   gh repo view
   ```

3. Walk through the key directories:
   - `api/` — Spring Boot backend (point out `src/main/java/com/octodemo/octocatsupply/`)
   - `frontend/` — React + Vite + Tailwind
   - `docs/` — Architecture, build, deployment documentation
   - `.github/` — AI tooling: instructions, skills, prompts, workflows
   - `infra/` — Bicep templates for Azure deployment

#### Part 2: Architecture Documentation (3 min)

1. Open [`docs/architecture.md`](../../docs/architecture.md).
2. Show the ERD diagram (Mermaid) — the entity relationships that drive the data model.
3. Show the component architecture diagram — how frontend, backend, and data layer connect.
4. Explain: "This file is referenced by Copilot instructions. When Copilot generates code, it reads this to understand the system architecture — we don't restate it in every instruction file."

#### Part 3: Architecture Patterns in Code (4 min)

1. Open a controller (e.g., [`ProductController.java`](../../api/src/main/java/com/octodemo/octocatsupply/controller/ProductController.java)) — show the REST endpoint pattern with Swagger annotations.
2. Open the matching repository — show the JPA repository pattern.
3. Open a frontend component (e.g., [`Products.tsx`](../../frontend/src/components/entity/product/Products.tsx)) — show the React component pattern consuming the API.
4. Explain: "Every entity follows this exact same layered pattern. This consistency is what makes Agent Skills work — we teach Copilot the pattern once, and it replicates it for every new entity."

> **Decision for ImageTrend:** Start with a monorepo for the initial product. Document your architecture in `docs/architecture.md` with ERD diagrams and component architecture — this becomes Copilot's blueprint. Establish a single, repeatable pattern for every entity (Controller → Repository → Database) and enforce it via Agent Skills. Plan for microservices extraction later if needed — the Copilot Coding Agent supports cross-repo orchestration skills when you're ready to split.

---

# 3. Coding Tool Selection: Copilot + LLM, Cursor + Claude, or Others

- **The Decision:** Which AI coding platform does ImageTrend standardize on — and does that lock you into a single AI model?
- **Why This Matters Now:** This is often the first question leadership asks: _"Which tool do we buy?"_ The answer has implications for licensing, governance, model flexibility, and how much control IT retains over the AI stack.

### Talk Track — Evaluating the Options

ImageTrend has several options for AI-assisted development. Let's evaluate them honestly:

| Option | Strengths | Limitations |
|--------|-----------|-------------|
| **GitHub Copilot** (recommended) | Native GitHub integration (Issues, PRs, Actions, CCA). BYOK model flexibility. Org-level governance. Enterprise controls. | Requires GitHub as the platform |
| **Cursor + Claude** | Strong Claude integration, good UX | No native GitHub integration. No async Coding Agent. No org-level governance. Each developer configures independently |
| **Continue / Cody / Others** | Open-source or specific vendor strengths | Fragmented ecosystem. No unified platform for code + issues + CI/CD + agents |

**Our recommendation:** GitHub Copilot — not because it's the only good tool, but because ImageTrend is already on GitHub, and the value multiplier comes from the _platform integration_: Issues → Coding Agent → PR → Code Review → Actions → Deploy. No other tool offers that end-to-end chain.

**And here's the key point: Copilot is not one model.** This is where the BYOK (Bring Your Own Key) capability changes the conversation:

**Why this matters for enterprise adoption:**

| Concern | How BYOK Addresses It |
|---------|----------------------|
| Data residency / sovereignty | Use Azure OpenAI in your preferred region |
| Model performance for your domain | Bring a fine-tuned model optimized for your stack |
| Cost control | Use your own Azure commitment / reserved capacity |
| Compliance & audit | All traffic flows through your own Azure tenant with your policies |
| Vendor lock-in | Swap models without changing developer workflows |

### How

#### Part 1: Show Built-in Model Switching (VS Code — 2 min)

1. Open **Copilot Chat** in VS Code.
2. At the bottom of the chat panel, click the **model picker dropdown** — the button shows the current model name (e.g., `GPT-4.1`).
3. Walk through the list. Point out the breadth of providers: **OpenAI** (GPT-4.1, GPT-5.2, GPT-5.4), **Anthropic** (Claude Sonnet 4.6, Claude Opus 4.6), **Google** (Gemini 2.5 Pro, Gemini 3.1 Pro), **xAI** (Grok Code Fast 1).
4. Select a different model (e.g., switch from GPT-4.1 to Claude Sonnet 4.6).
5. Ask the same question in chat — show that `@workspace` context, tool access, and agent capabilities all work identically regardless of model.
6. *(Optional)* Mention **premium request multipliers** — some models cost more premium requests per interaction (e.g., Claude Opus 4.6 = 3x, GPT-4.1 = 0x/free).

> **Point to make:** Developers can switch models with a single click, mid-conversation, without any configuration. This is zero-setup model flexibility.

#### Part 2: Show Organization-Level Model Policies (GitHub.com — 3 min)

1. Navigate to your org on **GitHub.com**.
2. Go to **Settings → Copilot → Policies**.
3. Show the **Editor preview models** and **Copilot Chat models** policy toggles — admins can enable or restrict which models are available to all developers in the org.
4. Point out that when a model is disabled at the org level, it disappears from every developer's model picker automatically — no per-user configuration needed.
5. *(Optional)* If you have an Enterprise account, mention that enterprise owners can set policies that cascade down to all organizations.

> **Point to make:** IT and security teams get centralized control. One toggle removes a model from every developer's IDE across the entire organization.

#### Part 3: Add a Custom Model via BYOK (GitHub.com — 5 min)

**Supported providers:** Anthropic, AWS Bedrock, Google AI Studio, Microsoft Foundry, OpenAI, OpenAI-compatible providers, xAI.

**Pre-requisite:** Have an API key ready from one of the supported providers.

1. Navigate to your org on **GitHub.com**.
2. Go to **Settings → Copilot → Models**.
3. Click the **Custom models** tab.
4. Click **Add API key**.
5. Under **Provider**, select your LLM provider (e.g., `Anthropic`, `Microsoft Foundry`, `OpenAI`).
6. Under **Name**, type a display name — this is what developers will see in the model picker (e.g., `ImageTrend Internal GPT`).
7. Under **API key**, paste your key.
8. **Select or add models:**
   - **For OpenAI / Anthropic / xAI:** Click **Fetch new models** — Copilot queries your provider and lists all available models. Check the ones you want.
   - **For Microsoft Foundry:** Enter your **Deployment URL**, then type a **Model ID** and click **Add model**.
   - **For OpenAI-compatible providers:** Enter your base URL and model identifiers manually.
9. Click **Save**.
10. **Verify in VS Code:** Open Copilot Chat, click the model picker. Your custom model appears under a section labeled with your **organization name**.

> **Decision for ImageTrend:** Standardize on GitHub Copilot as the AI coding platform. Use the built-in model picker for day-to-day flexibility (let developers choose GPT, Claude, or Gemini per task). If ImageTrend has specific compliance or data residency requirements, use BYOK to route through your own Azure OpenAI tenant. Set org-level model policies to restrict which models are available — IT keeps control, developers keep flexibility.

---

# 4. Define Sub-Agents for Plan, Develop, Test and So Forth

- **The Decision:** How do you structure AI-assisted workflows so that planning, development, and testing each have purpose-built agents with focused context — rather than throwing everything at a single generic prompt?
- **Why This Matters Now:** The biggest mistake teams make with AI coding tools is using them as a monolithic "write code" button. You get dramatically better results when you decompose the SDLC into phases, each with its own agent that has focused context and guardrails. This is the difference between "Copilot writes okay code" and "Copilot consistently produces production-quality output."

### Talk Track — Designing Your Sub-Agent Architecture

ImageTrend needs to define what its AI-assisted development pipeline looks like. The concept is simple: instead of one agent doing everything, you have specialized agents — just like you have specialized roles on a human team.

**The question:** _What agents does ImageTrend need, and what is each one responsible for?_

Here's the framework we recommend:

**The three-phase pipeline:**

| Phase | Agent Type | Copilot Mode / Tool | What It Does |
|-------|-----------|---------------------|-------------|
| **Plan** | Planning Agent | Plan Mode, Ask Mode | Explores the codebase, asks clarifying questions, produces a structured plan. Does NOT write code. |
| **Develop** | Development Agent | Agent Mode, CCA, Agent Skills | Implements the plan — writes code, creates files, runs builds. Follows skills and instructions for consistency. |
| **Test** | Testing Agent | Agent Mode, TDD Agents | Runs tests, analyzes coverage, generates missing tests, iterates on failures. Self-healing loop. |

**Custom agents in this repo:**

This repository defines purpose-built agents that specialize in each phase:

| Agent | Phase | Purpose |
|-------|-------|---------|
| `API Specialist` | Develop | Expert in REST API design, database schema, and endpoint implementation |
| `API Test Writer` | Test | Writes comprehensive tests for a specific API route and verifies 80% coverage |
| `BDD Specialist` | Plan + Test | Creates Gherkin features and Playwright automation with full coverage matrices |
| `tdd-red` | Test | Writes failing tests from user requirements (TDD Red phase) |
| `tdd-green` | Develop | Implements minimal code to make failing tests pass (TDD Green phase) |
| `tdd-blue` | Develop | Reviews and refactors code after tests pass (TDD Blue phase) |

**How handoffs work between phases:**

1. **Plan → Develop:** Use Plan Mode to produce a structured plan. Export it to markdown or use the `/handoff` custom prompt to create a clean context document. Hand it to Agent Mode or `/handoff-to-copilot-coding-agent` for async implementation.
2. **Develop → Test:** Once code is generated, point the testing agents at the new files. The `API Test Writer` agent takes a route file path and generates comprehensive tests. The TDD agents (`red` → `green` → `blue`) chain together for a test-driven workflow.
3. **Test → Iterate:** If tests fail, Copilot's self-healing loop reads failures and fixes the implementation — the same red-green-refactor cycle a developer would follow, but automated.

### How

#### Part 1: The "Plan" Agent — Planning Mode (5 min)

1. Open Copilot Chat and switch to **Plan** mode.
2. Enter:

   ```txt
   I need to add a new feature to track delivery vehicles assigned to branches. Each vehicle has a type (truck, van, motorcycle), a license plate, capacity in kg, and a current status (available, in-transit, maintenance).
   ```

3. Show Copilot exploring the codebase, asking clarifying questions, and producing a structured plan.
4. Answer one or two questions, then choose **Open in Editor** to export the plan as a markdown file.
5. Explain: "This plan is now a version-controlled artifact. We can review it in a PR, share it with the team, or hand it off to a development agent."

#### Part 2: The "Develop" Agent — Agent Mode + Skills (5 min)

1. Show the `api-endpoint` Agent Skill — open [`.github/skills/api-endpoint/SKILL.md`](../../.github/skills/api-endpoint/SKILL.md).
2. Explain: "This skill is the development agent's playbook. It tells Copilot exactly how to implement a new endpoint — model, repository, routes, migration, seed data, tests — following our patterns."
3. Switch to **Agent** mode and prompt:

   ```txt
   Add a new API endpoint for DeliveryVehicle. Vehicles belong to branches.
   ```

4. Show Copilot following the skill's workflow — it creates all the required files in the correct locations with the correct patterns.
5. *(Optional)* Show the `/handoff-to-copilot-coding-agent` prompt to demonstrate handing off to CCA for async implementation.

#### Part 3: The "Test" Agent — Self-Healing Tests (5 min)

1. Point Copilot at the generated code and prompt:

   ```txt
   Run tests, analyze coverage, and add missing tests for the DeliveryVehicle endpoint.
   ```

2. Show the self-healing loop — Copilot runs tests, reads failures, fixes the code, re-runs.
3. Show coverage improvement.
4. *(Optional)* Mention the TDD agents: "For teams that prefer test-driven development, we have `tdd-red` (write failing tests), `tdd-green` (make them pass), and `tdd-blue` (refactor) agents that chain together."

> **Decision for ImageTrend:** Define at least three agent roles — Plan, Develop, Test — before the kickoff. For each role, decide: which Copilot mode does it use? What skills/instructions does it follow? How does it hand off to the next phase? Start with the agents shown here (`API Specialist`, `API Test Writer`, `BDD Specialist`, TDD chain) and customize them to ImageTrend's patterns. Document the pipeline so every developer follows the same flow.

---

# 5. Protocols for and Working with AI Tools and Overlap

- **The Decision:** What are your team's rules of engagement when humans and AI agents are writing code simultaneously? How do you prevent merge conflicts, duplicated work, and inconsistent quality?
- **Why This Matters Now:** Without protocols, your first week with CCA will produce merge conflicts, stepping on each other's work, and arguments about whether AI-generated code is "good enough." These protocols need to be agreed on _before_ the first AI-generated PR lands.

### Talk Track — The Four Protocols Every Team Needs

When ImageTrend starts using the Copilot Coding Agent alongside human developers, you'll have two types of contributors making branches, writing code, and opening PRs. Without clear rules, you get collisions. Here are the four protocols we recommend ImageTrend define before kickoff:

### How

#### Part 1: Branch Strategy & Conflict Prevention (3 min)

1. Open a terminal and show the branch naming convention:

   ```sh
   git branch -a
   ```

2. Explain the convention:

   | Branch Creator | Naming Pattern | Example |
   |---------------|---------------|---------|
   | Human developer | `feature/<description>` | `feature/add-delivery-tracking` |
   | Copilot Coding Agent | `copilot/fix-<number>` (auto-generated) | `copilot/fix-42` |
   | Agentic Workflow | `workflow/<name>` | `workflow/daily-report` |

3. Explain the rules:
   - **CCA gets issue ownership** — When Copilot is assigned to an issue, the files related to that issue belong to the agent. Don't touch them on another branch until the agent's PR is merged or closed.
   - **Short-lived branches** — Both human and AI branches should be short-lived. Merge frequently to minimize drift.
   - **Rebase before merge** — All PRs rebase onto `main` before merge to keep history linear and reduce conflict surface.

4. Show the GitHub Projects board with a **Squad** or **Assignee** filter — demonstrate how the team tracks who (human or AI) is working on what.

#### Part 2: Context Overlap Management (2 min)

1. Show a scenario: open the GitHub Projects board and point to two issues that touch the same area of code.
2. Explain the protocol:
   - **Check the board first** — Before starting work, check if CCA or another developer is already working on related files.
   - **Communicate via issue comments** — If you need to touch files that CCA is working on, leave a comment on the issue: "Pausing CCA — I need to make a manual change to `SupplierController.java`."
   - **Use `CODEOWNERS`** — The `.github/CODEOWNERS` file ensures the right humans are always required reviewers, even on AI-generated PRs.

#### Part 3: AI Output Review Standards (3 min)

1. Open a PR from `copilot[bot]` (or describe what one looks like).
2. Walk through the review checklist for AI-generated code:

   | Check | Required? | Who |
   |-------|-----------|-----|
   | CI passes (build, test, lint) | ✅ Automated | GitHub Actions |
   | CodeQL scan passes | ✅ Automated | GHAS |
   | Agentic PR review (docs + tests) | ✅ Automated | Agentic Workflow |
   | Business logic correctness | ✅ Manual | 2 human reviewers |
   | Test assertions are meaningful | ✅ Manual | 1 human reviewer |
   | No hallucinated imports/packages | ✅ Manual | 1 human reviewer |
   | Follows existing patterns | ✅ Manual | 1 human reviewer |

3. Explain: "AI code passes the same bar as human code — automated checks first, then human review. No shortcuts."

#### Part 4: Prompt Hygiene (2 min)

1. Open the `.github/prompts/` directory and show the prompt files.
2. Explain the standards:
   - **All prompts are version-controlled** — They go through PR review like code.
   - **Test prompts across models** — A prompt that works on GPT-4.1 should also work on Claude Sonnet. Test before committing.
   - **Keep prompts DRY** — If two prompts share logic, extract the shared part into a custom instruction or skill.
   - **Document expected output** — Each prompt should have a comment block describing what it produces and any known limitations.

> **Decision for ImageTrend:** Document these four protocols in a team agreement (e.g., `docs/ai-collaboration-protocol.md`) and reference them in your `.github/copilot-instructions.md`. Agree on them _before_ developers start using CCA. Treat AI agents as team members with the same rules — not as magic tools that bypass process.

---

# 6. PR Review Rules: At Least 2 Devs Approve Every Auto-Generated PR

- **The Decision:** What review process do AI-generated PRs go through, and how do you enforce it so it can't be bypassed?
- **Why This Matters Now:** This is a governance question, not a tooling question. Leadership needs confidence that AI-generated code passes the same quality bar as human code. The answer is a _layered review model_ enforced by repository rulesets — not trust in the AI.

### Talk Track — Building a Review Model ImageTrend Can Trust

The question ImageTrend needs to answer is: _"How do we know AI-generated code is safe to ship?"_

The answer is: you don't trust the AI — you trust the _process_. Here's the layered review model we recommend:

| Layer | Reviewer | What It Catches | When |
|-------|----------|----------------|------|
| **Automated CI** | GitHub Actions | Build failures, lint errors, test regressions | On every push |
| **CodeQL + Dependabot** | GHAS | Security vulnerabilities, license violations | On every PR |
| **Agentic PR Review** | Agentic Workflow | Missing docs, missing tests, pattern violations | On every PR to target paths |
| **Copilot Code Review** | Copilot | Code quality, missing Swagger, context-aware suggestions | On assignment |
| **Human Review** | 2 team members | Business logic, architectural fit, meaningful assertions | Required before merge |

### How

#### Part 1: Repository Rulesets (4 min)

1. Navigate to GitHub.com → repo **Settings** → **Rules** → **Rulesets**.
2. Show (or create) a ruleset named `Production Protection`:
   - **Target:** Branches matching `main` and `release/*`
   - **Required pull request reviews:** `2` minimum approvals
   - **Dismiss stale reviews on new pushes:** Enabled
   - **Require review from code owners:** Enabled
   - **Required status checks:** `CI`, `CodeQL`, `Dependency Review`
   - **Block force pushes:** Enabled
3. Explain: "This ruleset applies to every PR targeting `main` — whether opened by a human, the Coding Agent, or an agentic workflow. No exceptions."

4. Show the `.github/CODEOWNERS` file:

   ```
   # Backend
   /api/                    @backend-team
   /database/               @backend-team

   # Frontend
   /frontend/               @frontend-team

   # Infrastructure
   /infra/                  @devops-team
   /.github/workflows/      @devops-team

   # AI Configuration — requires senior review
   /.github/instructions/   @tech-leads
   /.github/skills/         @tech-leads
   /.github/prompts/        @tech-leads
   ```

5. Explain: "CODEOWNERS ensures that AI configuration changes — instructions, skills, prompts — always get reviewed by tech leads. You don't want a junior dev or an AI agent silently changing the instructions that govern how Copilot generates code."

#### Part 2: Automated Quality Gates (3 min)

1. Show the agentic workflow `pr-doc-tests-check.md` — this reviews PRs for missing documentation and test coverage.
2. Show CodeQL and Dependency Review as required status checks in the ruleset.
3. Explain: "Before a human ever looks at the PR, three layers of automated review have already run. The human reviewers focus on what automation can't catch — does this make sense for our business?"

#### Part 3: Copilot Code Review (5 min)

> **Important:** Copilot Code Review findings are non-deterministic — you may not get all four finding types every time. If a finding is missing, talk through it and optionally re-assign Copilot to try again.

1. Navigate to the PR `Feature: Add ToS Download`.
2. In the **Reviewers** section on the right sidebar, assign **Copilot** as a reviewer.
3. **Show CCR running in Actions:**
   - Navigate to **Actions** → find the **Copilot Code Review** workflow run.
   - Show the **CodeQL Analysis** job — CCR runs a full CodeQL scan as part of its review, not just an LLM reading a diff.
   - Navigate to the **Autovalidate** job → find the **Run ESLint** step — CCR also runs ESLint and uses the results in its review.
   - Explain: "This isn't just an LLM reading a diff. Code Review runs _real analysis tools_ — CodeQL for security, ESLint for code quality — and synthesizes the results with its own code graph understanding."

4. **Go back to the PR and show the review findings.** CCR posts review comments — you may see up to four types:

   | Finding Type | Source | Example | Why It Matters |
   |---|---|---|---|
   | **CodeQL vulnerability** | CodeQL scan | Path traversal in the file download endpoint | Found via _semantic analysis_, not pattern matching |
   | **ESLint rule violation** | ESLint run | Linting error in new code | CI also catches this, but CCR provides a one-click fix inline |
   | **Missing Swagger docs** | `.github/instructions/api.instructions.md` | No OpenAPI annotations on the new endpoint | CCR reads your _custom instructions_ and enforces them in reviews |
   | **Code Graph context** | Code Graph (cross-file) | Issue with React Query usage not in the PR diff | CCR analyzed code _outside the PR_ using the code graph to find broader impact |

5. **Show the "Implement Suggestions" button** at the top of the review. One click hands _all_ review findings to the Copilot Coding Agent, which opens a follow-up commit fixing them.
   - Explain: "The review finds problems. The Coding Agent fixes them. The human approves the result. That's the full loop."

6. *(Optional)* **Show Copilot Group Changes:** If you have a human-created PR available (e.g., open one from the `feature-add-cart-page` branch), navigate to the **Files changed** tab — Copilot groups the diff into logical sections, making large PRs easier to review.

   > **Note:** Group Changes only appears on PRs created or edited by a human. It won't appear on the existing `Feature: Add ToS Download` PR unless you add a commit to it.

> **Decision for ImageTrend:** Configure a repository ruleset enforcing 2-reviewer approval, code owner review, and required status checks (CI, CodeQL, Dependency Review) on `main`. Create a `CODEOWNERS` file that maps paths to teams — and critically, require tech lead review for any changes to AI configuration (`.github/instructions/`, `.github/skills/`, `.github/prompts/`). Set this up _before_ CCA opens its first PR.

---

# 7. Version Control Everything: Prompts, Instructions, .md Files, Artifacts

- **The Decision:** How do you encode your team's coding standards, internal libraries, and architectural patterns so that Copilot follows them consistently — and how do you manage changes to that configuration over time?
- **Why This Matters Now:** Out of the box, Copilot only knows what's in its training data. It doesn't know ImageTrend's internal frameworks, naming conventions, or architectural decisions. Custom instructions, skills, and prompts teach Copilot how _your_ team works — and version-controlling them means the entire team stays in sync.

### Talk Track — Your AI Configuration Is Code

ImageTrend needs to decide: _how do we teach Copilot to follow our patterns?_ And just as importantly: _how do we review and evolve those instructions over time?_

The answer is a stack of version-controlled markdown files, each with a specific scope:

**Beyond instructions — the full customization stack:**

| Artifact | Location | Purpose | When It Activates |
|----------|----------|---------|-------------------|
| **Repo-wide instructions** | `.github/copilot-instructions.md` | General guidance | Always |
| **Path-scoped instructions** | `.github/instructions/*.md` | File-specific rules | When editing matching files |
| **Agent Skills** | `.github/skills/*/SKILL.md` | Task-specific playbooks | When Copilot detects a matching task |
| **Custom Prompts** | `.github/prompts/*.prompt.md` | Reusable prompt templates | When invoked by name (`/prompt-name`) |

### How

#### Part 1: Repository-Wide Instructions (3 min)

1. Open [`.github/copilot-instructions.md`](../../.github/copilot-instructions.md).
2. Walk through the key sections: architecture references, review guidance, escalation order (security → correctness → performance → maintainability → style).
3. Explain: "This file is the shared brain. Every Copilot interaction in this repo reads it. When we add a new coding standard or internal library, we update this file — and every developer's Copilot immediately knows about it."

#### Part 2: Path-Scoped Instructions (2 min)

1. Open `.github/instructions/api.instructions.md`.
2. Show the `applyTo` frontmatter — it targets API source files, migrations, seeds, and the Swagger spec.
3. Explain: "When a developer opens a backend file, these instructions activate automatically. When they switch to a frontend file, the frontend instructions kick in instead. Context-appropriate guidance, zero configuration."

#### Part 3: Agent Skills (3 min)

1. Open [`.github/skills/api-endpoint/SKILL.md`](../../.github/skills/api-endpoint/SKILL.md).
2. Show the YAML frontmatter (`name`, `description`) and the workflow steps.
3. Explain: "Skills are the next level beyond instructions. Instructions say 'how to behave.' Skills say 'how to do a specific job.' This skill teaches Copilot exactly how to implement a new API endpoint in our codebase — model, repository, routes, migration, seed, tests."

#### Part 4: Custom Prompts (2 min)

1. Show the `.github/prompts/` directory.
2. Open [`handoff-to-copilot-coding-agent.prompt.md`](../../.github/prompts/handoff-to-copilot-coding-agent.prompt.md).
3. Explain: "Prompts are reusable workflows. This one takes whatever you've planned in Chat, packages it into a GitHub issue, and assigns it to the Coding Agent — all with one command. It's version-controlled, so the whole team uses the same handoff process."

> **Decision for ImageTrend:** Before the kickoff, create your `.github/copilot-instructions.md` with ImageTrend's architecture, coding standards, and internal frameworks. Add path-scoped instructions for backend and frontend. Create at least one Agent Skill for your most repeated development pattern (e.g., adding a new API endpoint or a new React page). Version-control everything — changes to AI configuration go through PR review with tech lead approval.

---

# 8. Prototyping Tools: Capturing Comprehensive Requirements

- **The Decision:** What's the fastest way to turn a rough idea into a working prototype that stakeholders can validate — both internally and externally?
- **Why This Matters Now:** ImageTrend needs a prototyping workflow that compresses the requirements → design → prototype → feedback cycle. Traditional spec-driven development takes weeks to produce something stakeholders can react to. AI-assisted prototyping can produce a working UI in minutes, which means faster validation and fewer wasted development cycles.

### Talk Track — Choosing Your Prototyping Approach

**The question:** _How does ImageTrend go from "rough idea" to "stakeholder-validated prototype" as fast as possible?_

**Your options:**

| Approach | Speed | Fidelity | Best For |
|----------|-------|----------|----------|
| **Copilot Vision + Agent Mode** (recommended) | Minutes | High — working code | Turning wireframes/mockups into functional prototypes that run in a browser |
| **Figma → Code pipeline** | Hours | High — pixel-perfect | Teams with strong design resources and an established Figma workflow |
| **Plan Mode for requirements** | Minutes | Low — text only | Decomposing complex requirements into structured plans before building |
| **Traditional spec writing** | Days/weeks | N/A — documents | When regulatory or contractual requirements demand formal specification |

**Our recommendation:** Combine Copilot Vision with Plan Mode. Vision generates working prototypes from images. Plan Mode captures the requirements and decisions that emerge during prototyping. Together, they produce both the artifact (prototype) and the documentation (structured plan) that stakeholders need.

### How

#### Part 1: Vision + Agent Mode — Generate a Prototype (5 min)

> **Pre-requisite:** Have 1–2 wireframe or mockup images ready. These can be whiteboard photos, Figma exports, or screenshots of an existing application.

1. Run the app and show the current state — point out what's missing (e.g., no Cart page).
2. Open Copilot Chat and switch to **Agent** mode.
3. Attach the mockup image using the paperclip icon or drag/drop.
4. Enter:

   ```txt
   I need to implement a simple Cart Page based on this mockup. I also want a Cart icon in the NavBar that shows the number of items in the Cart.
   ```

5. Show Copilot analyzing the image: it detects the layout, the component structure, and the interaction patterns.
6. Watch as Copilot generates the components — new files, modified routes, updated navigation.
7. Accept the changes and show the working prototype in the browser.

> **Tip:** Use the [`demo-cart-page.prompt.md`](../../.github/prompts/demo-cart-page.prompt.md) custom prompt for a one-click automated version of this demo.

#### Part 2: Plan Mode — Decompose Requirements (3 min)

1. Switch to **Plan** mode.
2. Paste a requirement:

   ```txt
   We need a delivery tracking page that shows real-time status updates for all active deliveries.
   The page should include a map view, a filterable list of deliveries, and status badges
   (pending, in-transit, delivered, failed). Drivers should be able to update status from their mobile device.
   ```

3. Show Copilot asking clarifying questions — "Should the map use a specific provider?", "Does the driver update via a mobile app or responsive web?", "Should delivery history be retained?"
4. Answer the questions and show the structured plan Copilot produces.
5. Explain: "This plan is now a requirements document. We can export it to markdown, attach it to a GitHub Issue, and share it with stakeholders for review."

#### Part 3: The Feedback Loop (4 min)

The prototype is only valuable if stakeholders can react to it and their feedback flows back into the next iteration. ImageTrend needs a defined loop — not ad-hoc emails or Slack threads that get lost.

**The loop:**

```
Wireframe/Idea → Copilot generates prototype → Deploy/run locally
       ↑                                              ↓
   Iterate with                               Stakeholders review
   Agent Mode or CCA                          (internal or external)
       ↑                                              ↓
       └──────── Feedback captured in GitHub Issue ←───┘
```

1. **Show internal validation:**
   - Run the prototype locally (or point to the running app after Part 1).
   - Open the repository on GitHub → **Issues** → create a new Issue:

     ```
     Title: Cart Page — Stakeholder Feedback
     Type: Task
     Body: Prototype deployed from branch `feature/cart-page`.
           Preview: [link to local or staging URL]

           ## Feedback requested on:
           - [ ] Layout and component placement
           - [ ] Cart item interaction (add/remove/quantity)
           - [ ] Checkout flow — is it sufficient for v1?
           - [ ] Missing functionality
     ```

   - Explain: "This Issue becomes the single place where all feedback lives. No Slack threads, no email chains. Every comment is timestamped, attributable, and linked to the code."

2. **Show external validation:**
   - Explain the deployment options for sharing prototypes externally:

     | Option | Setup Effort | Best For |
     |--------|-------------|----------|
     | **Azure Static Web App staging slot** | Low — auto-deploys from PR branch | Long-running preview environments that update on every push |
     | **Vercel / Netlify preview deployments** | Low — auto-deploys from PR | Quick throwaway previews per PR |
     | **GitHub Pages** | Minimal — static export | Simple prototypes without API dependencies |
     | **Docker Compose on a shared VM** | Medium | Full-stack prototypes that need the API running |

   - Explain: "For ImageTrend, we recommend Azure Static Web App staging slots — they auto-deploy from PR branches, so every prototype gets a shareable URL that updates on every push. External stakeholders get a link; they leave feedback in the GitHub Issue."

3. **Show the iteration cycle:**
   - Open the Issue and add a mock comment as if a stakeholder left feedback:

     ```
     The cart total should show tax separately. Also, we need a "Save for Later" option.
     ```

   - Show two paths to iterate:
     - **Quick iteration:** Open Copilot Chat in Agent Mode, reference the Issue, and prompt:

       ```txt
       Based on the feedback in Issue #XX, update the Cart page to show tax
       as a separate line item and add a "Save for Later" button for each cart item.
       ```

     - **Async iteration:** Use `/handoff-to-copilot-coding-agent` to create a new Issue from the feedback and let CCA implement overnight.

   - Explain: "The feedback is in the Issue. The implementation is in the PR. The PR links back to the Issue. Everything is traceable — from the original wireframe to the stakeholder feedback to the code change."

4. **Show traceability:**
   - On the PR, show the linked Issue in the sidebar.
   - On the Issue, show the linked PR.
   - Explain: "Six months from now, when someone asks 'why does the cart show tax separately?', you can trace it back to this Issue, this stakeholder comment, and this PR. That's the value of keeping requirements and prototypes in the same platform."

> **Decision for ImageTrend:** Adopt Vision + Agent Mode as your primary prototyping tool. Prepare wireframes (whiteboard, Figma, or screenshots of competitor products) before the kickoff. Establish a feedback loop: prototype → stakeholder review via GitHub Issue comments → iterate. For external validation, deploy prototypes to an Azure Static Web App staging slot so stakeholders get a shareable URL. Every piece of feedback becomes a tracked Issue comment that links to the code change that addresses it.

---

# 9. Ingest ImageTrend Design System and Shared Component Library

- **The Decision:** How do you ensure that every AI-generated UI component automatically follows ImageTrend's brand guidelines, design tokens, and component patterns — without manual correction after every generation?
- **Why This Matters Now:** Without design system integration, Copilot generates generic-looking components with default Tailwind classes. Your designers will reject them, your developers will waste time manually applying brand styles, and you'll lose the speed advantage AI provides. This is the difference between "AI generates code we can ship" and "AI generates code we have to restyle."

### Talk Track — Making AI Brand-Aware

ImageTrend has an existing design system. The question is: _how do you get it into Copilot's head?_

**The integration has three layers — each one adds more enforcement:**

**The integration approach has three layers:**

| Layer | What It Does | Where It Lives |
|-------|-------------|----------------|
| **Design system instructions** | Tells Copilot the tokens, patterns, and naming conventions | `.github/instructions/design-system.instructions.md` |
| **Tailwind configuration** | Bakes design tokens into the build tool so they're enforced at compile time | `frontend/tailwind.config.js` |
| **Component skill** *(optional — create if needed)* | Provides full component templates for complex, reusable patterns | `.github/skills/imagetrend-design-system/SKILL.md` *(does not exist yet — create if your patterns are complex enough to warrant a full skill)* |

**What goes in the design system instructions:**

- **Color palette** — Primary, secondary, accent, neutral, semantic (success, warning, error) with Tailwind class names
- **Typography scale** — Font families, sizes, weights, line heights
- **Spacing** — Consistent spacing scale (4px base, or 8px, etc.)
- **Component naming** — e.g., `IT` prefix for all shared components: `ITButton`, `ITCard`, `ITModal`
- **Icon system** — Which icon library, how icons are sized and colored
- **Accessibility requirements** — Minimum contrast ratios, ARIA patterns, keyboard navigation

### How

#### Part 1: Create the Design System Instruction File (3 min)

> **Pre-requisite:** Obtain the ImageTrend design system assets from the UX team — design tokens, component specs, brand guidelines.

1. Open (or create) `.github/instructions/design-system.instructions.md`.
2. Show the frontmatter:

   ```yaml
   ---
   description: "ImageTrend design system tokens and component patterns for frontend code."
   applyTo: "frontend/src/**"
   ---
   ```

3. Walk through the instruction content:

   ```markdown
   ## Color Palette
   Use ImageTrend brand colors via Tailwind custom classes:
   - Primary: `it-blue-600` (#1E40AF) — buttons, links, active states
   - Secondary: `it-gray-700` (#374151) — body text
   - Accent: `it-amber-500` (#F59E0B) — highlights, badges
   - Background: `it-gray-50` (#F9FAFB) — page backgrounds
   - Error: `it-red-600` (#DC2626) — validation, destructive actions

   ## Typography
   - Headings: Inter, semi-bold, tracking-tight
   - Body: Inter, regular, text-base (16px)
   - Monospace: JetBrains Mono (code blocks only)

   ## Component Naming
   - Prefix all shared components with `IT`: `ITButton`, `ITCard`, `ITBadge`
   - Page components: `<Entity>Page.tsx` (e.g., `DeliveryPage.tsx`)
   - Entity components: `<Entity>List.tsx`, `<Entity>Detail.tsx`, `<Entity>Form.tsx`

   ## Spacing
   - Use Tailwind's default spacing scale (4px base)
   - Page padding: `p-6` on desktop, `p-4` on mobile
   - Card gap: `gap-4` between cards, `gap-6` between sections
   ```

4. Explain: "This file activates whenever a developer (or Copilot) works on any file under `frontend/src/`. Every generated component will use `it-blue-600` instead of generic `blue-500`, name components with the `IT` prefix, and follow our spacing conventions."

#### Part 2: Update Tailwind Configuration (2 min)

1. Open `frontend/tailwind.config.js`.
2. Show how design tokens are added to the theme:

   ```js
   theme: {
     extend: {
       colors: {
         'it-blue': { 600: '#1E40AF', 500: '#2563EB', 400: '#3B82F6' },
         'it-gray': { 700: '#374151', 100: '#F3F4F6', 50: '#F9FAFB' },
         'it-amber': { 500: '#F59E0B' },
         'it-red': { 600: '#DC2626' },
       },
       fontFamily: {
         sans: ['Inter', 'system-ui', 'sans-serif'],
         mono: ['JetBrains Mono', 'monospace'],
       },
     },
   }
   ```

3. Explain: "The instructions tell Copilot _what_ to use. The Tailwind config ensures those tokens actually _exist_ at build time. If Copilot generates `bg-it-blue-600`, it compiles. No guessing."

#### Part 3: Generate a Component with the Design System (3 min)

1. Open Copilot Chat in **Agent** mode.
2. Prompt:

   ```txt
   Create a new DeliveryStatus component that shows a badge with the delivery status
   (pending, in-transit, delivered, failed) with appropriate colors.
   ```

3. Show that Copilot automatically uses:
   - The `IT` prefix: `ITDeliveryStatusBadge.tsx`
   - ImageTrend colors: `it-amber-500` for pending, `it-blue-600` for in-transit, green for delivered, `it-red-600` for failed
   - The Inter font family and correct spacing
4. Compare: without the design system instructions, Copilot would generate generic Tailwind classes. With them, it generates on-brand components.

#### Part 4: Internal Libraries — Teaching Copilot What No Public Model Knows (3 min)

This is the most powerful part of the design system story. Design tokens are one thing — but what about ImageTrend's **internal frameworks, proprietary APIs, or internal libraries** that don't exist in any public training data? Copilot has never seen them. Custom instructions fix that.

This demo uses TAO — a fictional internal observability library already documented in this repo. TAO doesn't exist anywhere in public training data. Copilot has never seen it. But watch what happens when we describe it in our instructions.

1. Open [`.github/copilot-instructions.md`](../../.github/copilot-instructions.md) and add (or show) the following section:

   > #### Additional Guidelines for REST APIs
   >
   > For REST APIs, use the following guidelines:
   >
   > - Use descriptive naming
   > - Add Swagger docs for all API methods
   > - Implement logging and monitoring using [TAO](../../docs/tao.md)
   >   - assume TAO is installed and never add the package

2. Show the [TAO documentation](../../docs/tao.md) — walk through it briefly. Point out that this is a completely made-up library with its own API, decorators, and patterns. No public model has ever been trained on it.

3. Open the Supplier controller and ask Copilot to add observability:

   ```txt
   Add observability to the Supplier route using our internal standards.
   ```

4. Show how Copilot reads the custom instructions, finds the TAO reference, reads `docs/tao.md`, and generates code that uses TAO's decorators and API — **even though TAO doesn't exist anywhere in public training data**.

5. Explain the key insight:
   - "This code won't compile because TAO is fictional — it's a made-up library we created for this demo. But that's exactly the point."
   - "Copilot generated correct usage of a library it has _never been trained on_, purely because we described it in our instructions and linked to the documentation."
   - "For ImageTrend, this means your internal shared component library, your proprietary APIs, your internal logging framework — Copilot can use all of them correctly, as long as you describe them in your custom instructions and link to the docs."
   - "This is the difference between Copilot generating _generic code_ and Copilot generating _ImageTrend code_ that uses your actual internal packages and follows your actual internal patterns."

> **Why this matters for the business:** Every organization has internal libraries, proprietary frameworks, and team-specific patterns that public AI models know nothing about. Custom instructions bridge that gap. You're not limited to what's in Copilot's training data — you can teach it anything.

#### Part 5: Accessibility Review with Third-Party Skill (2 min)

1. Explain that the Web Interface Guidelines skill (from Vercel Labs) can be installed to review generated components for accessibility:

   ```sh
   npx skills add vercel-labs/agent-skills --skill web-design-guidelines -a github-copilot
   ```

2. Show how Copilot can review the generated component for contrast ratios, ARIA labels, and keyboard navigation patterns.

> **Decision for ImageTrend:** Before the kickoff, get design system assets from your UX team (colors, typography, spacing, component specs). Encode them in a `.github/instructions/design-system.instructions.md` file with `applyTo: "frontend/src/**"`. Update your Tailwind config with custom color tokens so Copilot-generated classes compile. Optionally create a full Agent Skill with component templates if your patterns are complex. This is a one-time setup that pays dividends on every generated component.

---

# 10. Set Up Tech Toolchain for CICD, Production-Ready Code, and Deployment

- **The Decision:** What does ImageTrend's CI/CD pipeline look like, and how do you set it up so that AI-generated code goes through the same build → test → scan → deploy pipeline as human-written code?
- **Why This Matters Now:** Without CI/CD, you have no automated quality gate. AI-generated code ships unchecked. With CI/CD, every PR — whether from a human or from the Copilot Coding Agent — must pass the same pipeline before it can merge. This is the foundation of trust in AI-assisted development.

### Talk Track — The CI/CD Foundation for AI-Assisted Teams

ImageTrend needs to answer two questions:

1. **What pipeline does code go through before it ships?** — Build, test, lint, security scan, deploy. This should be non-negotiable for both human and AI code.
2. **Can Copilot help _build_ the pipeline itself?** — Yes. Copilot can generate GitHub Actions workflows, Bicep/Terraform templates, and even agentic workflows from natural language.

**Here's what a production-ready setup looks like and how Copilot helps you get there faster:**

**What this repo already has:**

| Workflow | Purpose |
|----------|---------|
| [`ci.yml`](../../.github/workflows/ci.yml) | Build + test on every push and PR |
| [`build-and-publish.yml`](../../.github/workflows/build-and-publish.yml) | Build and publish container images |
| [`deploy.yml`](../../.github/workflows/deploy.yml) | Deployment pipeline with environment approvals |
| [`codeql-advanced.yml`](../../.github/workflows/codeql-advanced.yml) | CodeQL security scanning |
| [`copilot-setup-steps.yml`](../../.github/workflows/copilot-setup-steps.yml) | Dev environment setup for the Copilot Coding Agent |

**Agentic workflows — the next evolution:**

In addition to traditional YAML workflows, this repo uses **agentic workflows** — markdown files that describe what an AI agent should do, compiled to Actions YAML:

| Agentic Workflow | Purpose |
|-----------------|---------|
| [`daily-repo-activity-summary.md`](../../.github/workflows/daily-repo-activity-summary.md) | Scheduled daily repo report |
| [`pr-doc-tests-check.md`](../../.github/workflows/pr-doc-tests-check.md) | PR quality gate for docs + tests |
| [`auto-analyze-failures.md`](../../.github/workflows/auto-analyze-failures.md) | Auto-triage failed workflow runs |

### How

#### Part 1: Existing CI Pipeline (3 min)

1. Navigate to the **Actions** tab on GitHub.
2. Show a recent `CI` workflow run — green checks, test results, build output.
3. Open the workflow file `ci.yml` and show the structure: trigger, jobs, steps.
4. Point out the reusable patterns: `actions/checkout@v4`, `actions/setup-java`, pinned action versions.

#### Part 2: Generate Deployment Infrastructure with Copilot (4 min)

1. Open Copilot Chat in **Agent** mode.
2. Add [`docs/deployment.md`](../../docs/deployment.md) as context.
3. Prompt:

   ```txt
   Generate the Bicep templates and GitHub Actions deployment workflow according to the deployment plan in docs/deployment.md.
   ```

4. Show the generated artifacts:
   - **Bicep templates** — Azure Container Apps infrastructure
   - **GitHub Actions YAML** — deployment pipeline with staging → production environment approvals
5. Accept the changes and explain: "We'd commit these, push, and the pipeline runs automatically. Environment protection rules require manual approval before production deployment."

#### Part 3: Agentic Workflows (3 min)

1. Show the agentic workflow file `daily-repo-activity-summary.md` — point out the YAML frontmatter (triggers, permissions, tools, safe-outputs) and the markdown body (natural language instructions).
2. Navigate to **Actions** and trigger the **Daily Repo Activity Summary** workflow manually.
3. While it runs, explain: "The agent reads the repo, gathers data on recent issues and PRs, and creates a structured summary issue. The `safe-outputs` section limits it to creating at most 1 issue — this prevents runaway agents."
4. Show the generated issue when complete.

> **Decision for ImageTrend:** Set up your CI pipeline (build + test + lint + CodeQL) _before_ the kickoff. This is the quality gate that gives the team confidence in AI-generated code. Use Copilot to generate the deployment pipeline (Bicep + Actions) rather than writing it by hand. Consider agentic workflows for operational automation (daily summaries, PR quality gates, failure triage) — these are quick wins that demonstrate the value of the platform beyond coding.

---

# 11. Test Agents: Unit, Integration, Security, and Regression/Automation

- **The Decision:** What does ImageTrend's testing strategy look like when AI agents can generate, run, and self-heal tests autonomously? How do you ensure comprehensive coverage across unit, integration, security, and regression layers?
- **Why This Matters Now:** Testing is where AI delivers the most immediate, measurable ROI. But the ROI depends on _how_ you structure the testing pipeline. Without a strategy, you get AI-generated tests that pass but don't assert meaningful behavior. With a strategy, you get a self-sustaining testing pipeline that continuously improves coverage.

### Talk Track — Designing the AI-Powered Testing Pyramid

ImageTrend needs to decide: _what does each layer of the testing pyramid look like, and which AI agent owns each layer?_

Here's the framework:

| Layer | Agent / Tool | What It Does |
|-------|-------------|-------------|
| **Unit Tests** | Agent Mode, `API Test Writer` agent | Generates tests following existing conventions, runs them, fixes failures, verifies coverage |
| **Security Tests** | GHAS (CodeQL, Dependabot, Secret Scanning) + Copilot Autofix | Continuous scanning with AI-powered remediation |
| **Integration Tests** | `BDD Specialist` agent, Playwright | Generates Gherkin features from requirements, creates Playwright automation |
| **Regression/Automation** | Agentic Workflows, TDD agents | Nightly E2E suite, PR quality gates, TDD red-green-blue pipeline |

### How

#### Part 1: Unit Test Coverage — Self-Healing Loop (3 min)

1. Open Copilot Chat in **Agent** mode.
2. Prompt:

   ```txt
   Run the existing test suite for the Product API, analyze the coverage report,
   and add tests for any untested branches or error scenarios.
   ```

3. Show the self-healing loop:
   - Copilot runs `mvn test` and reads the output
   - It identifies untested paths (e.g., 404 on non-existent product, validation errors)
   - It generates new test methods following the existing `ProductControllerTest` conventions
   - It re-runs the tests — if one fails, it reads the error, fixes the test, and runs again
4. Show the coverage improvement.

> **Key Takeaway:** Copilot follows the same red-green-refactor cycle a developer would, but automated. It reads your existing test conventions and generates tests that fit seamlessly.

#### Part 2: Security Tests — GHAS + Copilot Autofix (3 min)

1. Navigate to **Security** → **Code Scanning** on GitHub.
2. Show the existing alert: `Database query built from user-controlled sources` (SQL injection).
3. Click **Generate fix** — show Copilot Autofix proposing a parameterized query.
4. *(Optional)* Assign the alert to **Copilot** — the Coding Agent will open a PR with the fix, run tests, and iterate.
5. Briefly show **Secret Scanning** and **Dependabot** alerts — explain that these run continuously on every push.

> **Key Takeaway:** Security testing isn't a separate phase — it's continuous. GHAS scans on every push, Copilot generates fixes, and the Coding Agent can remediate at scale.

#### Part 3: Integration Tests — BDD + Playwright (2 min)

1. Show the `tests/features/` directory in the frontend — this is where Gherkin feature files live.
2. Show the `tests/e2e/` directory — this is where Playwright test files live.
3. Explain the BDD workflow:

   ```txt
   @BDD Specialist Generate Gherkin features and Playwright tests for the Product listing page.
   Cover: page load, product search, filtering by category, adding to cart, and empty state.
   ```

4. *(Optional)* Run the BDD Specialist agent and show it generating:
   - A `.feature` file with Gherkin scenarios
   - A Playwright test file implementing the step definitions
   - A coverage matrix showing which scenarios are covered

> **Key Takeaway:** The BDD Specialist agent generates both the specification (Gherkin) and the automation (Playwright) from a single prompt. Requirements and tests are created together, not separately.

#### Part 4: Regression & Automation — TDD Agents + Agentic Workflows (2 min)

1. Explain the TDD agent chain:

   | Agent | Phase | Input | Output |
   |-------|-------|-------|--------|
   | `tdd-red` | Write failing tests | Feature requirements | Test file with failing tests |
   | `tdd-green` | Make tests pass | Failing test file | Minimal implementation code |
   | `tdd-blue` | Refactor | Passing tests + implementation | Clean, refactored code |

2. Show the agentic workflow `pr-doc-tests-check.md` — this runs on every PR and reviews for test coverage gaps.
3. Explain: "For nightly regression, we can create an agentic workflow that runs the full E2E suite on `main`, analyzes failures, and creates issues for any regressions — all automatically."

> **Decision for ImageTrend:** Define a testing strategy with four layers: unit tests (self-healing via Agent Mode), security tests (GHAS continuous scanning), integration tests (BDD Specialist agent generating Gherkin + Playwright), and regression (agentic workflow on nightly schedule + PR quality gates). Set a coverage target (≥80%) and enforce it via required status checks. Use the TDD agent chain (`red` → `green` → `blue`) for teams that prefer test-driven development. The key insight: AI doesn't replace your testing strategy — it _automates_ it.

---

<br><br>

```
============================================================
  PRE-DEMO CHECKLIST
============================================================
```

---

<br><br>

## Pre-Demo Checklist

### Materials Ready ✅

- [x] OctoCAT Supply repo cloned and running locally
- [x] DemoWalkthrough.md sections for: BYOK, Custom Instructions, Skills, Planning Mode, CCA Handoffs, Actions, GHAS, Unit Tests
- [x] Custom agents defined: `tdd-red`, `tdd-green`, `tdd-blue`, `API Specialist`, `API Test Writer`, `BDD Specialist`
- [x] Custom prompts in `.github/prompts/`
- [x] GitHub MCP Server configured (for CCA handoff demos)
- [x] GitHub CLI installed and authenticated (`gh auth status`)
- [x] Existing GitHub Project board with issues, sprints, and views
- [x] Existing PRs for ruleset / code review demos (`Feature: Add ToS Download`)
- [x] Product vision document — [`docs/product-vision.md`](../../docs/product-vision.md) (Section 1)
- [x] Design system instruction file — [`.github/instructions/design-system.instructions.md`](../../.github/instructions/design-system.instructions.md) (Section 9)
- [x] Tailwind config updated with ImageTrend design tokens — [`frontend/tailwind.config.js`](../../frontend/tailwind.config.js) (Section 9)
- [x] CODEOWNERS file — [`.github/CODEOWNERS`](../../.github/CODEOWNERS) (Section 6)
- [x] Gherkin feature file — [`tests/features/product-navigation.feature`](../../frontend/tests/features/product-navigation.feature) (Section 11)
- [x] Playwright test — [`tests/e2e/product-navigation.spec.ts`](../../frontend/tests/e2e/product-navigation.spec.ts) (Section 11)

### Materials Needed Before Friday 🔴

- [ ] **Repository ruleset** — Configure 2-reviewer approval rule on the demo repo targeting `main` via GitHub.com → Settings → Rules → Rulesets (Section 6)
- [ ] **1–2 wireframe/mockup images** — For the prototyping/Vision demo — use whiteboard photos, Figma exports, or screenshots (Section 8)
