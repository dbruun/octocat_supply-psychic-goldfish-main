# Code Injection Vulnerability Demo

This prompt demonstrates how to introduce a Code injection vulnerability into the Delivery API for demonstration purposes. **WARNING: This creates an intentional security vulnerability that should NEVER be used in production code.**

## Overview

We are going to add a new logic block that is supposed to show how a command can be executed when updating the status of a delivery. This execution will be vulnerable to exploitation, allowing an attacker to inject arbitrary code into the application.

### New Branch

First create a new branch for the vulnerable implementation, called `code-injection-demo`. If this branch exists, create a new one with a different name like `code-injection-demo-1` or similar.

```bash
git checkout -b <branch_name>
```

### Create a Vulnerable Block


Update `api/src/main/java/com/octodemo/octocatsupply/controller/DeliveryController.java`, adding a new `updateDeliveryStatus` endpoint. The vulnerable code uses `Runtime.getRuntime().exec()` to run a user-supplied `notifyCommand` without any sanitization:

```java
// Add this import at the top of DeliveryController.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

// Add this new endpoint inside the DeliveryController class

@PutMapping("/{id}/status")
@Operation(summary = "Update delivery status")
public ResponseEntity<?> updateDeliveryStatus(
        @PathVariable Long id,
        @RequestBody Map<String, String> statusData) {

    Delivery delivery = deliveryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Delivery", id));

    delivery.setStatus(statusData.get("status"));
    Delivery updatedDelivery = deliveryRepository.save(delivery);

    String notifyCommand = statusData.get("notifyCommand");
    if (notifyCommand != null && !notifyCommand.isEmpty()) {
        try {
            Process process = Runtime.getRuntime().exec(notifyCommand);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return ResponseEntity.ok(Map.of(
                    "delivery", updatedDelivery,
                    "commandOutput", output.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    return ResponseEntity.ok(updatedDelivery);
}
```


**This vulnerability is for educational purposes only. Never deploy code with Code injection vulnerabilities to production.**

### Push and Create a PR

```bash
git add .
git commit -m "Add Code injection vulnerability for demo"
git push origin <branch_name>
```

Then, create a pull request (PR) from the `<branch_name>` branch to the main branch in the GitHub repository.
