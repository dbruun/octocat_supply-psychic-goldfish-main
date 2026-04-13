package com.octodemo.octocatsupply;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.octodemo.octocatsupply.util.DatabaseInitializer;

@SpringBootApplication
public class OctocatSupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OctocatSupplyApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(DatabaseInitializer initializer) {
		return args -> {
			boolean initDb = false;
			boolean seedDb = false;
			boolean migrateDb = false;

			for (String arg : args) {
				if ("--init-db".equals(arg)) {
					initDb = true;
				}
				if ("--seed-db".equals(arg)) {
					seedDb = true;
				}
				if ("--migrate-db".equals(arg)) {
					migrateDb = true;
				}
			}

			if (initDb || migrateDb) {
				initializer.runMigrations();
			}

			if (seedDb) {
				initializer.seedDatabase();
			}

			if (!initDb && !migrateDb && !seedDb) {
				initializer.runMigrations();
				initializer.seedDatabase();
			}
		};
	}
}
