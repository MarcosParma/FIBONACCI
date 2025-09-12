# --- Etapa 1: Build ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el pom.xml y descargar dependencias (cacheo)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y empaquetar la app
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Runtime ---
FROM eclipse-temurin:17-jdk AS runtime
WORKDIR /app

# Copiar el JAR construido
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Spring Boot default)
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
