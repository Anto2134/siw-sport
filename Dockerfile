# Immagine base con Java 17
FROM eclipse-temurin:17-jdk

# Set della directory di lavoro
WORKDIR /app

# Copia il jar dentro l'immagine
COPY target/siw-sport-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Comando per avviare l'app
ENTRYPOINT ["java", "-jar", "app.jar"]
