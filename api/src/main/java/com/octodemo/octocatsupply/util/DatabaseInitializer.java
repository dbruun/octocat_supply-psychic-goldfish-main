package com.octodemo.octocatsupply.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class DatabaseInitializer {

	private final DataSource dataSource;

	@Value("${spring.datasource.url}")
	private String databaseUrl;

	public DatabaseInitializer(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void runMigrations() {
		// Check if migrations have already been applied by checking if the products table exists
		if (areMigrationsApplied()) {
			log.info("Database migrations already applied, skipping migration operation");
			return;
		}
		
		log.info("Running database migrations...");
		Path migrationsDir = Paths.get("database/migrations");
		
		if (!Files.exists(migrationsDir)) {
			log.warn("Migrations directory not found: {}", migrationsDir);
			return;
		}

		List<String> appliedMigrations = new ArrayList<>();

		try (Stream<Path> paths = Files.list(migrationsDir).sorted()) {
			paths.filter(Files::isRegularFile)
				 .filter(p -> p.toString().endsWith(".sql"))
				 .forEach(path -> {
					 String fileName = path.getFileName().toString();
					 log.info("Applying migration: {}", fileName);
					 executeSqlFile(path);
					 appliedMigrations.add(fileName);
				 });
		} catch (IOException e) {
			log.error("Error reading migrations directory", e);
		}

		if (!appliedMigrations.isEmpty()) {
			log.info("Applied migrations: {}", String.join(", ", appliedMigrations));
		}
	}

	private boolean areMigrationsApplied() {
		try (Connection conn = dataSource.getConnection();
			 Statement stmt = conn.createStatement()) {
			// Check if the products table exists
			var rs = stmt.executeQuery(
				"SELECT name FROM sqlite_master WHERE type='table' AND name='products'"
			);
			return rs.next();
		} catch (Exception e) {
			// If we can't check, assume not migrated
			log.debug("Could not check if migrations are applied: {}", e.getMessage());
		}
		return false;
	}

	public void seedDatabase() {
		// Check if database is already seeded by checking if products table has data
		if (isDatabaseSeeded()) {
			log.info("Database already seeded, skipping seed operation");
			return;
		}
		
		log.info("Seeding database...");
		Path seedDir = Paths.get("database/seed");
		
		if (!Files.exists(seedDir)) {
			log.warn("Seed directory not found: {}", seedDir);
			return;
		}

		List<String> seededTables = new ArrayList<>();

		try (Stream<Path> paths = Files.list(seedDir).sorted()) {
			paths.filter(Files::isRegularFile)
				 .filter(p -> p.toString().endsWith(".sql"))
				 .forEach(path -> {
					 String fileName = path.getFileName().toString();
					 log.info("Seeding data from: {}", fileName);
					 executeSqlFile(path);
					 seededTables.add(fileName);
				 });
		} catch (IOException e) {
			log.error("Error reading seed directory", e);
		}

		if (!seededTables.isEmpty()) {
			log.info("Seeded tables: {}", String.join(", ", seededTables));
		}
	}

	private boolean isDatabaseSeeded() {
		try (Connection conn = dataSource.getConnection();
			 Statement stmt = conn.createStatement()) {
			var rs = stmt.executeQuery("SELECT COUNT(*) as count FROM products");
			if (rs.next()) {
				return rs.getInt("count") > 0;
			}
		} catch (Exception e) {
			// If we can't check, assume not seeded
			log.debug("Could not check if database is seeded: {}", e.getMessage());
		}
		return false;
	}

	private void executeSqlFile(Path sqlFile) {
		try {
			String sql = Files.readString(sqlFile);
			
			// Remove comments and split by semicolon
			String[] statements = sql.split(";");
			
			try (Connection conn = dataSource.getConnection();
				 Statement stmt = conn.createStatement()) {
				
				for (String statement : statements) {
					// Remove SQL comments (lines starting with --)
					String cleaned = statement.lines()
							.filter(line -> !line.trim().startsWith("--"))
							.collect(java.util.stream.Collectors.joining("\n"))
							.trim();
					
					if (!cleaned.isEmpty()) {
						try {
							stmt.execute(cleaned);
						} catch (Exception e) {
							log.error("Error executing statement in {}: {}", sqlFile.getFileName(), cleaned.substring(0, Math.min(100, cleaned.length())), e);
							throw e;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error executing SQL file: {}", sqlFile, e);
		}
	}
}
