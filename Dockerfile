# syntax=docker/dockerfile:1

# --- Build stage: compile, package, and split into layers ---
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Resolve dependencies first so this layer is cached unless pom.xml changes
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

# Build the executable jar, then extract Spring Boot layers for better caching
COPY src/ src/
RUN ./mvnw -B -q -DskipTests clean package \
    && cp target/*.jar application.jar \
    && java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# --- Runtime stage: minimal JRE, unprivileged user ---
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

# Run as a non-root system user
RUN groupadd --system spring && useradd --system --gid spring spring

# Copy layers from most-stable (third-party deps) to least-stable (app code).
# Each layer is a separate Docker layer, so rebuilds after a code change only
# re-copy the small application layer instead of all dependencies.
COPY --from=build /app/extracted/dependencies/ ./
COPY --from=build /app/extracted/spring-boot-loader/ ./
COPY --from=build /app/extracted/snapshot-dependencies/ ./
COPY --from=build /app/extracted/application/ ./

USER spring
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
