# Utilisez une image de base Java 11
FROM eclipse-temurin:21
WORKDIR /app
COPY target/customer-service.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
