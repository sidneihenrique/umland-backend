FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src

# Copia o zip para dentro do container
COPY uploads.zip /app/uploads.zip

# Entra na pasta e descompacta
RUN apk add --no-cache unzip && \
    cd /app/ && unzip uploads.zip

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

COPY --from=build /workspace/app/target/*.war /app/app.war

# Copia os arquivos de upload ANTES de criar o usuário
COPY --from=build /app/uploads/ /app/uploads/

RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring:spring /app
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.war"]
