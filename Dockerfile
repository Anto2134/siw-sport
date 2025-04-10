# Usa un'immagine con Maven e Java 17
FROM maven:3.8.4-openjdk-17-slim AS build

# Set della directory di lavoro
WORKDIR /app

# Copia il codice sorgente nel container
COPY . .

# Compila il progetto con Maven
RUN mvn clean package -DskipTests

# Usa un'immagine con solo Java 17 per eseguire l'applicazione
FROM eclipse-temurin:17-jdk

# Set della directory di lavoro
WORKDIR /app

# Copia il .jar dal container precedente
COPY --from=build /app/target/siw-sport-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Comando per avviare l'app
ENTRYPOINT ["java", "-jar", "app.jar"]
