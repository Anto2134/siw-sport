# # Immagine base con Java 17
# FROM eclipse-temurin:17-jdk

# # Set della directory di lavoro
# WORKDIR /app

# # Copia il jar dentro l'immagine
# COPY target/siw-sport-0.0.1-SNAPSHOT.jar app.jar

# # Espone la porta 8080
# EXPOSE 8080

# # Comando per avviare l'app
# ENTRYPOINT ["java", "-jar", "app.jar"]
# Immagine base con Java 17 e Maven
# FROM maven:3.8.6-openjdk-17-slim AS build
FROM openjdk:17-jdk-slim AS build


# Set della directory di lavoro
WORKDIR /app

# Copia i file di progetto nel container
COPY . .

# Compilazione del progetto con Maven (crea il .jar)
RUN mvn clean package -DskipTests

# Immagine per eseguire il .jar
FROM eclipse-temurin:17-jdk

# Set della directory di lavoro
WORKDIR /app

# Copia il .jar generato nella fase precedente
COPY --from=build /app/target/siw-sport-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Comando per avviare l'app
ENTRYPOINT ["java", "-jar", "app.jar"]

