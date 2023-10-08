# не работает на windows брат
FROM openjdk:20

EXPOSE 8080

WORKDIR /app

COPY .mvn/ ./mvn

COPY mvnw pom.xml ./

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
