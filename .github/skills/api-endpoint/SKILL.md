---
name: api-endpoint
description: Generate REST API endpoints for the OctoCAT Supply Chain application following established patterns. Use this skill when creating new CRUD endpoints, adding routes, implementing JPA repositories, or defining entity models with Swagger documentation. Triggers on requests to add API features, create endpoints, implement data access layers, or extend the Spring Boot backend.
---

# API Endpoint Development

This skill guides the creation of REST API endpoints following the OctoCAT Supply Chain application's established patterns.

## Architecture Overview

The API follows a layered architecture:
```
Controllers (Spring MVC) → Repository (Spring Data JPA) → SQLite Database
       ↓                          ↓
  Models (JPA Entities)    Services (optional, for complex logic)
```

## When to Use This Skill

- Creating new CRUD endpoints for entities
- Adding a new model/entity to the system
- Implementing JPA repository interfaces for data access
- Writing OpenAPI/Swagger documentation
- Extending existing controllers with new operations

## Workflow

### Step 1: Define the Entity Model

Create a JPA entity class in `api-java/src/main/java/com/octodemo/octocatsupply/model/{EntityName}.java`.

**Pattern:**
```java
package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entity_names")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "entity_name_id")
	private Long entityNameId;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column(nullable = false)
	private Boolean active = true;
}
```

**Key conventions:**
- Primary key: `{entityName}Id` (camelCase) mapped to `{entity_name}_id` (snake_case) via `@Column(name = "...")`
- Use `Long` for IDs, `Boolean` for flags, `String` for dates (ISO format), `Double` for decimals
- Use `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` from Lombok for boilerplate
- Use `@Column(nullable = false)` for required fields
- Foreign keys stored as `Long` IDs — not object references (no `@ManyToOne` / `@OneToMany`)
- Explicit `@Column(name = "...")` for column names that differ from the Java field name

### Step 2: Create the Repository

Create `api-java/src/main/java/com/octodemo/octocatsupply/repository/{EntityName}Repository.java` as a Spring Data JPA interface.

**Basic repository (covers most cases):**
```java
package com.octodemo.octocatsupply.repository;

import com.octodemo.octocatsupply.model.EntityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityNameRepository extends JpaRepository<EntityName, Long> {
}
```

**Provides out-of-the-box:**
- `findAll()` — Get all records
- `findById(Long)` — Get by primary key (returns `Optional<EntityName>`)
- `save(EntityName)` — Create or update
- `delete(EntityName)` — Delete record
- `deleteById(Long)` — Delete by ID

**With derived query methods (if needed):**
```java
@Repository
public interface EntityNameRepository extends JpaRepository<EntityName, Long> {
	Optional<EntityName> findByName(String name);
}
```

### Step 3: Create the Controller

Create `api-java/src/main/java/com/octodemo/octocatsupply/controller/{EntityName}Controller.java` with Swagger documentation and REST handlers.

**Controller pattern:**
```java
package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.EntityName;
import com.octodemo.octocatsupply.repository.EntityNameRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entity-names")
@RequiredArgsConstructor
@Tag(name = "EntityNames", description = "API endpoints for managing entity names")
public class EntityNameController {

	private final EntityNameRepository entityNameRepository;

	@GetMapping
	@Operation(summary = "Get all entity names")
	public List<EntityName> getAllEntityNames() {
		return entityNameRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get entity name by ID")
	public ResponseEntity<EntityName> getEntityNameById(@PathVariable Long id) {
		EntityName entityName = entityNameRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EntityName", id));
		return ResponseEntity.ok(entityName);
	}

	@PostMapping
	@Operation(summary = "Create a new entity name")
	public ResponseEntity<EntityName> createEntityName(@RequestBody EntityName entityName) {
		EntityName saved = entityNameRepository.save(entityName);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an entity name")
	public ResponseEntity<EntityName> updateEntityName(@PathVariable Long id, @RequestBody EntityName entityNameDetails) {
		EntityName entityName = entityNameRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EntityName", id));

		entityName.setName(entityNameDetails.getName());
		entityName.setDescription(entityNameDetails.getDescription());
		entityName.setActive(entityNameDetails.getActive());

		EntityName updated = entityNameRepository.save(entityName);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an entity name")
	public ResponseEntity<Void> deleteEntityName(@PathVariable Long id) {
		EntityName entityName = entityNameRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EntityName", id));
		entityNameRepository.delete(entityName);
		return ResponseEntity.noContent().build();
	}
}
```

**Key conventions for controllers:**
- `@RequiredArgsConstructor` for constructor injection via Lombok
- `@Tag` for Swagger grouping, `@Operation(summary = "...")` for each endpoint
- Use `orElseThrow(() -> new ResourceNotFoundException(...))` for 404 handling
- `ResponseEntity.status(HttpStatus.CREATED).body(...)` for POST (201)
- `ResponseEntity.noContent().build()` for DELETE (204)
- Update methods explicitly set each field from the request body

### Step 4: Register the Controller

Spring Boot auto-discovers controllers via component scanning — no manual registration needed. Just ensure:
- The class is in the `com.octodemo.octocatsupply.controller` package
- The class has `@RestController` annotation

### Step 5: Create Database Migration

See `references/database-conventions.md` for migration file conventions.

### Step 6: Create Seed Data

**Always create seed data** for new entities in `api-java/database/seed/{NNN}_{entity_names}.sql`.

**Seed file naming convention:**
- Use the next sequential number (e.g., `005_categories.sql`)
- Use snake_case plural entity name
- Place in `api-java/database/seed/` directory

**Seed data best practices:**
```sql
-- 005_entity_names.sql
-- Seed data for entity_names table
-- Provides realistic test data for development and demo purposes

INSERT INTO entity_names (entity_name_id, name, description, active) VALUES
  (1, 'Example Entity 1', 'First example for testing', 1),
  (2, 'Example Entity 2', 'Second example for testing', 1),
  (3, 'Example Entity 3', 'Third example with different status', 0),
  (4, 'Example Entity 4', 'Fourth example for edge cases', 1);
```

**Guidelines for seed data:**
- Provide 3-5 realistic examples per entity
- Use explicit IDs for referential integrity with related entities
- Include variety: different statuses, dates, edge cases
- Use ISO 8601 format for dates (`YYYY-MM-DDTHH:mm:ss.sssZ`)
- Add comments explaining the purpose
- Include data that tests boundaries (empty strings, nulls where allowed, max lengths)
- Use `1` and `0` for boolean values (SQLite convention)
- For foreign keys, reference existing seed data from other tables

**Example with relationships:**
```sql
-- Reference existing suppliers (from 001_suppliers.sql)
INSERT INTO entity_names (entity_name_id, name, supplier_id, stock_quantity) VALUES
  (1, 'Widget A', 1, 100),  -- References supplier_id = 1
  (2, 'Widget B', 1, 50),   -- Same supplier
  (3, 'Widget C', 2, 200);  -- Different supplier
```

### Step 7: Create Unit Tests

**Always create unit tests** for new controllers in `api-java/src/test/java/com/octodemo/octocatsupply/controller/{EntityName}ControllerTest.java`.

See `references/testing-patterns.md` for:
- Complete test file structure and patterns
- Required test coverage for all CRUD operations
- Mock setup using Mockito and `@ExtendWith(MockitoExtension.class)`
- Commands for running tests

**Test coverage requirements:**
- ✅ All CRUD operations (create, read all, read by ID, update, delete)
- ✅ Success cases with valid data
- ✅ Edge cases (empty results, null values, boundary conditions)
- ✅ Error cases (not found scenarios)
- ✅ Mock verification (correct repository method calls)

## Naming Conventions

| Context | Convention | Example |
|---------|------------|---------|
| Java entity class | PascalCase | `Supplier` |
| Java field | camelCase | `supplierId` |
| Database table | snake_case, plural | `suppliers` |
| Database column | snake_case | `supplier_id` |
| Route path | kebab-case, plural | `/api/suppliers` |
| Repository interface | PascalCase + Repository | `SupplierRepository` |
| Controller class | PascalCase + Controller | `SupplierController` |
| Service class | PascalCase + Service | `ProductService` |
| Test class | PascalCase + Test | `ProductControllerTest` |

## Error Handling

Use the custom exception class from `exception/ResourceNotFoundException.java`:
- `ResourceNotFoundException(String resource, Long id)` — 404 responses
- `ResourceNotFoundException(String message)` — 404 with custom message

The `GlobalExceptionHandler` (`@RestControllerAdvice`) automatically catches these and returns:
```json
{ "error": "Supplier not found with id: 123" }
```

See `references/error-handling.md` for detailed patterns.

## File Locations

```
api-java/src/main/java/com/octodemo/octocatsupply/
├── model/{EntityName}.java              # JPA entity
├── repository/{EntityName}Repository.java  # Spring Data JPA interface
├── controller/{EntityName}Controller.java  # REST endpoints + Swagger
├── service/{EntityName}Service.java     # Business logic (optional)
├── exception/
│   ├── ResourceNotFoundException.java   # 404 exception
│   └── GlobalExceptionHandler.java      # Maps exceptions to HTTP responses
├── config/
│   ├── OpenApiConfig.java               # Swagger configuration
│   └── CorsConfig.java                  # CORS settings
└── util/
    └── DatabaseInitializer.java         # Migrations + seeding

api-java/database/
├── migrations/                          # Schema changes
└── seed/                                # Demo data

api-java/src/test/java/com/octodemo/octocatsupply/
└── controller/{EntityName}ControllerTest.java  # Unit tests
```

## Quick Reference: Complete Checklist

When creating a new API endpoint, ensure you complete ALL steps:

- [ ] **Entity** (`model/{EntityName}.java`) — JPA entity with Lombok + `@Column` mappings
- [ ] **Repository** (`repository/{EntityName}Repository.java`) — `JpaRepository<EntityName, Long>` interface
- [ ] **Controller** (`controller/{EntityName}Controller.java`) — REST handlers + `@Operation` docs
- [ ] **Migration** (`database/migrations/{NNN}_{description}.sql`) — CREATE TABLE statement
- [ ] **Seed Data** (`database/seed/{NNN}_{entity_names}.sql`) — 3-5 realistic examples
- [ ] **Unit Tests** (`controller/{EntityName}ControllerTest.java`) — All CRUD operations, error cases

**Do not skip seed data or unit tests** — they are required for all new endpoints.

## Additional Resources

- See `references/testing-patterns.md` for comprehensive testing guidance
- See `references/error-handling.md` for detailed error patterns
- See `references/database-conventions.md` for SQLite specifics
- See `references/swagger-patterns.md` for OpenAPI documentation
