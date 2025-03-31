FROM openjdk:21-oracle

WORKDIR /app

RUN microdnf install -y findutils

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean bootJar

RUN cp $(find build/libs -name "*.jar" | head -n 1) app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]