# Usa una base image di JDK per costruire il progetto
FROM maven:3.8.5-openjdk-17 AS build

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file pom.xml e scarica le dipendenze del progetto
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia il resto del codice sorgente
COPY src ./src

# Compila il progetto
RUN mvn package -DskipTests

# Usa un’immagine più leggera per il runtime
FROM openjdk:17-jdk-slim

# Copia l'applicazione compilata dalla fase build
COPY --from=build /app/target/myapp.jar /app.jar

# Specifica il comando di avvio
ENTRYPOINT ["java", "-jar", "/app.jar"]
