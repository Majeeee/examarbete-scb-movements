FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Kopiera gradle-wrapper först (cache-optimering)
COPY gradlew .
COPY gradle/ gradle/

# Kopiera build-filer
COPY build.gradle.kts settings.gradle.kts ./

# Ge gradlew körbehörighet
RUN chmod +x gradlew

# Kopiera källkod
COPY src/ src/

# Bygg JAR
RUN ./gradlew clean bootJar --no-daemon

# Exponera porten
EXPOSE 8080

# Starta JARen
CMD ["java", "-jar", "/app/build/libs/backend-0.0.1-SNAPSHOT.jar"]
