# OctoCAT Supply API - Java Backend Makefile
# Cross-platform compatible (Linux, macOS, Windows with Git Bash or WSL)
# Mirrors the npm scripts from api-nodejs/package.json

# Platform-specific Maven wrapper command
ifeq ($(OS),Windows_NT)
    MVN := ./mvnw.cmd
else
    MVN := ./mvnw
endif

.DEFAULT_GOAL := help

.PHONY: help
help: ## Display available commands
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)

.PHONY: build
build: ## Build the package (equivalent to npm run build)
	$(MVN) clean package -DskipTests

.PHONY: install
install: ## Install all dependencies (including dev)
	$(MVN) clean install -DskipTests

.PHONY: start
start: start-install ## Start the production server

.PHONY: start-install
start-install: ## Initialize DB with seed data and start server
	$(MVN) spring-boot:run -Dspring-boot.run.arguments="--init-db --seed-db" -Dmaven.test.skip=true

.PHONY: dev
dev: ## Start the development server
	$(MVN) spring-boot:run -Dmaven.test.skip=true

.PHONY: test
test: ## Run tests
	$(MVN) test

.PHONY: db-init
db-init: ## Initialize database schema
	$(MVN) spring-boot:run -Dspring-boot.run.arguments="--init-db" -Dmaven.test.skip=true

.PHONY: db-seed
db-seed: ## Initialize and seed database with sample data
	$(MVN) spring-boot:run -Dspring-boot.run.arguments="--init-db --seed-db" -Dmaven.test.skip=true

.PHONY: db-migrate
db-migrate: ## Run database migrations (alias for db-init)
	$(MVN) spring-boot:run -Dspring-boot.run.arguments="--init-db" -Dmaven.test.skip=true
