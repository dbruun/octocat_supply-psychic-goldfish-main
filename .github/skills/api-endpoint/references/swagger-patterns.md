# OpenAPI/Swagger Documentation Patterns

This document details how to write OpenAPI documentation for API endpoints in the OctoCAT Supply Chain application using Spring Boot and SpringDoc OpenAPI.

## Overview

The API uses SpringDoc OpenAPI (`springdoc-openapi-starter-webmvc-ui`) to automatically generate OpenAPI 3.0 documentation from:

1. **JPA entity classes** — Schema definitions derived from field types and annotations
2. **Controller annotations** — `@Operation`, `@Tag`, `@Parameter`, `@ApiResponse`
3. **OpenAPI configuration** — App-level metadata in `OpenApiConfig.java`

Documentation is automatically available at:
- **Swagger UI**: `http://localhost:3000/api-docs`
- **OpenAPI JSON**: `http://localhost:3000/api-docs` (JSON spec)

## Schema Documentation (Entity Models)

SpringDoc automatically generates schemas from JPA entities. Enhance with `@Schema` annotations:

```java
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a supplier in the supply chain")
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	@Schema(description = "The unique identifier for the supplier", example = "1")
	private Long supplierId;

	@Column(nullable = false)
	@Schema(description = "The name of the supplier", example = "PurrTech Innovations")
	private String name;

	@Column
	@Schema(description = "Contact email for the supplier", example = "felix@purrtech.co")
	private String email;

	@Column(nullable = false)
	@Schema(description = "Whether the supplier is active", example = "true")
	private Boolean active = true;
}
```

### Property Type Mapping

| Java Type | OpenAPI Type | Format |
|-----------|--------------|--------|
| `Long` | `integer` | `int64` |
| `Integer` | `integer` | `int32` |
| `Double` | `number` | `double` |
| `String` | `string` | — |
| `Boolean` | `boolean` | — |
| `List<String>` | `array` | items: { type: string } |

## Endpoint Documentation (Controllers)

### Tags

Group related endpoints with `@Tag` on the controller class:

```java
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "API endpoints for managing suppliers")
public class SupplierController {
    // ...
}
```

### GET All (List)

```java
@GetMapping
@Operation(summary = "Get all suppliers")
public List<Supplier> getAllSuppliers() {
    return supplierRepository.findAll();
}
```

### GET One (By ID)

```java
@GetMapping("/{id}")
@Operation(summary = "Get supplier by ID")
public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
    Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    return ResponseEntity.ok(supplier);
}
```

### POST (Create)

```java
@PostMapping
@Operation(summary = "Create a new supplier")
public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
    Supplier saved = supplierRepository.save(supplier);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}
```

### PUT (Update)

```java
@PutMapping("/{id}")
@Operation(summary = "Update a supplier")
public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplierDetails) {
    Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));

    supplier.setName(supplierDetails.getName());
    supplier.setDescription(supplierDetails.getDescription());
    supplier.setActive(supplierDetails.getActive());

    Supplier updated = supplierRepository.save(supplier);
    return ResponseEntity.ok(updated);
}
```

### DELETE

```java
@DeleteMapping("/{id}")
@Operation(summary = "Delete a supplier")
public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
    Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    supplierRepository.delete(supplier);
    return ResponseEntity.noContent().build();
}
```

## Path Parameters

Use `@PathVariable` with optional `@Parameter` for documentation:

```java
@GetMapping("/{id}")
@Operation(summary = "Get product by ID")
public ResponseEntity<Product> getProductById(
        @Parameter(description = "Product ID", required = true)
        @PathVariable Long id) {
    // ...
}
```

## Query Parameters

For filtering or pagination, use `@RequestParam`:

```java
@GetMapping("/search")
@Operation(summary = "Search products by name")
public ResponseEntity<List<Product>> searchProducts(
        @Parameter(description = "Search term for product name")
        @RequestParam String name) {
    List<Product> products = productService.searchProductsByName(name);
    return ResponseEntity.ok(products);
}
```

## Custom Response Documentation

For endpoints with non-standard responses, use `@ApiResponse`:

```java
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@GetMapping("/{id}")
@Operation(summary = "Get supplier by ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Supplier found"),
    @ApiResponse(responseCode = "404", description = "Supplier not found")
})
public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
    // ...
}
```

## App-Level Configuration

The OpenAPI metadata is configured in `config/OpenApiConfig.java`:

```java
package com.octodemo.octocatsupply.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OctoCAT Supply Chain Management API")
                        .description("Java/Spring Boot implementation of the OctoCAT Supply Chain Management API")
                        .version("1.0.0"));
    }
}
```

## Best Practices

1. **Always add `@Operation(summary = "...")`** — Brief, action-oriented summary for each endpoint
2. **Use `@Tag` on controllers** — Groups endpoints logically in Swagger UI
3. **Add `@Schema` on entities** — When you need custom descriptions or examples
4. **Document error responses** — Use `@ApiResponses` for non-200 status codes
5. **Keep it simple** — SpringDoc auto-generates most documentation from types and annotations; only add explicit docs when the auto-generated version is insufficient
