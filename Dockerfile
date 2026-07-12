FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and project metadata
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

# Download dependencies
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]