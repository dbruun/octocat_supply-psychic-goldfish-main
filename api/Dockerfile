FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src
COPY database ./database

# Build application
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S appuser && adduser -S appuser -G appuser

# Copy JAR from builder
COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /app/database ./database

# Create data directory and set permissions
RUN mkdir -p /app/data && chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Application environment variables
ENV PORT=3000 \
    DATABASE_PATH=/app/data/supply_chain.db \
    SPRING_PROFILES_ACTIVE=production

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]
