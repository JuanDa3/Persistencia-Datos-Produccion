# Etapa 1: Construcción
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copiar el archivo POM y descargar las dependencias necesarias
COPY pom.xml .
# Copiar el código fuente del proyecto
COPY persistencia/src ./src
COPY persistencia/pom.xml ./persistencia/

# Copiar todo el código fuente del proyecto
COPY persistencia/src ./persistencia/src

# Compilar el proyecto y empaquetarlo en un archivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=build /app/persistencia/target/persistencia-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que la aplicación se ejecuta
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
