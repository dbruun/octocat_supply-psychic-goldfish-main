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

---

## Demo: GitHub CLI (`gh`) — Your Command-Line Hub

- **What to show:** The GitHub CLI as a powerful interface for managing repos, issues, pull requests, Actions runs, and Copilot — all from the terminal.
- **Why:** Many developers live in the terminal. The GitHub CLI brings the full GitHub workflow to the command line, reducing context switches to the browser.

### Prerequisites

1. Install the GitHub CLI if not already available:

   ```sh
   winget install GitHub.cli
   ```

2. Authenticate:

   ```sh
   gh auth login
   ```

3. Verify authentication status:

   ```sh
   gh auth status
   ```

### How — Repository Operations

1. Show the current repo's details from the project root:

   ```sh
   gh repo view
   ```

2. Show that you can open the repo in the browser instantly:

   ```sh
   gh repo view --web
   ```

3. *(Optional)* Clone another repo to demonstrate discovery:

   ```sh
   gh repo clone <owner>/<repo>
   ```

### How — Issues

1. List open issues for the current repo:

   ```sh
   gh issue list
   ```

2. Create a new issue interactively:

   ```sh
   gh issue create --title "Add stock quantity to Product model" --body "Track inventory levels per product."
   ```

3. View a specific issue (replace `<number>` with the issue number from the previous step):

   ```sh
   gh issue view <number>
   ```

4. *(Optional)* Close the issue:

   ```sh
   gh issue close <number>
   ```

### How — Pull Requests

1. List open pull requests:

   ```sh
   gh pr list
   ```

2. Create a pull request from the current branch (make sure you have a branch with changes pushed):

   ```sh
   gh pr create --fill
   ```

   > `--fill` auto-populates the title and body from commit messages.

3. View PR details and checks:

   ```sh
   gh pr view <number>
   ```

4. Check out a teammate's PR locally for review:

   ```sh
   gh pr checkout <number>
   ```

### How — GitHub Actions

1. List recent workflow runs:

   ```sh
   gh run list
   ```

2. View details of a specific run (replace `<run-id>` with an ID from the list):

   ```sh
   gh run view <run-id>
   ```

3. *(Optional)* Re-run a failed workflow:

   ```sh
   gh run rerun <run-id>
   ```

### How — Copilot in the CLI

1. Install the Copilot CLI extension (one-time setup):

   ```sh
   gh extension install github/gh-copilot
   ```

2. Ask Copilot to explain a command:

   ```sh
   gh copilot explain "git rebase -i HEAD~3"
   ```

3. Ask Copilot to suggest a command for a task:

   ```sh
   gh copilot suggest "find all Java files modified in the last 7 days"
   ```

4. Show that Copilot suggests the right shell command and lets you copy, execute, or revise it.
5. *(Optional)* Ask Copilot a general programming question:

   ```sh
   gh copilot explain "what is the difference between JPA @OneToMany and @ManyToMany"
   ```

### How — Copilot Coding Agent (CCA) from the CLI

1. Create an issue that describes a feature request:

   ```sh
   gh issue create --title "Add a healthcheck endpoint to the API" \
     --body "Add a GET /api/health endpoint that returns 200 OK with a JSON body containing the app version and database connectivity status."
   ```

2. Assign the Copilot Coding Agent to the issue (replace `<number>` with the issue number):

   ```sh
   gh issue edit <number> --add-assignee @me
   gh issue edit <number> --add-assignee "copilot"
   ```

   > **Note:** Assigning `copilot` triggers the Coding Agent to pick up the issue and start a session automatically.

3. Watch the Coding Agent session start — check the issue in the browser:

   ```sh
   gh issue view <number> --web
   ```

4. Monitor the agent's progress by listing Copilot Coding Agent sessions:

   ```sh
   gh copilot-workspace list
   ```

   > If the `copilot-workspace` extension is not available, monitor via the issue timeline in the browser.

5. Once the agent opens a PR, view it from the terminal:

   ```sh
   gh pr list --author "copilot[bot]"
   ```

6. Check out the agent's PR locally and review the changes:

   ```sh
   gh pr checkout <pr-number>
   gh pr diff <pr-number>
   ```

7. *(Optional)* Add a review comment to steer the agent — it will pick up feedback and iterate:

   ```sh
   gh pr review <pr-number> --comment --body "Please also add a unit test for the healthcheck endpoint."
   ```

8. Once satisfied, approve and merge:

   ```sh
   gh pr review <pr-number> --approve
   gh pr merge <pr-number> --squash --delete-branch
   ```

### How — End-to-End CLI Workflow (Issue → CCA → Merge)

> This ties together Issues, Copilot Coding Agent, and PRs in a single terminal-driven flow.

1. Create the issue and assign Copilot in one shot:

   ```sh
   gh issue create \
     --title "Add pagination to the Products endpoint" \
     --body "Support page and size query params on GET /api/products. Default to page=0, size=20." \
     --assignee "copilot"
   ```

2. Explain that the Coding Agent is now working asynchronously — you can continue other work.
3. After the agent opens a PR, review the diff:

   ```sh
   gh pr list --author "copilot[bot]"
   gh pr diff <pr-number>
   ```

4. Run the CI checks and confirm they pass:

   ```sh
   gh pr checks <pr-number>
   ```

5. Merge when ready:

   ```sh
   gh pr merge <pr-number> --squash --delete-branch
   ```

> **Key Takeaway:** The GitHub CLI brings repos, issues, PRs, Actions, Copilot, and even the Coding Agent into a single terminal tool — letting you drive the entire development lifecycle without leaving the command line.

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

### Talk Track — Copilot and Testing

Testing is one of the areas where Copilot delivers the most immediate ROI. Writing tests is essential but often tedious — developers know they need them, but the effort of scaffolding test classes, mocking dependencies, and covering edge cases slows them down. Copilot changes that dynamic in two ways:

**Agent-driven testing** — You can point Copilot at an existing route or service and ask it to run the test suite, analyze coverage gaps, and generate the missing tests. Copilot will:
- Execute the tests and read the output
- Identify untested branches, error paths, and edge cases
- Generate new test methods that follow the existing test conventions (same assertion library, same naming patterns, same setup/teardown structure)
- Re-run the tests to verify they pass — and if they don't, self-heal by reading the failure output and fixing the code

**Self-healing loop** — This is a key concept: Copilot doesn't just generate tests and walk away. In Agent mode, it runs the tests, reads failures, diagnoses the issue, and iterates — the same red-green-refactor cycle a developer would follow, but automated.

**What makes Copilot-generated tests good:**
- They follow existing patterns in your test suite (Copilot reads your other test files for conventions)
- They cover both happy paths and error scenarios (404s, validation failures, edge cases)
- They use realistic test data that makes sense in the domain
- Custom instructions and skills can further guide test structure (e.g., "always test with and without authentication")

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

### Talk Track — Agent Mode, Vision, and Custom Prompts

This demo brings together three powerful Copilot capabilities:

**Agent Mode** is Copilot's most autonomous mode. Unlike Ask mode (which answers questions) or Edit mode (which makes targeted changes), Agent mode can:
- Read and analyze multiple files across the codebase
- Create new files and modify existing ones
- Run terminal commands (build, test, lint)
- Iterate on failures — if a build breaks or a test fails, it reads the output and fixes the issue
- Make multi-file, multi-step changes in a single conversation

Think of it as having a junior developer who can read your entire project, follow your patterns, and implement a feature end-to-end.

**Copilot Vision** adds the ability to attach images (mockups, screenshots, whiteboard photos) to a chat. Copilot analyzes the visual content and uses it as context — it can detect UI layouts, component structures, color schemes, and design patterns from an image and translate them into code.

**Custom Prompts** (`.github/prompts/*.prompt.md`) are reusable, parameterized prompt templates that you commit to your repo. They:
- Encapsulate complex multi-step workflows into a single command (e.g., `/demo-cart-page`)
- Configure which Copilot mode and tools to use via YAML frontmatter
- Ensure consistency — every team member gets the same high-quality output
- Can include variables, tool configurations, and detailed instructions that would be tedious to type every time

Together, these three features enable "vibe coding" — describing what you want in natural language (optionally with a visual reference) and letting Copilot handle the implementation details.

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

### Talk Track — What Are Custom Instructions?

Custom instructions are markdown files you commit to your repository that tell Copilot **how your team works**. Without them, Copilot only knows what's in its training data. With them, you can teach Copilot about your internal libraries, naming conventions, architectural decisions, and review standards — things no public model could know.

**There are two levels of custom instructions:**

1. **Repository-wide instructions** — A single file at `.github/copilot-instructions.md` that applies to every Copilot interaction in the repo. This is where you put:
   - High-level architecture descriptions (e.g., "this is a Spring Boot API + React frontend")
   - General review guidance and coding standards
   - Build and testing commands
   - Escalation priorities (security first, then correctness, then performance, etc.)
   - References to deeper docs rather than restating them

2. **Path-scoped instructions** — Individual files in `.github/instructions/` with YAML frontmatter that targets specific file paths. Each file has:
   - A `description` field explaining its purpose
   - An `applyTo` glob pattern that controls **when** the instructions activate (e.g., `api/src/**/*.java` for backend code, `frontend/src/**` for UI code)
   - Focused guidance that only kicks in when you're working in matching files

   For example, this repo has three path-scoped files:
   - [`api.instructions.md`](../../.github/instructions/api.instructions.md) — applies to API source, migrations, and seeds
   - [`frontend.instructions.md`](../../.github/instructions/frontend.instructions.md) — applies to React/Vite/Tailwind files
   - [`database.instructions.md`](../../.github/instructions/database.instructions.md) — applies to migration and seed files

**How to format them:**

- The repo-wide file is plain markdown — headings, bullets, code blocks. Write it like you're onboarding a new developer.
- Path-scoped files start with YAML frontmatter between `---` fences:

  ```yaml
  ---
  description: "Guidance for editing and reviewing API code changes."
  applyTo: "api/src/**/*.java, database/migrations/**"
  ---
  ```

- Keep instructions **concise and actionable** — Copilot reads them as context, so bloated files waste the context window.
- **Link to docs** instead of restating them (e.g., "see `docs/architecture.md`" rather than pasting the architecture inline).
- Use an **escalation order** so Copilot prioritizes the right things: security → correctness → performance → maintainability → style.

> **Tip:** These files are version-controlled, so your entire team benefits — and they evolve with the codebase just like any other code.

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

### Talk Track — What Are Agent Skills?

Agent skills are the next step beyond custom instructions. Where instructions tell Copilot **how to behave**, skills teach Copilot **how to do specific jobs**. Think of them as reusable playbooks — complete with step-by-step workflows, code templates, and domain expertise — that Copilot follows when a matching task comes up.

**How skills differ from instructions:**

| | Custom Instructions | Agent Skills |
|---|---|---|
| **Purpose** | General guidance and coding standards | Task-specific workflows and patterns |
| **Scope** | Always active (repo-wide) or file-path-scoped | Triggered when a matching task is detected |
| **Depth** | Concise rules and priorities | Detailed multi-step playbooks with code templates |
| **Location** | `.github/copilot-instructions.md` or `.github/instructions/` | `.github/skills/<skill-name>/SKILL.md` |

**The anatomy of a skill:**

Each skill lives in its own folder under `.github/skills/` and contains a `SKILL.md` file with:

1. **YAML frontmatter** — metadata that tells Copilot when to activate:

   ```yaml
   ---
   name: api-endpoint
   description: Generate REST API endpoints following established patterns.
     Use this skill when creating new CRUD endpoints, adding routes,
     implementing JPA repositories, or defining entity models with Swagger documentation.
   ---
   ```

   - `name` — a short identifier for the skill
   - `description` — a natural language trigger: Copilot matches your request against this text to decide whether to load the skill

2. **Workflow steps** — a structured guide Copilot follows, typically including:
   - When to use the skill (trigger conditions)
   - Step-by-step instructions (e.g., "Step 1: Define the Entity Model", "Step 2: Create the Repository")
   - Code templates and patterns to follow
   - File paths and naming conventions
   - Validation checks and testing guidance

**Where skills come from:**

- **Build your own** — Create a `.github/skills/<name>/SKILL.md` file for your team's patterns. This repo has an [`api-endpoint`](../../.github/skills/api-endpoint/SKILL.md) skill that codifies how to add new REST endpoints following the Spring Boot + JPA patterns used in this project.
- **Install third-party skills** — The community publishes skills you can add with a single command:

  ```sh
  npx skills add <owner>/<repo> --skill <skill-name> -a github-copilot
  ```

- **User-level skills** — Skills can also be installed globally (outside the repo) so they're available across all your projects via your VS Code settings. These are useful for personal productivity patterns or cross-cutting concerns like accessibility reviews.

**Key design principles for writing skills:**

- **Be specific in the description** — Copilot uses the description to decide when to activate the skill, so include concrete trigger phrases (e.g., "creating new CRUD endpoints", "adding routes").
- **Include real code templates** — Don't just describe the pattern; show the actual code Copilot should generate, using your project's actual packages, naming, and structure.
- **Keep it scoped** — One skill per concern. Don't pack API patterns, testing guidance, and deployment steps into a single skill.
- **Reference, don't duplicate** — If your skill needs context from other files (like architecture docs), tell Copilot to read them rather than copying content into the skill.

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

### Talk Track — Copilot as a Security Partner

Application security has traditionally been a bottleneck: security reviews happen late in the cycle, findings pile up in backlogs, and developers often lack the specialized knowledge to fix vulnerabilities correctly. Copilot shifts security left by bringing AppSec expertise directly into the developer's workflow.

**What Copilot can do for security:**

- **Vulnerability detection** — Ask Copilot to analyze your codebase (using `@workspace`) and it will identify common vulnerability classes: XSS, injection, insecure CORS, missing headers, broken authentication, and more. It references the actual files and lines where the issues exist.
- **Contextual remediation** — Copilot doesn't just flag problems; it generates fixes that fit your codebase's patterns. It understands your middleware stack, error handling conventions, and framework idioms, so the fix integrates cleanly.
- **Education in context** — When a developer asks "why is this a problem?", Copilot explains the attack vector, the potential impact, and the defense — right next to the vulnerable code. This builds security literacy over time.
- **Issue creation** — With the GitHub MCP Server, Copilot can create tracked issues for vulnerabilities it finds, ensuring nothing falls through the cracks.

**What Copilot is NOT:**
- It's not a replacement for SAST/DAST tools, penetration testing, or security audits
- It may miss complex, multi-step vulnerabilities that require deep application logic understanding
- It should be used as a complement to GitHub Advanced Security (CodeQL, Dependabot, secret scanning), not a substitute

**The value proposition:** Every developer gets an AppSec-aware pair programmer — no waiting for the security team's review cycle.

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

## GitHub Advanced Security (GHAS)

> For the full standalone GHAS walkthrough with additional options and edge cases, see [`ghas.md`](ghas.md).

### Talk Track — What Is GitHub Advanced Security?

GitHub Advanced Security (GHAS) is the platform's native application security suite. Where the previous demo showed Copilot as an ad-hoc security partner, GHAS provides **continuous, automated security** that runs on every push, every PR, and across your entire Git history — without a developer needing to ask.

**The three pillars of GHAS:**

1. **Code Scanning (CodeQL)** — GitHub's semantic code analysis engine. Unlike pattern-matching linters, CodeQL treats your code as data — it builds a database of your codebase and runs queries against it to find vulnerabilities like SQL injection, XSS, path traversal, and more. CodeQL understands data flow, so it can trace user input from a request parameter through your application logic to a dangerous sink.

2. **Secret Scanning** — Continuously monitors your repository (including the full Git history) for leaked credentials: API keys, tokens, passwords, certificates. It recognizes 200+ provider patterns (GitHub PATs, AWS keys, Slack tokens, etc.) and uses AI to detect generic secrets that don't match known patterns. **Push Protection** takes this further — it blocks pushes containing secrets *before* they reach the remote, preventing leaks instead of just detecting them.

3. **Dependabot** — Monitors your dependency tree for known vulnerabilities (CVEs) and outdated packages. It generates alerts with EPSS scores, CWE classifications, and remediation guidance — and can automatically open PRs to bump vulnerable dependencies to safe versions. **Dependency Review** enforces policies on PRs, blocking merges that introduce dependencies with prohibited licenses or known vulnerabilities.

**How GHAS and Copilot work together:**

- **Copilot Autofix** — When CodeQL finds a vulnerability, Copilot generates a fix directly in the alert or PR. You don't need to understand the vulnerability class deeply — Copilot proposes the remediation inline.
- **Assign alerts to the Coding Agent** — You can assign CodeQL alerts to the Copilot Coding Agent. It will open a PR with the fix, run tests, and iterate — no human coding required for routine remediations.
- **Security Campaigns** — At the organization level, you can create campaigns that group related alerts and bulk-assign them to Copilot for remediation at scale.
- **CCA + CodeQL** — The Coding Agent automatically runs CodeQL at the end of each coding session. If it introduces a vulnerability, it warns about it in its summary — and the CI scan catches it as a failed check.

**The layered defense model:**

| Layer | Tool | When |
|---|---|---|
| **IDE / Pre-commit** | Copilot Chat, MCP Secret Scanning | Before code leaves the developer's machine |
| **Push** | Secret Scanning Push Protection | At `git push` time |
| **PR** | CodeQL Autofix, Dependency Review | At pull request time |
| **Branch** | CodeQL, Dependabot alerts | Continuous on default branch |
| **Remediation** | Copilot Coding Agent, Security Campaigns | Async bulk and individual fixes |

### Demo: Code Scanning with CodeQL

- **What to show:** CodeQL detecting real vulnerabilities and Copilot Autofix generating fixes.
- **Why:** Demonstrate the shift-left security model — vulnerabilities are caught and fixed in the developer workflow, not in a separate security review.

> **Note:** Code Scanning runs in Default Setup natively. See the [full GHAS walkthrough](ghas.md#switch-to-advanced-codeql-setup) if you want to demo the advanced CodeQL setup.

#### How — Existing Alerts on Main Branch

1. Navigate to the repository's **Security** page → **Code Scanning**.
2. Show the alerts. The guaranteed one is a SQL injection: `Database query built from user-controlled sources`.
3. Click into the alert and show **Generate fix** — Copilot Autofix proposes a remediation inline.
4. *(Optional)* Show how you can **Chat** about this vulnerability directly from the alert page.

> **Note:** You may see additional alerts beyond the SQL injection. Use them if present, but don't rely on them — the demo codebase evolves and some may be fixed over time.

#### How — PR Protection (Introduced Vulnerabilities)

1. Navigate to **Pull Requests** and find the PR `Feature: Add ToS Download`.
2. Show the Code Scanning results — you should see two alerts:
   - **Uncontrolled data used in path expression** (a path traversal vulnerability — OWASP #1: Broken Access Control)
   - **Missing rate limiting**
3. Show how GHAS not only flags the vulnerability but Copilot Autofix already provides a fix inline in the PR.

#### *(Optional)* How — Live-Code a Vulnerability

1. Open Chat and enter `/code-injection` to run the code injection prompt.

   > Sometimes a model will refuse since this is "bad" — try another model and show how "responsible" Copilot is.

2. The prompt should create a new branch, modify a route to add a vulnerability, and push.
3. Create a PR and show how GHAS alerts and suggests a fix inline.

### Demo: Secret Scanning & Push Protection

- **What to show:** Secret Scanning detecting leaked credentials in Git history and Push Protection blocking secrets before they reach the remote.
- **Why:** Demonstrate that GHAS catches secrets across the full Git history — not just the current branch — and prevents new leaks proactively.

#### How — Existing Leaked Secrets

1. Navigate to **Security** → **Secret scanning**.
2. Show the alerts:
   - **Default: GitHub PAT** — Leaked in `api/.env.example` (later removed, but Secret Scanning found it in Git history)
   - **Generic: AI Detected Password** — Also in `api/.env.example`, detected by AI as a generic secret
   - **Generic: RSA Token** — Leaked in `api/ca.key`, a non-provider pattern secret

#### How — Push Protection Live Demo

1. Apply the Patch Set **GHAS: Inject Secrets**.
2. This creates `logs/debug.log` containing a leaked Anthropic API key.
3. Commit the file.
4. Open a terminal and run:

   ```sh
   git push
   ```

5. Show Push Protection blocking the push — it identifies the secret type and prevents it from reaching the remote.
6. *(Optional)* Navigate to the bypass URL in the push protection message to demo the bypass workflow.

#### *(Optional)* How — Pre-Commit Scanning via MCP

1. Apply the Patch Set **GHAS: Inject Secrets** (if not already applied).
2. With the GitHub MCP Server running, open Copilot Chat in **Agent** mode and prompt:

   ```txt
   Scan all current changes for exposed secrets and show me the files and lines I should update before I commit
   ```

3. Copilot invokes MCP secret scanning and returns the file, line number, and secret type — catching the leak *before* commit.

### Demo: Dependabot — Vulnerable Dependencies & License Compliance

- **What to show:** Dependabot alerting on known CVEs in dependencies and Dependency Review blocking PRs with prohibited licenses.
- **Why:** Demonstrate supply chain security — vulnerabilities don't just come from your code, they come from your dependencies.

#### How — Existing Vulnerability Alerts

1. Navigate to **Security** → **Dependabot**.
2. Show the guaranteed alerts:
   - **Axios v1.8.1** in [`frontend/package.json`](../../frontend/package.json) — contains [CVE-2025-27152](https://github.com/advisories/GHSA-jr5f-v2jv-69x6). Show the EPSS score, CWE, and remediation guidance.
   - **Dockerfile Alpine** in [`frontend/Dockerfile`](../../frontend/Dockerfile) and [`api/Dockerfile`](../../api/Dockerfile) — outdated Alpine version. Dependabot opens a PR with the update.

> **Note:** You may see additional dependency vulnerabilities naturally — use them to your advantage ("this is like a real project").

#### How — License Violation (Dependency Review)

1. Find the PR `feature: Add download of terms and services`.
2. Show that the **Dependency Review** required workflow failed.
3. Explain: the PR adds `ua-parser-js`, which is licensed under **AGPL-3.0** — a strong copyleft license that's explicitly denied by the Dependency Review policy. This prevents teams from accidentally introducing license-incompatible dependencies.

#### *(Optional)* How — Vulnerable Action Demo

1. Apply the Patch Set **GHAS: Inject Dependabot Vulnerable Action** (select "Yes" for creating a new branch).
2. Commit the created workflow file and create a PR.
3. Show two things:
   - Dependabot created an alert for `tj-actions/branch-names@v8.2.0` ([CVE-2025-54416](https://github.com/advisories/GHSA-gq52-6phf-x2r6))
   - The Action was blocked by the Actions Allow List (show in **Settings** → **Actions** → **General**)

### Demo: Assigning CodeQL Alerts to the Copilot Coding Agent

- **What to show:** Assigning security alerts directly to Copilot for automated remediation.
- **Why:** Demonstrate that GHAS + Copilot can remediate vulnerabilities at scale without manual developer intervention.

#### How — Assign from Alert Page

1. Navigate to **Security** → **Code scanning alerts**.
2. Find the alert `Database query built from user-controlled sources`.
3. Click **Generate Autofix** (required before assigning Copilot).
4. Assign **Copilot** from the assignee list.
5. Navigate to the linked PR or check progress from **Copilot Mission Control**.

> **Tip:** Generate the autofix before your demo to save time — Copilot can only be assigned to alerts with a generated autofix.

#### *(Optional)* How — CCA Runs CodeQL Automatically

1. Show any Copilot Coding Agent session from Mission Control.
2. Search for "CodeQL" in the session to show that the agent automatically runs CodeQL at the end of each session.
3. Explain: even if the agent doesn't fix a finding itself, it warns about it in the summary — and the CI CodeQL scan catches it as a failed check on the PR.

> **Key Takeaway:** GHAS provides continuous, automated security across code, secrets, and dependencies — and with Copilot integration, remediation scales from individual fixes to organization-wide campaigns.

---

## Demo: Automating Deployment with GitHub Actions, Azure and Bicep

- **What to show:** Copilot generating Actions workflows and Infrastructure-as-Code.
- **Why:** Show Copilot's ability to automate CI/CD workflows.

### Talk Track — What Are GitHub Actions?

GitHub Actions is GitHub's built-in CI/CD and automation platform. It lets you define workflows — automated pipelines — that run in response to events in your repository: pushes, pull requests, issue creation, scheduled timers, manual triggers, and more. Everything lives in your repo as code, so your automation is versioned, reviewed, and deployed alongside the application it supports.

**Core concepts:**

1. **Workflows** — YAML files in `.github/workflows/` that define an automation pipeline. Each workflow has:
   - A **trigger** (`on:`) — the event(s) that start it (e.g., `push`, `pull_request`, `schedule`, `workflow_dispatch` for manual runs)
   - One or more **jobs** — independent units of work that run on a runner (GitHub-hosted or self-hosted)
   - **Steps** within each job — individual commands or actions to execute in sequence

   ```yaml
   name: CI
   on:
     push:
       branches: [main]
     pull_request:
       branches: [main]

   jobs:
     build:
       runs-on: ubuntu-latest
       steps:
         - uses: actions/checkout@v4
         - name: Build
           run: make build
         - name: Test
           run: make test
   ```

2. **Actions** — Reusable building blocks you plug into steps with `uses:`. They come from:
   - **GitHub's official actions** — `actions/checkout`, `actions/setup-java`, `actions/upload-artifact`
   - **The Marketplace** — thousands of community and vendor actions for deployment, notifications, security scanning, etc.
   - **Your own repo** — custom actions in `.github/actions/` for team-specific logic

3. **Environments** — Named deployment targets (e.g., `staging`, `production`) with:
   - **Protection rules** — required reviewers, wait timers, branch restrictions
   - **Secrets and variables** — scoped to the environment so production credentials are isolated from dev

4. **Secrets and variables** — Encrypted values (`secrets.MY_SECRET`) and plain config (`vars.MY_VAR`) stored at the repo, environment, or organization level. Never hard-code credentials in workflow files.

**What this repo already has:**

This project includes several workflows in [`.github/workflows/`](../../.github/workflows/):
- [`ci.yml`](../../.github/workflows/ci.yml) — continuous integration (build + test on push/PR)
- [`build-and-publish.yml`](../../.github/workflows/build-and-publish.yml) — build and publish artifacts
- [`deploy.yml`](../../.github/workflows/deploy.yml) — deployment pipeline
- [`codeql-advanced.yml`](../../.github/workflows/codeql-advanced.yml) — GitHub Advanced Security code scanning
- [`copilot-setup-steps.yml`](../../.github/workflows/copilot-setup-steps.yml) — setup steps for the Copilot Coding Agent

**Key design principles for workflows:**

- **Keep workflows focused** — one workflow per concern (CI, deploy, security scan). Don't pack everything into a single giant file.
- **Use reusable workflows and composite actions** — extract shared logic into `workflow_call` workflows or composite actions to avoid duplication across repos.
- **Pin action versions** — use full SHA or major version tags (`actions/checkout@v4`) rather than `@main` to prevent supply chain attacks.
- **Use environments with protection rules** — require approval before deploying to production; never auto-deploy to prod from a PR.
- **Store secrets properly** — use repository or environment secrets, never commit tokens or keys to workflow files.

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

### Talk Track — What Is Planning Mode?

Copilot Chat has several modes, each suited to different tasks. Planning Mode sits between "asking questions" and "making changes" — it's designed for **thinking before doing**.

**The Copilot Chat modes progression:**

| Mode | Purpose | Actions |
|---|---|---|
| **Ask** | Learn and explore | Answers questions, explains code, no file changes |
| **Plan** | Design and strategize | Analyzes the codebase, asks clarifying questions, produces a structured plan |
| **Edit** | Targeted changes | Makes specific edits to selected files |
| **Agent** | Autonomous implementation | Reads/writes files, runs commands, iterates on failures |

**Why Plan Mode matters:**

- **Prevents premature implementation** — Instead of jumping straight into code generation, Plan Mode forces a discovery phase. Copilot explores the codebase, identifies affected files, and asks questions before proposing changes.
- **Captures requirements** — Copilot will ask clarifying questions (e.g., "should the cart persist across sessions?" or "do you want a separate Cart model or inline state?"). This surfaces decisions early, before code is written.
- **Creates a shareable artifact** — The plan can be exported to a markdown file and shared with teammates, used as a spec, or handed off to the Coding Agent for async implementation.
- **Bridges to implementation** — From a plan, you can either start implementation immediately in Agent Mode or hand it off to the Copilot Coding Agent via a custom prompt.

**When to use Plan Mode:**

- Feature planning where scope isn't fully defined
- Architectural discussions ("how should I structure this?")
- Pre-work before handing off to the Coding Agent
- Cross-team handoffs where the plan serves as documentation

### How

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

### Talk Track — What Is the Copilot Coding Agent?

The Copilot Coding Agent (CCA) is GitHub Copilot's fully autonomous coding backend. Unlike Copilot in the IDE — where you interact in real-time — the Coding Agent works **asynchronously** on GitHub's infrastructure. You assign it a task (via an issue), and it works independently: reading the codebase, writing code, running tests, and opening a pull request when it's done.

**How it works:**

1. **Trigger** — Assign the `copilot` user to a GitHub issue, or use a custom prompt to hand off work.
2. **Environment** — The agent spins up a secure, sandboxed cloud environment with your repo cloned. It uses the [`copilot-setup-steps.yml`](../../.github/workflows/copilot-setup-steps.yml) workflow to configure the environment (install dependencies, set up databases, etc.).
3. **Execution** — The agent reads the issue description, explores the codebase, plans its approach, writes code, runs builds and tests, and iterates on failures — all autonomously.
4. **Output** — It opens a pull request with its changes. You review the PR like any other — and if you leave review comments, the agent picks them up and iterates.

**When to use the Coding Agent vs. Agent Mode in the IDE:**

| | Agent Mode (IDE) | Coding Agent (Async) |
|---|---|---|
| **Interaction** | Real-time, conversational | Async — assign and walk away |
| **Where it runs** | Your local machine | GitHub's cloud infrastructure |
| **Best for** | Exploratory work, quick changes, learning | Well-scoped features, backlog items, overnight tasks |
| **Feedback loop** | Immediate (you see every step) | PR-based (review when ready) |
| **Context** | Your open files + conversation | Full repo + issue description |

**Key concepts:**

- **Setup steps** — The [`copilot-setup-steps.yml`](../../.github/workflows/copilot-setup-steps.yml) workflow tells the agent how to set up the dev environment. This is critical — if the agent can't build and test, it can't iterate.
- **MCP integration** — The GitHub MCP Server lets IDE-based Copilot create issues and assign them to the Coding Agent programmatically.
- **Model selection** — When viewing an issue assigned to the agent, you can choose which model backs the session (e.g., Claude Code, Codex, or the default).
- **Handoff patterns** — Custom prompts like `/handoff` and `/handoff-to-copilot-coding-agent` provide structured ways to transfer context from an IDE planning session to the async agent.

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

### Talk Track — What Are Agentic Workflows?

Agentic Workflows are a new category of GitHub Actions workflows where the "runner" isn't a script — it's an AI agent. Instead of writing imperative YAML steps (`run: npm test`, `uses: actions/deploy`), you write **natural language instructions** that an AI agent interprets and executes autonomously.

**How they differ from traditional Actions workflows:**

| | Traditional Workflows | Agentic Workflows |
|---|---|---|
| **Instructions** | Imperative YAML steps | Natural language markdown |
| **Execution** | Deterministic — same steps every time | Adaptive — the agent decides how to accomplish the goal |
| **Error handling** | Explicit `if: failure()` conditions | The agent reads errors and adapts its approach |
| **Maintenance** | Update YAML when tools/APIs change | Instructions stay stable; the agent adapts to changes |
| **Output** | Logs, artifacts, deployments | Issues, PR reviews, reports — plus traditional outputs |

**How they work:**

1. **Trigger** — Agentic workflows use the same event triggers as traditional workflows (`push`, `pull_request`, `schedule`, `workflow_dispatch`).
2. **Markdown instructions** — Instead of YAML steps, you provide a markdown file (in `.github/workflows/`) that describes what the agent should accomplish, what format the output should follow, and any constraints.
3. **Safe outputs** — A `safe-outputs` section in the workflow YAML constrains what the agent can do (e.g., "create at most 1 issue with this title prefix"). This is a critical safety guardrail — it prevents runaway agents from flooding your repo with issues or making unintended changes.
4. **Execution** — The agent reads your instructions, explores the repo, gathers data (using GitHub APIs, reading files, running commands), and produces the specified output.

**The three patterns this demo covers:**

- **Creation from scratch** (Demo 1) — Using the Coding Agent to author a new agentic workflow from a natural language prompt
- **Scheduled reporting** (Demo 2) — An agent that runs daily to summarize repo activity and create a structured issue
- **PR quality gates** (Demo 3) — An agent that reviews pull requests for documentation and test coverage, posting review comments and optionally blocking merges

**Key safety considerations:**

- Always use `safe-outputs` to scope what the agent can create/modify
- Use `max:` limits to prevent runaway execution
- Review generated workflows before merging — the agent writes natural language instructions, but you own the guardrails
- Agentic workflows have the same permissions model as traditional workflows (`permissions:` key in YAML)

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