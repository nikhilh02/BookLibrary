FROM maven:3.9.6-amazoncorretto-21

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
