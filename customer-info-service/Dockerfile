FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# Copy maven files first (for better layer caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Fix file permissions and build the jar
RUN chmod +x ./mvnw && \
    ./mvnw install -DskipTests

# Create a smaller runtime image
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

# Add non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy build artifacts
COPY --from=build /workspace/app/target/*.jar app.jar

# Create directories for uploads
RUN mkdir -p /home/spring/uploads/profile-photos && \
    chown -R spring:spring /home/spring/uploads

# Expose the application port
EXPOSE 9000

# Set environment variable for uploads
ENV APP_FILE_UPLOAD_DIR=/home/spring/uploads

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]