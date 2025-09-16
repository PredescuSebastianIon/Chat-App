# ---- build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# cache m2 ca să nu reia dep-urile
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# (opțional) rulează ca user non-root
RUN addgroup -S spring && adduser -S spring -G spring

# ia jar-ul rezultat (oricum s-ar numi) și numește-l app.jar
COPY --from=build /app/target/*.jar /app/app.jar

# Render setează $PORT la runtime. Pornește Spring pe acel port.
# Atenție: folosim shell form ca să se expandeze $PORT.
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0"
EXPOSE 8080
USER spring
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
