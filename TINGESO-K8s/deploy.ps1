param(
    [Parameter(Mandatory = $true)]
    [string]$DockerUsername
)

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path $PSScriptRoot -Parent
$backendRoot = Join-Path $projectRoot "TINGESO-Backend"
$frontendRoot = Join-Path $projectRoot "TINGESO-Frontend"   # cambia este nombre si tu carpeta frontend se llama distinto

$services = @(
    "config-service",
    "eureka-service",
    "gateway-service",
    "m1-inventario",
    "m2-prestamos",
    "m3-clientes",
    "m4-tarifas",
    "m5-kardex",
    "m6-reportes",
    "m7-usuarios"
)

Write-Host "Reemplazando <DOCKER_USERNAME> con $DockerUsername en manifiestos K8s..."
Get-ChildItem -Path $PSScriptRoot -Filter *.yaml | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    $content = $content -replace '<DOCKER_USERNAME>', $DockerUsername
    Set-Content -Path $_.FullName -Value $content -Encoding UTF8
}

Write-Host "Construyendo y subiendo imágenes del Backend..."
foreach ($service in $services) {
    $servicePath = Join-Path $backendRoot $service

    if (!(Test-Path $servicePath)) {
        throw "No existe la carpeta del servicio: $servicePath"
    }

    $tag = "${DockerUsername}/${service}:v1"

    Write-Host "Construyendo $service..."
    docker build -t $tag $servicePath
    if ($LASTEXITCODE -ne 0) {
        throw "Falló el build de $service"
    }

    Write-Host "Subiendo $service..."
    docker push $tag
    if ($LASTEXITCODE -ne 0) {
        throw "Falló el push de $service"
    }
}

Write-Host "Construyendo y subiendo imagen del Frontend..."
if (!(Test-Path $frontendRoot)) {
    throw "No existe la carpeta del frontend: $frontendRoot"
}

$frontendTag = "${DockerUsername}/tingeso-frontend:v1"

docker build -t $frontendTag $frontendRoot
if ($LASTEXITCODE -ne 0) {
    throw "Falló el build del frontend"
}

docker push $frontendTag
if ($LASTEXITCODE -ne 0) {
    throw "Falló el push del frontend"
}

Write-Host "Aplicando manifiestos a Minikube..."
kubectl apply -f (Join-Path $PSScriptRoot "01-postgres.yaml")
kubectl apply -f (Join-Path $PSScriptRoot "02-infra.yaml")
kubectl apply -f (Join-Path $PSScriptRoot "03-microservices.yaml")
kubectl apply -f (Join-Path $PSScriptRoot "04-frontend.yaml")

Write-Host "Despliegue completado! Usa 'minikube service frontend --url' para acceder."