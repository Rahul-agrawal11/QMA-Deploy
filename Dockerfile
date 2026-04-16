# =========================================================
# Stage 1: Build
# =========================================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Install required tools for mvnw (needs bash on alpine)
RUN apk add --no-cache bash

# Copy Maven wrapper and pom.xml first (cache dependencies)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Fix line endings and grant execute permissions
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build, skip tests (tests run in CI)
RUN ./mvnw clean package -DskipTests -B

# =========================================================
# Stage 2: Runtime
# =========================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Security: run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Change ownership
RUN chown appuser:appgroup app.jar

USER appuser

# Railway injects $PORT dynamically — expose the default
EXPOSE 8080

# Health check using sh (wget may not be in jre-alpine)
HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
  CMD wget -qO- http://localhost:${PORT:-8080}/actuator/health || exit 1

# Run the application — let Railway's $PORT override via Spring's server.port=${PORT:8080}
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
