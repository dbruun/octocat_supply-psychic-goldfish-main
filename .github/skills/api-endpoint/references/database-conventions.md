# Database Conventions Reference

This document details SQLite database conventions for the OctoCAT Supply Chain API (Java/Spring Boot).

## File Locations

```
api-java/
├── data/
│   └── octocat-supply.db       # SQLite database file (gitignored)
└── database/
    ├── migrations/              # Schema changes
    │   ├── 001_init.sql
    │   └── 002_add_supplier_status_fields.sql
    └── seed/                    # Demo data
        ├── 001_suppliers.sql
        ├── 002_headquarters.sql
        ├── 003_branches.sql
        └── 004_products.sql
```

## Migration Files

### Naming Convention

```
{sequence}_{description}.sql
```

- **sequence**: 3-digit zero-padded number (001, 002, etc.)
- **description**: snake_case description of changes

### Migration Structure

```sql
-- 003_add_inventory_table.sql

-- Create the inventory table
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id INTEGER NOT NULL,
    branch_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    last_updated TEXT NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (branch_id) REFERENCES branches(branch_id)
);

-- Create indexes for common queries
CREATE INDEX IF NOT EXISTS idx_inventory_product ON inventory(product_id);
CREATE INDEX IF NOT EXISTS idx_inventory_branch ON inventory(branch_id);
```

### Key Conventions

1. **Always use IF NOT EXISTS** — Makes migrations idempotent
2. **INTEGER PRIMARY KEY AUTOINCREMENT** — Standard for auto-incrementing IDs
3. **Foreign keys** — Always define with REFERENCES clause
4. **Indexes** — Create for foreign keys and frequently queried columns
5. **Defaults** — Use SQLite functions like `datetime('now')` for timestamps

## Column Naming and JPA Mapping

| Java Field | Database Column | `@Column` Annotation |
|------------|-----------------|---------------------|
| `supplierId` | `supplier_id` | `@Column(name = "supplier_id")` |
| `contactPerson` | `contact_person` | `@Column(name = "contact_person")` |
| `name` | `name` | `@Column` (no name needed — matches) |
| `active` | `active` | `@Column(nullable = false)` |
| `imgName` | `img_name` | `@Column(name = "img_name")` |

**Rule**: Use explicit `@Column(name = "...")` when the Java camelCase field does not exactly match the snake_case column name.

## Data Types

| Java Type | SQLite Type | Notes |
|-----------|-------------|-------|
| `Long` | `INTEGER` | Primary keys and foreign keys |
| `Integer` | `INTEGER` | Numeric values |
| `Double` | `REAL` | Floating point (prices, discounts) |
| `String` | `TEXT` | Strings and dates |
| `Boolean` | `INTEGER` | 0 = false, 1 = true |

## Boolean Handling

SQLite doesn't have a native boolean type. JPA/Hibernate handles the conversion automatically:
- Java `Boolean.TRUE` → SQLite `1`
- Java `Boolean.FALSE` → SQLite `0`
- SQLite `1` → Java `Boolean.TRUE`
- SQLite `0` → Java `Boolean.FALSE`

In entity classes, use `Boolean` (not `boolean`) to allow nullability:

```java
@Column(nullable = false)
private Boolean active = true;

@Column(nullable = false)
private Boolean verified = false;
```

In seed SQL, use `1` and `0`:

```sql
INSERT INTO suppliers (name, active, verified) VALUES ('Acme', 1, 0);
```

## Seed Files

### Naming Convention

```
{sequence}_{table_name}.sql
```

Order matters! Tables with foreign keys must be seeded after their referenced tables.

### Seed Structure

```sql
-- 004_products.sql

INSERT INTO products (product_id, name, supplier_id, price, description, sku, unit)
VALUES
  (1, 'Smart Cat Feeder', 1, 129.99, 'Automatic feeding system', 'SCF-001', 'piece'),
  (2, 'GPS Cat Collar', 1, 79.99, 'Real-time tracking', 'GCC-001', 'piece'),
  (3, 'Interactive Laser Toy', 2, 49.99, 'Automated play system', 'ILT-001', 'piece');
```

### Key Conventions

1. **Explicit IDs** — Specify IDs for predictable references
2. **Match model fields** — Column names must match database schema
3. **Order by dependency** — Seed referenced tables first
4. **Boolean values** — Use `1` for true, `0` for false

## Database Configuration

### application.properties

```properties
# Database Configuration
spring.datasource.url=jdbc:sqlite:./data/octocat-supply.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

**Key settings:**
- `ddl-auto=none` — Schema managed by manual migrations, not Hibernate auto-DDL
- `SQLiteDialect` — From `hibernate-community-dialects` package
- Database path: `./data/octocat-supply.db` (relative to project root)

## Database Initialization

The `DatabaseInitializer` runs on startup via `CommandLineRunner`:

```java
@Bean
public CommandLineRunner initDatabase(DatabaseInitializer initializer) {
    return args -> {
        // Default: run both migrations and seeding
        initializer.runMigrations();
        initializer.seedDatabase();
    };
}
```

**Features:**
- **Idempotent**: Checks if migrations/seed already applied before re-running
- **Migration check**: Queries `sqlite_master` for expected tables
- **Seed check**: Counts records in seeded tables
- **SQL execution**: Reads `.sql` files, splits by `;`, executes via raw JDBC

**Command-line args for selective initialization:**
- `--init-db` — Run migrations only
- `--seed-db` — Run seed only
- `--migrate-db` — Alias for init-db

## JPA Entity Mapping

### Primary Key

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "supplier_id")
private Long supplierId;
```

Maps to: `supplier_id INTEGER PRIMARY KEY AUTOINCREMENT`

### Foreign Key (as Long ID)

```java
@Column(name = "supplier_id", nullable = false)
private Long supplierId;
```

Maps to: `supplier_id INTEGER NOT NULL REFERENCES suppliers(supplier_id)`

**Note**: This project stores foreign keys as plain `Long` fields, not as `@ManyToOne` relationships.

### Nullable vs Required

```java
@Column(nullable = false)        // NOT NULL
private String name;

@Column                          // NULL allowed (default)
private String description;
```

## Adding a New Table

1. Create migration: `api-java/database/migrations/00X_add_table_name.sql`
2. Create seed (if needed): `api-java/database/seed/00X_table_name.sql`
3. Restart application to run migrations (handled by `DatabaseInitializer`)
4. Create entity, repository, and controller following skill patterns

## Common Queries via JPA

Most queries are handled by Spring Data JPA's built-in methods:

```java
// Find all (SELECT * FROM table ORDER BY id)
List<Entity> entities = repository.findAll();

// Find by ID (SELECT * FROM table WHERE id = ?)
Optional<Entity> entity = repository.findById(id);

// Save / Update (INSERT or UPDATE based on ID presence)
Entity saved = repository.save(entity);

// Delete
repository.delete(entity);
repository.deleteById(id);
```

For custom queries, use derived query methods:

```java
// Generates: SELECT * FROM products WHERE name = ?
Optional<Product> findByName(String name);
```

Or native SQL via `EntityManager` in a service class:

```java
@PersistenceContext
private EntityManager entityManager;

public List<Product> searchByName(String name) {
    String sql = "SELECT * FROM products WHERE name LIKE '%" + name + "%'";
    Query query = entityManager.createNativeQuery(sql, Product.class);
    return query.getResultList();
}
```

## Best Practices

1. **Always use parameterized queries** — Prevent SQL injection (JPA handles this for repository methods; use `setParameter` for native queries)
2. **Use `ddl-auto=none`** — Manage schema via manual migration files
3. **Keep migrations idempotent** — Use `IF NOT EXISTS` for CREATE statements
4. **Match seed order to foreign keys** — Seed parent tables before child tables
5. **Use `Boolean` not `boolean`** — Allows nullability and JPA handles SQLite integer conversion
