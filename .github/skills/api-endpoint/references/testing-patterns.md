# Testing Patterns for API Endpoints

This document provides comprehensive guidance for writing unit tests for API endpoints in the OctoCAT Supply Chain application (Java/Spring Boot).

## Overview

**Always create unit tests** for new controllers in `api-java/src/test/java/com/octodemo/octocatsupply/controller/{EntityName}ControllerTest.java`.

All tests use JUnit 5 + Mockito:
- `JUnit 5` — Test framework (`@Test`, `@ExtendWith`, assertions)
- `Mockito` — Mocking framework (`@Mock`, `@InjectMocks`, `when/verify`)

## Controller Unit Tests

### Test File Structure

Create tests in `api-java/src/test/java/com/octodemo/octocatsupply/controller/{EntityName}ControllerTest.java`:

```java
package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.EntityName;
import com.octodemo.octocatsupply.repository.EntityNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityNameControllerTest {

	@Mock
	private EntityNameRepository entityNameRepository;

	@InjectMocks
	private EntityNameController entityNameController;

	// Test methods...
}
```

### Required Test Coverage

Test all CRUD operations with the following scenarios:

#### GET All

```java
@Test
void shouldReturnAllEntityNames() {
	EntityName entity1 = new EntityName(1L, "Entity 1", "Description 1", true);
	EntityName entity2 = new EntityName(2L, "Entity 2", "Description 2", false);

	when(entityNameRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

	List<EntityName> result = entityNameController.getAllEntityNames();

	assertEquals(2, result.size());
	assertEquals("Entity 1", result.get(0).getName());
	assertEquals("Entity 2", result.get(1).getName());
	verify(entityNameRepository, times(1)).findAll();
}

@Test
void shouldReturnEmptyListWhenNoEntityNames() {
	when(entityNameRepository.findAll()).thenReturn(List.of());

	List<EntityName> result = entityNameController.getAllEntityNames();

	assertTrue(result.isEmpty());
	verify(entityNameRepository, times(1)).findAll();
}
```

#### GET By ID

```java
@Test
void shouldReturnEntityNameById() {
	EntityName entity = new EntityName(1L, "Entity 1", "Description 1", true);

	when(entityNameRepository.findById(1L)).thenReturn(Optional.of(entity));

	ResponseEntity<EntityName> response = entityNameController.getEntityNameById(1L);

	assertTrue(response.getStatusCode().is2xxSuccessful());
	assertNotNull(response.getBody());
	assertEquals("Entity 1", response.getBody().getName());
	verify(entityNameRepository, times(1)).findById(1L);
}

@Test
void shouldThrowWhenEntityNameNotFound() {
	when(entityNameRepository.findById(999L)).thenReturn(Optional.empty());

	assertThrows(ResourceNotFoundException.class, () -> {
		entityNameController.getEntityNameById(999L);
	});

	verify(entityNameRepository, times(1)).findById(999L);
}
```

#### POST (Create)

```java
@Test
void shouldCreateEntityName() {
	EntityName input = new EntityName(null, "New Entity", "New description", true);
	EntityName saved = new EntityName(5L, "New Entity", "New description", true);

	when(entityNameRepository.save(input)).thenReturn(saved);

	ResponseEntity<EntityName> response = entityNameController.createEntityName(input);

	assertEquals(201, response.getStatusCode().value());
	assertNotNull(response.getBody());
	assertEquals(5L, response.getBody().getEntityNameId());
	assertEquals("New Entity", response.getBody().getName());
	verify(entityNameRepository, times(1)).save(input);
}
```

#### PUT (Update)

```java
@Test
void shouldUpdateEntityName() {
	EntityName existing = new EntityName(1L, "Old Name", "Old description", true);
	EntityName details = new EntityName(null, "Updated Name", "Updated description", false);
	EntityName updated = new EntityName(1L, "Updated Name", "Updated description", false);

	when(entityNameRepository.findById(1L)).thenReturn(Optional.of(existing));
	when(entityNameRepository.save(any(EntityName.class))).thenReturn(updated);

	ResponseEntity<EntityName> response = entityNameController.updateEntityName(1L, details);

	assertTrue(response.getStatusCode().is2xxSuccessful());
	assertNotNull(response.getBody());
	assertEquals("Updated Name", response.getBody().getName());
	verify(entityNameRepository, times(1)).findById(1L);
	verify(entityNameRepository, times(1)).save(any(EntityName.class));
}

@Test
void shouldThrowWhenUpdatingNonExistentEntityName() {
	EntityName details = new EntityName(null, "Updated", "Desc", true);

	when(entityNameRepository.findById(999L)).thenReturn(Optional.empty());

	assertThrows(ResourceNotFoundException.class, () -> {
		entityNameController.updateEntityName(999L, details);
	});

	verify(entityNameRepository, times(1)).findById(999L);
	verify(entityNameRepository, never()).save(any());
}
```

#### DELETE

```java
@Test
void shouldDeleteEntityName() {
	EntityName entity = new EntityName(1L, "Entity 1", "Description", true);

	when(entityNameRepository.findById(1L)).thenReturn(Optional.of(entity));

	ResponseEntity<Void> response = entityNameController.deleteEntityName(1L);

	assertEquals(204, response.getStatusCode().value());
	verify(entityNameRepository, times(1)).findById(1L);
	verify(entityNameRepository, times(1)).delete(entity);
}

@Test
void shouldThrowWhenDeletingNonExistentEntityName() {
	when(entityNameRepository.findById(999L)).thenReturn(Optional.empty());

	assertThrows(ResourceNotFoundException.class, () -> {
		entityNameController.deleteEntityName(999L);
	});

	verify(entityNameRepository, times(1)).findById(999L);
	verify(entityNameRepository, never()).delete(any());
}
```

### Test Coverage Requirements

Your controller tests must cover:

- ✅ **CRUD operations** — Test all GET, POST, PUT, DELETE endpoints
- ✅ **Success cases** — Verify correct behavior with valid data
- ✅ **Edge cases** — Empty results, boundary conditions
- ✅ **Error cases** — Not found scenarios (404)
- ✅ **Mock verification** — Ensure correct repository methods called with correct args
- ✅ **Status codes** — Verify HTTP status codes (200, 201, 204, 404)

## Running Tests

### Command Line

```bash
# Run all tests
cd api-java && ./mvnw test

# Run all tests (via Makefile)
make test

# Run a specific test class
./mvnw test -Dtest=ProductControllerTest

# Run a specific test method
./mvnw test -Dtest="ProductControllerTest#shouldReturnAllProducts"

# Run with verbose output
./mvnw test -Dsurefire.useFile=false
```

### Test Output

Maven Surefire produces:
- Console output with pass/fail status
- XML reports in `target/surefire-reports/`
- Summary of tests run, failures, errors, skipped

## Best Practices

### Mocking
- Use `@Mock` for repository dependencies
- Use `@InjectMocks` for the controller under test
- Use `when(...).thenReturn(...)` for stubbing
- Use `verify(mock, times(N)).method(args)` to verify calls
- Use `any(Class.class)` for flexible argument matching
- Use `never()` to verify a method was NOT called

### Test Organization
- One test class per controller
- Group related tests logically (by HTTP method or behavior)
- Clear, descriptive method names using `shouldDoSomething` pattern
- Arrange-Act-Assert pattern in each test

### Assertions
- `assertEquals(expected, actual)` for value comparison
- `assertTrue(condition)` / `assertFalse(condition)` for boolean checks
- `assertNotNull(value)` for existence checks
- `assertThrows(ExceptionType.class, () -> { ... })` for error cases
- Verify HTTP status codes: `response.getStatusCode().value()` or `.is2xxSuccessful()`

### Test Data
- Use the `@AllArgsConstructor` to create entities: `new Product(1L, 1L, "Name", "Desc", 99.99, "SKU", "piece", "img.png", 0.0)`
- Use `null` for ID when testing create operations (ID is auto-generated)
- Use descriptive, realistic test data

### Edge Cases to Test
- Empty collections
- Non-existent IDs (404 scenarios)
- Null fields where allowed
- Boundary values for numeric fields

## Example: Complete Test File

See `api-java/src/test/java/com/octodemo/octocatsupply/controller/ProductControllerTest.java` for a production-ready example following all patterns.

## Related Documentation

- See `error-handling.md` for testing error scenarios
- See `database-conventions.md` for SQLite-specific testing considerations
