# Getting Started with Copilot — The Basics

> These demos introduce core Copilot features using the OctoCAT Supply app. Start here before moving to the advanced workflows below.

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

<br><br>

---

```
============================================================
  ADVANCED COPILOT FEATURES
============================================================
```

---

<br><br>

# General Copilot Features

## Demo: Enhancing Unit Tests and Coverage

### Option 1: Using Coding Agent

### Option 2: Manual Chat (For deeper explanation)

1. Ask Copilot to run tests, analyze coverage and add missing Branch tests to include tests for untested scenarios
2. Show Agent working on the tests and adding new tests for the API Branch route
3. Show Copilot "self-healing" (if tests fail)
4. Accept the changes
5. Ask Copilot to add tests for the Product route to show generation of new tests

---

## Demo: Using Vision and Agent to Generate Cart Functionality

> **Note**
>
> **Quick Start Option:** Use the [`demo-cart-page.prompt.md`](../../.github/prompts/demo-cart-page.prompt.md) custom prompt for an automated demo. This prompt will have Agent Mode implement the complete Cart Page functionality automatically with proper context and tools pre-configured.

- **What to show:** "Vibe coding" using Agent Mode and Vision to complete complex tasks, plus demonstrate custom prompt efficiency.
- **Why:** Demonstrate how Copilot Vision can detect design patterns, how Agent can understand a codebase and create complex changes over multiple files, and how custom prompts can streamline complex demos.

### Option 2: Manual Chat (For deeper explanation)

1. Run the App to show the original code. Once the site starts, click on **Products** in the NavBar and show the Product Page. Add an item to the Cart — note that nothing actually happens, except a message saying, "Added to Cart". Explain that there is no Cart in the frontend app currently.
2. Open Copilot and switch to **Plan** mode.
3. Attach the cart image using the paperclip icon or drag/drop to add it to the chat.
4. Enter the following prompt:

   ```txt
   I need to implement a simple Cart Page. I also want a Cart icon in the NavBar that shows the number of items in the Cart.
   ```

5. Highlight that Copilot has suggested changes and planned the components to add/modify.
6. *(OPTIONAL if you have the GitHub MCP Server configured):* Ask Copilot to create an issue in my repo to implement the Cart page and Cart icon.
7. Show the issue in the repo.
8. Switch to **Agent** mode in Copilot Chat. Switch to Claude 3.5 Sonnet (a good implementation model) and enter this prompt:

   ```txt
   Implement the changes.
   ```

9. Show Copilot's changes and how you can see each one and keep/reject each one.
10. Accept Copilot's suggested fixes.
11. Go back to the Frontend app. Navigate to Products. Show adding items to the cart (note the icon updating). Click on the Cart icon to navigate to the Cart page. Show the total, and adding/removing items from the cart.

> **Key Takeaway:** Custom prompts provide consistency and can encapsulate complex workflows that would otherwise require multiple manual steps.

---

## Demo: Custom Instructions and Repository Configuration

- **What to show:** Copilot's Custom Instructions feature using the existing [`.github/copilot-instructions.md`](../../.github/copilot-instructions.md) configuration.
- **Why:** Demonstrate that Copilot can be customized and personalized for internal libraries, coding standards, and team practices that don't exist in the foundational models.

### How

1. Show the existing [`.github/copilot-instructions.md`](../../.github/copilot-instructions.md) file in the repository.
2. Explain how this file provides context about:
   - Repository information (owner, repo name)
   - Architecture references (see [`docs/architecture.md`](../../docs/architecture.md))
   - Build and testing instructions

### Demo Enhanced Custom Instructions

**Option 1:** Apply the Patch Set **Copilot: Custom Instructions** which will update the custom-instructions file.

**Option 2:** Update the custom instructions file by hand, adding these additional guidelines:

> #### Additional Guidelines for REST APIs
>
> For REST APIs, use the following guidelines:
>
> - Use descriptive naming
> - Add Swagger docs for all API methods
> - Implement logging and monitoring using [TAO](../../docs/tao.md)
>   - assume TAO is installed and never add the package

3. Show the [TAO documentation](../../docs/tao.md) to demonstrate the fictional internal library.
4. Ask Copilot to add observability to the Supplier route using our internal standards.
5. Show how Copilot uses the custom instructions to implement TAO observability patterns.

> **Note:** Explain that this will not compile since TAO doesn't really exist — this demonstrates how custom instructions can reference internal frameworks.

> **Key Takeaway:** Custom instructions allow teams to encode their specific practices, internal libraries, and coding standards.

---

## Demo: Review Files for Compliance with 3p Web Interface Guidelines Using Agent Skills

- **What to show:** Copilot's ability to review code for specific web accessibility guidelines using the Web Interface Guidelines agent skill.
- **Why:** Demonstrate that Copilot can be used to enforce best practices and guidelines, such as web accessibility standards using 3p agent skills, an open standard for custom instructions.

### How

1. In the terminal, run the following command to add the Web Interface Guidelines skill to the repository:

   ```sh
   npx skills add vercel-labs/agent-skills --skill web-design-guidelines -a github-copilot
   ```

2. Open the [`SKILL.md`](../../.github/skills/api-endpoint/SKILL.md) and explain that this skill provides guidelines for accessibility, typography and other web design best practices. It fetches the latest guidelines from GitHub. For all guidelines see the [Web Interface Guidelines Documentation](https://github.com/vercel-labs/agent-skills).
3. Ask Copilot to review the UI.
4. Show how Copilot reads the skill, fetches the guidelines, and reviews the code.
5. Once review is complete, share some findings and optionally ask Copilot to fix some issues.

> **Key Takeaway:** Copilot supports 3p agent skills to customize Copilot Chat.

---

# Security

## Demo: Copilot and Application Security

- **What to show:** Copilot's ability to understand and remediate security vulnerabilities.
- **Why:** Demonstrate that Copilot can be used to scale AppSec by bringing security expertise to Developers directly.

### How

1. Open Copilot Chat and switch to **Ask** mode.
2. Ask Copilot to analyze `@workspace` and check if there are obvious security vulnerabilities.
3. You should see issues like:
   - Cross-site Scripting (XSS) vulnerability
   - Command Injection Vulnerability
   - Insecure CORS Configuration
   - Missing Security Headers
   - Insecure Authentication Implementation
4. Chat with Copilot to address one of these issues: generate a fix for the vulnerability.
5. *(Optional with GitHub MCP Server):* Ask Copilot to create an issue to fix the vulnerability and select one for Copilot to create an Issue.

---

## Demo: Automating Deployment with GitHub Actions, Azure and Bicep

- **What to show:** Copilot generating Actions workflows and Infrastructure-as-Code.
- **Why:** Show Copilot's ability to automate CI/CD workflows.

### How

1. Ensure that you have run the `configure-deployment.sh` script to set up the initial infrastructure and configure the environments and vars in the repo.
2. Add the [`deployment.md`](../../docs/deployment.md) file as context.
3. Prompt Copilot Agent to generate bicep files and workflows according to the deployment plan.
4. Show generated files:
   - GitHub Actions YAML to build & test
   - GitHub Actions YAML to deploy including an approval step
5. Accept the changes.
6. Commit and push to see the pipeline execution.
7. Show the deployment.

---

## Demo: Planning Mode in VS Code

1. Open the demo repository in VS Code.
2. Open Copilot Chat and switch to **Plan** Mode.
3. Use the following prompt to kick off a planning session:

   ```txt
   I need to implement a cart feature in this application. Help me plan that.
   ```

4. Copilot will first retrieve some information and then come back with some questions. *(CAUTION: They might not be exactly the same each time, so get creative.)*
5. Answer the questions accordingly. Copilot might come back with more questions. You can decide whether to keep answering them or, at some point, turn the plan into action.
6. Choose one of the following actions:
   - **Start Implementation** — to have Agent Mode implement the plan right away.
   - **Open in Editor** — to store the plan in a markdown file.
7. Switch to **Agent** Mode and prompt Copilot to hand this over to the Coding Agent *(requires MCP to be started)*:

   ```txt
   Can you hand this plan over to coding agent?
   ```

<br><br>

---

```
============================================================
  COPILOT CODING AGENT & MISSION CONTROL
============================================================
```

---

<br><br>

# Copilot Coding Agent & Mission Control

## Copilot Coding Agent

### Demo: Using `/handoff` Custom Prompt for Session Management

- **What to show:** Using the custom `/handoff` prompt to hand off Ask/Agent work to another session with proper context preservation.
- **Why:** Demonstrate how custom prompts can control context, drop unnecessary information, and efficiently hand off work between Chat/Agent sessions or team members.

#### How

1. Open Copilot Chat and switch to **Plan** mode.
2. Enter:

   ```txt
   I want to add Personal Profile page to the app that shows the user profile and their purchases.
   ```

3. Show the output and ask Copilot to change something in the plan: for example, remove the purchases part.
4. **Explain the Context Problem:** Currently the entire conversation is in the context, which over time grows long and can consume too much of the context window. Custom prompts can solve this by creating clean handoffs.
5. **Show the Custom Prompt:** Open the [`handoff.prompt.md`](../../.github/prompts/handoff.prompt.md) file in the prompts directory. Point out:
   - The YAML frontmatter configuring it as an Agent mode prompt
   - The internal thinking process in HTML comments (not shown to user)
   - The structured template for consistent handoffs
6. **Run the Prompt:** Click the "Run" button, use Command Palette → "Prompts: Run Prompt", or type `/handoff` in the chat to execute the handoff prompt.
7. **Show Results:** Display the generated `handoff.md` document. It should contain:
   - Clean summary without noise from the conversation
   - Gathered information and requirements
   - The refined plan (without the removed purchases part)
   - Next actions for the receiving developer
8. **Complete the Handoff:** Switch to **Agent** mode, include the handoff document as context, and ask Copilot to implement the changes according to the handoff document. You can cancel after a few seconds since you don't need to show the entire implementation.
9. **Best Practices:** Explain that custom handoff prompts are valuable for:
   - Context size management
   - Clean knowledge transfer between sessions
   - Team collaboration and handoffs
   - Preserving important decisions while removing noise
10. **Cleanup:** You can revert the changes to the `handoff.md` file after the demo.

---

### Demo: Using `/handoff-to-copilot-coding-agent` Custom Prompt for Async Session Continuation

- **What to show:** Using the custom `/handoff-to-copilot-coding-agent` prompt to hand off current plan work to GitHub Copilot Coding Agent with proper context preservation.
- **Why:** Demonstrate how custom prompts can encapsulate IDE tools and MCP tools calls into a cohesive workflow.

#### How

1. Make sure that you have Remote GitHub MCP Server running.
2. Open Copilot Chat and switch to **Plan** Chat Mode.
3. Enter:

   ```txt
   I want to add Personal Profile page to the app that shows the user profile and their purchases.
   ```

4. Show the output and ask Copilot to change something in the plan: for example, remove the purchases part.
5. **Explain Time Constraints:** We have a detailed plan now, Copilot Agent can follow it and implement the desired feature, however, in order to use our time efficiently we can hand off the implementation to the Copilot Agent, allowing us to focus on other tasks (or showing other Copilot features in this demo).
6. **Show the Custom Prompt:** Open the [`handoff-to-copilot-coding-agent.prompt.md`](../../.github/prompts/handoff-to-copilot-coding-agent.prompt.md) file in the prompts directory. Point out:
   - The YAML frontmatter configuring it as an Agent mode prompt
   - The internal thinking process in HTML comments (not shown to user)
   - The structured issue template for consistent handoffs
   - Use of tools like `changes`, `create_issue`, and `assign_copilot_to_issue`
   - Show how to configure the tools (click "Configure Tools" link above `tools: []` line)
7. **Run the Prompt:**

   > **Important:** We're in the **Plan** Chat Mode now, and it has a limited set of tools available. We need to switch to **Agent** mode to use `/handoff-to-copilot-coding-agent` prompt. At the moment we cannot force switch the mode.

   Click the "Run" button, use Command Palette → "Prompts: Run Prompt", or type `/handoff-to-copilot-coding-agent` to execute the handoff prompt.

8. **Show Results:** Display the generated output. It should contain a call to GitHub MCP and a short summary with the Issue link:
   - Clean summary without noise from the conversation
   - Gathered information and requirements
   - The refined plan (without the removed purchases part)
9. Open the GitHub repository and show the new issue. Demonstrate that it's been assigned to GitHub Copilot Coding Agent and it started the session.

   > **Tip:** When viewing the issue, you can select **Claude Code** or **Codex** as the agent to handle the implementation instead of the default Copilot Coding Agent.

10. **Complete the Handoff:** You can now stop the session if you don't need this implementation for your demo.
11. **Best Practices:** Explain that custom prompts are valuable for:
    - Codifying repetitive parts of existing workflows
    - Improving the discoverability of available Copilot use cases

<br><br>

---

```
============================================================
  NEXT MAJOR DEMO BLOCK — ADD NEW CONTENT BELOW
============================================================
```

---

<br><br>


GitHub Copilot Skills Demo

> This walkthrough demonstrates how to use Agent Skills to generate high-quality, consistent code that follows specific instructions, formats or tools.

### What are Skills?

Agent Skills are folders of instructions, scripts, and resources that GitHub Copilot can load when relevant to perform specialized tasks. Skills are an open standard that works across multiple AI agents, including GitHub Copilot in VS Code, GitHub Copilot CLI, and GitHub Copilot coding agent.

**Key benefits of Agent Skills:**

- **Specialize Copilot:** Tailor capabilities for domain-specific tasks without repeating context
- **Reduce repetition:** Create once, use automatically across all conversations
- **Compose capabilities:** Combine multiple skills to build complex workflows
- **Efficient loading:** Only relevant content loads into context when needed
- **Portable:** Works across VS Code, Copilot CLI, and Copilot coding agent

### Skill Structure

Skills are defined in `.github/skills/` or `.claude/skills/` directories and contain:

- **`SKILL.md`** — The skill definition with YAML frontmatter (name, description) and detailed instructions
- **Additional resources** — Scripts, examples, templates, and reference documentation

---

## Demo: Using the `api-endpoint` Skill to Add a New Entity

- **Why:**
  - **Consistency:** New developers (or AI assistants) need specialized knowledge to produce high-quality results. Skills encode institutional knowledge so that code generation is consistent and bespoke.
  - **Reduced Review Overhead:** When code generation follows established patterns, reviewers can focus on business logic rather than style/convention fixes.
  - **Faster Onboarding:** New team members can use Skills to understand how things are done in your codebase.
  - **Scalability:** As the codebase grows, Skills ensure consistency, producing higher-quality results and making the codebase easier to maintain.

- **What to Show:**
  - **The Skill Definition:** Show the `api-endpoint` skill structure and explain how it encodes a specific technical skill (in this case, creating an API endpoint)
  - **Natural Language Prompt:** Demonstrate using a simple prompt to generate a complete, production-ready API endpoint
  - **Generated Artifacts:** Show all the files Copilot creates (model, repository, routes, migration, seed data, tests)
  - **Pattern Adherence:** Highlight how the generated code follows the exact same patterns as existing code

### How

#### Part 1: Explore the Skill

1. Enable the `chat.useAgentSkills` setting in VS Code to use Agent Skills.
2. Open the [`.github/skills/api-endpoint/SKILL.md`](../../.github/skills/api-endpoint/SKILL.md) file.
3. Walk through the key sections:
   - **Architecture Overview:** Shows the layered architecture (Routes → Repository → Database)
   - **When to Use This Skill:** Trigger conditions for the skill
   - **Workflow Steps:** Step-by-step guide for creating models, repositories, routes, migrations, and seed data
   - **Patterns and Examples:** Concrete code patterns for each component
4. *(Optional)* Show the `references/database-conventions.md` file to demonstrate how supporting documentation is included.

#### Part 2: Generate the DeliveryVehicle Entity

1. Open Copilot Chat and switch to **Agent** mode.
2. Enter the following prompt:

   ```txt
   Add a new API endpoint for a new Entity called 'DeliveryVehicle'. Vehicles belong to branches.
   ```

3. Watch as Copilot:
   - Analyzes the existing codebase structure
   - References the `api-endpoint` skill automatically
   - Generates all required components following the established patterns:
     - **Model:** Generates the model using conventions
     - **Repository:** Generates the Repository with CRUD operations
     - **Routes:** Generates the route with full REST endpoints
     - **Migration:** Creates DB migrations
     - **Seed Data:** Creates seed data
     - **Tests:** Creates and runs unit tests for the new endpoint
4. Review the generated code and highlight:
   - **Naming Conventions:** Follows naming conventions for entities/methods
   - **Foreign Key Relationship:** The `branchId` field linking to the branches table
   - **API Documentation:** Complete OpenAPI annotations for all endpoints
   - **Error Handling:** Consistent use of custom errors
   - **SQL Utilities:** Using specified utils
   - **Unit Tests:** Created and verified unit tests

#### Part 3: Verify the Implementation

1. Accept the changes.
2. Run build/unit tests.
3. Open the Swagger UI at `http://localhost:3000/api-docs` and show the new DeliveryVehicle endpoints.
4. *(Optional)* Test the CRUD operations using the Swagger UI.

<br><br>

---

```
============================================================
  NEXT MAJOR DEMO BLOCK — ADD NEW CONTENT BELOW
============================================================
```

---

<br><br>


# GitHub Agentic Workflows Demo

## Demo 1: Create a New Workflow from Scratch

> **Note**
>
> This takes 10–30 minutes to complete. Start this first, then demo the existing workflows while it runs.

- **What to show:** Using CCA to create an agentic workflow from scratch.
- **Why:** Demonstrate the "vibe-coding" approach to workflow authoring.

### How

1. Open Copilot Chat in the repo **Agents** tab.
2. Choose the **Opus 4.6** model for best results.
3. Enter this prompt:

   ```txt
   Create a workflow for GitHub Agentic Workflows using https://github.com/github/gh-aw/blob/main/create.md. The purpose of the workflow is to import multi-device resolution tester agentic workflow from github/gh-aw and adapt it to test the website in this repo. Ensure that the build steps are followed in docs/build.md. Please create a pull request with these changes and ensure it can be triggered from workflow_dispatch as well as scheduled weekly.
   ```

4. Let CCA cook — it will:
   - Fetch the creation guide
   - Create the markdown file describing the intent of the workflow
   - Create the workflow file in `.github/workflows/`
5. While waiting, proceed to **Demo 2** and **Demo 3**.
6. Once complete, review the generated workflow:
   - Check the frontmatter (permissions, tools, safe-outputs)
   - Review the natural language instructions
   - Show how it references `${{ github.xxx }}` context variables
7. Merge the PR and manually trigger the workflow to show it in action.
8. Review the issue it creates.

> **Key Takeaway:** Natural language prompts + CCA = rapid workflow creation without YAML expertise.

---

## Demo 2: Daily Repo Activity Summary

- **What to show:** Scheduled autonomous reporting.
- **Why:** Demonstrate scheduled agentic automation and repository insights.
- **Setup:** The workflow `daily-repo-activity-summary.md` is already configured.

### How

1. Navigate to the **Actions** tab in GitHub.
2. Find the workflow: **"Daily Repo Activity Summary"**.
3. Click **"Run workflow"** to trigger manually.

   > **Note:** In production, this runs daily at a random time (jittered).

4. Wait for completion (~2–5 minutes).
5. Check the **Issues** tab for the new summary issue.
6. Show the generated content:
   - Issues opened/closed in last 24 hours
   - Pull requests activity
   - Notable high-activity items
   - Direct links to all referenced items
7. Show the workflow code:

   ```markdown
   ## Output Requirements

   - Title should use the configured prefix and be concise (e.g., "[daily activity] 2026-02-04 Repo Summary")
   - Body must follow the reporting format guidelines:
     - Use `###` headers or lower
     - Provide a summary section with key counts
     - Use `<details>` sections for lists of issues and PRs
   ```

8. Point out the `safe-outputs` constraint:

   ```yaml
   safe-outputs:
     create-issue:
       title-prefix: "[daily activity] "
       max: 1  # Only one issue per run
   ```

> **Key Takeaway:** Agentic workflows can autonomously gather data, synthesize insights, and create structured reports on a schedule.

---

## Demo 3: PR Documentation & Test Coverage Review

- **What to show:** Agentic workflow that reviews PRs for missing docs and tests.
- **Why:** Demonstrate how agentic workflows can enforce code quality standards as automated PR reviewers — acting as a "quality gate" that blocks merges.
- **Setup:** The workflow `pr-doc-tests-check.md` is already configured. It only triggers on PRs that modify `api/src/routes/deliveryVehicle.ts`.

> **Presenter Note:** This demo depends on having a PR that creates or modifies `api/src/routes/deliveryVehicle.ts`. You can use the Agent Skills demo for that (see Part 2: Generate the DeliveryVehicle Entity in the Skills Demo above). Otherwise, follow the steps below.

### How

1. Open Copilot Chat in **Agent** mode and prompt:

   ```txt
   Add a new "Delivery Vehicles" CRUD API endpoint to the application following existing patterns.
   ```

2. Let Copilot generate the model, repository, route, and migration.
3. Create a PR with the changes (title doesn't matter — the workflow triggers on path match).
4. Wait ~5 minutes for the agentic workflow to complete.
5. Show the PR review posted by `github-actions` (exact output varies — AI-generated, but the structure is consistent):
   - **Documentation Check** table: flags missing or outdated doc files (e.g. `api-swagger.json`, `docs/architecture.md`, `README.md`) with 🔴 High / 🟡 Medium priorities
   - **Unit Test Coverage Check** table: flags missing test files for the route and repository with 🔴 High priority
   - **Review verdict:** `REQUEST_CHANGES` (blocks merge depending on the rules defined in the rulesets)

> **Key Takeaway:** Agentic workflows can act as intelligent, context-aware code reviewers that enforce documentation and testing standards automatically on every PR.