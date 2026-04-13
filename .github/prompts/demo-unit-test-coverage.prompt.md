---
description: 'Demo: Improve API Test Coverage - Add Unit Tests for Missing Routes.'
tools: ['search', 'edit', 'web', 'browser/openBrowserPage', 'read', 'execute', 'azure-mcp-server/search', 'playwright/*', 'github/*']
---
# 🧪 Demo: Add Unit Tests for Product and Supplier Routes

## 📊 Current State

- Only **1 test file exists**: `ProductControllerTest.java`


## 🎯 Objective
Increase API test coverage by implementing comprehensive unit tests for Product and Supplier routes.

## 📋 Missing Test Files

### 🔗 Route Tests (High Priority)
The following route files need complete test coverage:


- [ ] `src/test/java/com/octodemo/octocatsupply/controller/ProductControllerTest.java` (expand coverage)
- [ ] `src/test/java/com/octodemo/octocatsupply/controller/SupplierControllerTest.java`


## ✅ Test Coverage Requirements

### For Each Route Test File:

- **CRUD Operations:**
  - ✅ GET all entities
  - ✅ GET single entity by ID
  - ✅ POST create new entity
  - ✅ PUT update existing entity
  - ✅ DELETE entity by ID

- **Error Scenarios:**
  - ❌ 404 for non-existent entities
  - ❌ 400 for invalid request payloads
  - ❌ 422 for validation errors
  - ❌ Edge cases (malformed IDs, empty requests)

## 🛠️ Implementation Guidelines

### Use Existing Pattern

Follow the pattern established in `src/test/java/com/octodemo/octocatsupply/controller/ProductControllerTest.java`:
```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
```

### Test Structure Template
```java
@ExtendWith(MockitoExtension.class)
class [Entity]ControllerTest {

    @Mock
    private [Entity]Repository [entity]Repository;

    @InjectMocks
    private [Entity]Controller [entity]Controller;

    @Test
    void shouldReturnAll[Entities]() { /* GET all test */ }

    @Test
    void shouldReturn[Entity]ById() { /* GET by ID test */ }

    @Test
    void shouldCreate[Entity]() { /* POST test */ }

    @Test
    void shouldUpdate[Entity]() { /* PUT test */ }

    @Test
    void shouldDelete[Entity]() { /* DELETE test */ }

    @Test
    void shouldReturn404When[Entity]NotFound() { /* Error test */ }
}
```


## 🔧 Running Tests


```bash
# Run all tests
cd api && ./mvnw test

# Run a specific test class
cd api && ./mvnw test -Dtest=ProductControllerTest

# Run tests with coverage (via JaCoCo if configured)
cd api && ./mvnw test jacoco:report
```


## 📈 Success Criteria
- [ ] Add route test files for Product and Supplier
- [ ] All tests passing in CI/CD

## 🚀 Getting Started

1. Start with `SupplierControllerTest.java` - copy `ProductControllerTest.java` pattern
2. Use `@Mock` and `@InjectMocks` for dependency injection
3. Implement basic CRUD tests first
4. Add error scenarios incrementally
5. Run `./mvnw test` after each file to track progress


## 📚 Related Files

- ERD Diagram: `api/ERD.png`
- Existing test: `api/src/test/java/com/octodemo/octocatsupply/controller/ProductControllerTest.java`
- Config: `api/pom.xml`

