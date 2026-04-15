---
mode: agent
description: Start the OctoCAT Supply app (API + Frontend)
---

Start the OctoCAT Supply application by running both servers.

## Step 1: Start the API (Spring Boot on port 3000)

Run this in a terminal:

```
Set-Location "{{workspaceFolder}}/api"; .\mvnw.cmd spring-boot:run '-Dspring-boot.run.arguments=--init-db --seed-db' '-Dmaven.test.skip=true'
```

Wait until you see `Started OctocatSupplyApplication` in the output before proceeding.

## Step 2: Start the Frontend (Vite)

Run this in a **separate** terminal:

```
Set-Location "{{workspaceFolder}}/frontend"; $env:VITE_API_URL="http://localhost:3000"; npm run dev
```

Wait until you see `VITE ready` in the output.

## Step 3: Confirm

Tell the user both servers are running and provide the URLs:
- API: http://localhost:3000
- Frontend: whatever port Vite reports (usually 5137)
