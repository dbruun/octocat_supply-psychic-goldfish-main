# Error Handling Reference

This document details the error handling patterns used in the OctoCAT Supply Chain API (Java/Spring Boot).

## Error Class Hierarchy

```
RuntimeException
└── ResourceNotFoundException (404)
```

Unhandled exceptions are caught by the global handler and mapped to 500.

## Error Types

### ResourceNotFoundException

Use when an entity is not found by ID.

```java
package com.octodemo.octocatsupply.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String resource, Long id) {
		super(String.format("%s not found with id: %d", resource, id));
	}
}
```

**Usage:**
```java
throw new ResourceNotFoundException("Supplier", id);
// → "Supplier not found with id: 123" (404)

throw new ResourceNotFoundException("Product with name: " + name);
// → "Product with name: Widget" (404)
```

## Global Exception Handler

The `@RestControllerAdvice` class catches all exceptions and maps them to HTTP responses:

```java
package com.octodemo.octocatsupply.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Internal server error: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
```

## Exception Flow

1. Controller throws `ResourceNotFoundException("Supplier", 10L)`
2. `@RestControllerAdvice` intercepts the exception
3. `@ExceptionHandler` method returns JSON response
4. HTTP Status: `404 NOT_FOUND`

For unhandled exceptions:
1. Any `Exception` not caught by a specific handler
2. Generic handler returns `500 INTERNAL_SERVER_ERROR`
3. Message: `"Internal server error: <original message>"`

## JSON Error Response Format

All API errors return consistent JSON:

```json
{
  "error": "Supplier not found with id: 123"
}
```

For server errors:

```json
{
  "error": "Internal server error: Connection refused"
}
```

## Usage in Controllers

### Pattern: orElseThrow for GET/PUT/DELETE by ID

```java
@GetMapping("/{id}")
public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
    Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    return ResponseEntity.ok(supplier);
}
```

### Pattern: Update with field-by-field copy

```java
@PutMapping("/{id}")
public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier details) {
    Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));

    supplier.setName(details.getName());
    supplier.setDescription(details.getDescription());
    supplier.setActive(details.getActive());

    Supplier updated = supplierRepository.save(supplier);
    return ResponseEntity.ok(updated);
}
```

### Pattern: Delete with existence check

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
    Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    supplierRepository.delete(supplier);
    return ResponseEntity.noContent().build();
}
```

## Adding New Exception Types

If you need additional exception types (e.g., `ConflictException` for 409), follow this pattern:

1. Create a new exception extending `RuntimeException`:
```java
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
```

2. Add a handler in `GlobalExceptionHandler`:
```java
@ExceptionHandler(ConflictException.class)
public ResponseEntity<Map<String, String>> handleConflict(ConflictException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
}
```

## HTTP Status Code Reference

| Exception | Status Code | Use Case |
|-----------|-------------|----------|
| `ResourceNotFoundException` | 404 | Entity not found by ID |
| `Exception` (catch-all) | 500 | Unhandled server errors |

## Best Practices

1. **Always use `orElseThrow`** — Prefer `findById(id).orElseThrow(...)` over manual null checks
2. **Use the two-arg constructor** — `new ResourceNotFoundException("Supplier", id)` for consistent messages
3. **Don't catch exceptions in controllers** — Let the `GlobalExceptionHandler` handle them
4. **Never leak stack traces** — The global handler wraps all errors in a clean JSON response
5. **Add new exception types as needed** — Follow the existing pattern for new HTTP status codes
