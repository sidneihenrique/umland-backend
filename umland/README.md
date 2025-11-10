# � UMLAND Backend - Docker

Backend Spring Boot com Docker para desenvolvimento e produção.

## � Desenvolvimento Local

```bash
# Iniciar backend + PostgreSQL
docker compose up

# Rebuild após mudanças
docker compose up --build

# Parar
docker compose down
```

**Acesse:** http://localhost:9090/swagger-ui.html

## � Arquivos Docker

- `Dockerfile` - Multi-stage build otimizado
- `docker-compose.yml` - Orquestração local (backend + PostgreSQL)
- `.dockerignore` - Otimização de build

## ⚙️ Perfis de Ambiente

- `application.properties` - Desenvolvimento local
- `application-docker.properties` - Docker local  
- `application-prod.properties` - Produção (Railway, Fly.io, etc)

## � Deploy

Railway detecta automaticamente o Dockerfile e faz deploy.

**Variáveis necessárias:**
- `SPRING_PROFILES_ACTIVE=prod`
- `DATABASE_URL` (auto-injetado pelo Railway)

## � Stack

- Java 21
- Spring Boot 3.5.5
- PostgreSQL 16
- Docker
