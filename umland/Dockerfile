# ==================================
# STAGE 1: Build da aplicação
# ==================================
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Define diretório de trabalho
WORKDIR /app

# Copia apenas pom.xml primeiro (cache de dependências)
COPY umland/pom.xml .

# Baixa dependências (essa camada fica em cache se pom.xml não mudar)
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY umland/src ./src

# Compila a aplicação (pula testes para build mais rápido)
RUN mvn clean package -DskipTests

# ==================================
# STAGE 2: Imagem final (runtime)
# ==================================
FROM eclipse-temurin:21-jre-alpine

# Instala curl para health checks
RUN apk add --no-cache curl

# Cria usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring

# Define diretório de trabalho
WORKDIR /app

# Cria diretórios para uploads
RUN mkdir -p /app/uploads/avatars /app/uploads/characters /app/uploads/items && \
    chown -R spring:spring /app

# Copia o JAR da stage de build
COPY --from=builder /app/target/*.war app.war

# Muda para usuário não-root
USER spring:spring

# Expõe a porta da aplicação
EXPOSE 9090

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:9090/actuator/health || exit 1

# Comando para iniciar a aplicação
# -Djava.security.egd=file:/dev/./urandom acelera inicialização
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:prod}", \
    "-jar", \
    "app.war"]
