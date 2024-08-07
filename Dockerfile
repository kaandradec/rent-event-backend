## Usa una imagen oficial de Maven para construir el JAR con JDK 17
#FROM maven:3.9.8-sapmachine-22 AS build
#
## Configura el directorio de trabajo en el contenedor
#WORKDIR /app
#
## Copia el archivo pom.xml y el directorio src al contenedor
#COPY pom.xml .
#COPY src ./src
#
## Construye el archivo JAR
#RUN mvn clean package -DskipTests

# Usa una imagen oficial de OpenJDK 17 como imagen base
FROM openjdk:17-oracle

ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build stage
COPY ${JAR_FILE} app.jar

EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
