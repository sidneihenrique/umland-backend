# Script para fazer deploy no Google Cloud Run
# Execute este script após instalar o Google Cloud CLI

# Configurações
$PROJECT_ID = "umland-backend"   # ID do projeto GCloud
$REGION = "us-central1"          # Região do Cloud Run
$SERVICE_NAME = "umland-backend"
$IMAGE_NAME = "gcr.io/$PROJECT_ID/$SERVICE_NAME"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Deploy do Umland Backend para GCloud Run" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# 1. Autenticar (se necessário)
Write-Host "`n[1/5] Verificando autenticação..." -ForegroundColor Yellow
gcloud auth list

# 2. Configurar projeto
Write-Host "`n[2/5] Configurando projeto..." -ForegroundColor Yellow
gcloud config set project $PROJECT_ID

# 3. Build da imagem usando Cloud Build
Write-Host "`n[3/5] Construindo imagem Docker no Cloud Build..." -ForegroundColor Yellow
gcloud builds submit --tag $IMAGE_NAME

# 4. Deploy no Cloud Run
Write-Host "`n[4/5] Fazendo deploy no Cloud Run..." -ForegroundColor Yellow
gcloud run deploy $SERVICE_NAME `
  --image $IMAGE_NAME `
  --platform managed `
  --region $REGION `
  --allow-unauthenticated `
  --port 8080 `
  --env-vars-file env.yaml `
  --memory 512Mi `
  --cpu 1 `
  --min-instances 0 `
  --max-instances 10

# 5. Verificar status
Write-Host "`n[5/5] Verificando status do serviço..." -ForegroundColor Yellow
gcloud run services describe $SERVICE_NAME --region $REGION

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "Deploy concluído com sucesso!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
