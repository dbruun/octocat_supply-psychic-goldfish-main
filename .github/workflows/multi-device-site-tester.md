---
name: Multi-Device Site Tester
description: Tests the OctoCAT website across multiple device form factors after running documented build steps.
on:
  schedule:
    - cron: '0 9 * * 1'
  workflow_dispatch:
    inputs:
      devices:
        description: 'Device types to test (comma-separated: mobile,tablet,desktop)'
        required: false
        default: 'mobile,tablet,desktop'
permissions:
  contents: read
  issues: read
  pull-requests: read
tracker-id: multi-device-site-tester
engine:
  id: claude
  max-turns: 100
strict: true
timeout-minutes: 45
runtimes:
  node:
    version: "24"
tools:
  timeout: 120
  playwright:
    version: "v1.56.1"
  bash:
    - "make install*"
    - "make build*"
    - "make db-seed*"
    - "make dev-api*"
    - "npm run dev*"
    - "curl*"
    - "kill*"
    - "lsof*"
    - "ip*"
    - "hostname*"
    - "ls*"
    - "pwd*"
    - "cd*"
safe-outputs:
  create-issue:
    expires: 2d
    labels: [testing, automated]
network:
  allowed:
    - node
---

# Multi-Device Website Testing

You are a multi-device website testing specialist.

## Context

- Repository: ${{ github.repository }}
- Triggered by: @${{ github.actor }}
- Devices to test: ${{ inputs.devices }}
- Workspace: ${{ github.workspace }}

## Mission

Import and adapt the gh-aw multi-device tester pattern to this repository by validating the website UI across mobile, tablet, and desktop viewports.

## Step 1: Follow build steps from docs/build.md

Run the documented build flow from the repository root:

```bash
cd ${{ github.workspace }}
make install
make build
```

Then prepare runtime services:

```bash
cd ${{ github.workspace }}
make db-seed
```

Start API in background:

```bash
cd ${{ github.workspace }}
make dev-api > /tmp/api.log 2>&1 &
echo $! > /tmp/api.pid
```

Start frontend in background on documented frontend port (5137):

```bash
cd ${{ github.workspace }}/frontend
FRONTEND_PORT=5137
VITE_API_URL=http://127.0.0.1:3000 npm run dev -- --host 0.0.0.0 --port "${FRONTEND_PORT}" > /tmp/frontend.log 2>&1 &
echo $! > /tmp/frontend.pid
```

Wait until frontend is ready:

```bash
FRONTEND_PORT=5137
curl --retry 30 --retry-delay 2 --retry-connrefused --fail "http://127.0.0.1:${FRONTEND_PORT}"
```

## Step 2: Device matrix

Use `${{ inputs.devices }}` to determine which device classes to test.

- Mobile: 390x844, 428x926, 393x851, 360x800
- Tablet: 768x1024, 834x1194, 1024x1366
- Desktop: 1366x768, 1920x1080, 2560x1440

## Step 3: Run Playwright MCP checks

Use Playwright MCP tools (not npm playwright) and test against the running website.

Detect bridge IP for Playwright:

```bash
FRONTEND_PORT=5137
ROUTE_PROBE_IP=1.1.1.1
SERVER_IP=$(ip -4 route get "${ROUTE_PROBE_IP}" 2>/dev/null | awk '{print $7; exit}')
if [ -z "$SERVER_IP" ]; then SERVER_IP=$(hostname -I | awk '{print $1}'); fi
echo "Playwright URL: http://${SERVER_IP}:${FRONTEND_PORT}/"
```

For each viewport:
- Set viewport size
- Navigate to `http://${SERVER_IP}:${FRONTEND_PORT}/` using `waitUntil: 'domcontentloaded'`
- Capture screenshot
- Verify key layout checks (no major overflow/cutoff, navigation usable, primary content visible)
- Record accessibility and interaction findings

## Step 4: Report

If issues are found, create one issue titled:

`🔍 Multi-Device Site Testing Report - [Date]`

Include:
- Trigger details and workflow run URL
- Device summary (pass/warn/critical counts)
- Critical issues
- Warnings/details per device
- Accessibility findings
- Actionable recommendations

If no issues are found, call `noop` with a concise success message including device count.

Always produce a safe output (`create_issue` or `noop`).

## Step 5: Cleanup

Stop background processes:

```bash
if [ -f /tmp/frontend.pid ]; then kill "$(cat /tmp/frontend.pid)" || true; fi
if [ -f /tmp/api.pid ]; then kill "$(cat /tmp/api.pid)" || true; fi
```
