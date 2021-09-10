<div align="center">

![](https://img.shields.io/badge/Status-Done-brightgreen)

</div>

<div align="center">

# Parking Lot API

This project has been built to practice some technologies, which I need to use in my job. For that, I've implemented a general idea of a parking lot API.

![](https://img.shields.io/badge/Autor-Welington%20Larsen-brightgreen)
</div>

## Technologies
- Kotlin
- Micronaut
- gRPC
- Docker
- Kubernetes
- PostgreSQL
- Kotest

## Required local infrastructure
- Java 11
- Docker

## Optional local infrastructure
- Minikube
- Kubectl

## Local Execution (terminal)
- Open the project root folder
- Execute Gradle embedded commands, like `./gradlew clean build`
- Raise the needed containers `docker-compose up`
- Start the application

## Manual deploy into a Minikube cluster
- Open the infra folder 
- Execute the follows commands:
```
kubectl apply -f postgresql-configmap.yaml
kubectl apply -f postgresql-storage.yaml
kubectl apply -f postgresql-deployment.yaml
kubectl apply -f postgresql-svc.yaml
kubectl apply -f app-deployment.yaml
minikube service app-service
```
- REST endpoints will be available