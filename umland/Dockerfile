FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

COPY --from=build /workspace/app/target/*.war /app/app.war

RUN addgroup -S spring && adduser -S spring -G spring
RUN chown spring:spring /app/app.war
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.war"]
