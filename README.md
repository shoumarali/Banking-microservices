> 🚧 Note: This project is in early-stage development and actively being built by a solo developer.

# Banking Microservices 🏦⚡️

A Spring Cloud-based microservices architecture solving traditional monolithic system limitations through modern distributed design.

Configuration Server Setup

The Config Server fetches configuration files from the following GitHub repository:
🔗 [shoumarali/configs](https://github.com/shoumarali/configs)

## 📑 Table of Contents
- [Summary](#-summary)
- [Overview](#-overview)
- [Key Features](#-key-features)
- [Technical Stack](#-technical-stack)
- [Getting Started](#-getting-started)

## 📌 Summary
Banking Microservices is a project that:
- 🧩 Decouples monolithic banking systems into scalable services
- ☁️ Implements cloud native patterns with Spring Cloud
- 🛡️ Enforces security via JWT and Keycloak
- ⚡ Achieves high availability through fault tolerance
- 📊 Provides full observability with metrics/logging/tracing

## 🌐 Overview
This project modernizes banking systems by addressing:
- 📉 Scaling bottlenecks of monolithic architectures
- 🔄 service to service communication
- 🚦 Traffic management in production environments
- 🔍 Debugging difficulties in distributed systems

## ✨ Key Features
| Feature | Implementation |
|---------|---------------|
| **Centralized Config** | Spring Cloud Config (GitHub-backed) |
| **Service Discovery** | Eureka Server |
| **API Gateway** | Spring Cloud Gateway (JWT Auth) |
| **Fault Tolerance** | Resilience4j + Redis |
| **Event-Driven** | RabbitMQ (Spring AMQP) |
| **Observability** | Prometheus + Grafana, Loki, OpenTelemetry |
| **SMS Notifications** | Twilio Integration |
| **Containerization** | Docker + Jib |

## 💻 Technical Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.3 + Spring Cloud
- **Database**: MySQL
- **Auth**: Keycloak (OAuth2/JWT)
- **Containerization**: Docker, Jib
- **Orchestration**: Docker Compose
- **Messaging**: RabbitMQ
- **Monitoring**: Prometheus, Grafana, Loki, Tempo


## 🚀 Getting Started

### Prerequisites
- Docker
- Bash shell

### Quick Start
 **Build containers**:
  Navigate to the project's root directory and run "./RecreateImages.sh" to build the Docker images.

  Then change to the default Docker Compose directory and start the containers in detached mode my running "docker compose up -d"
  
ℹ️I’m currently setting up Kubernetes deployment using Minikube. The images aren’t yet available on Docker Hub but will be published soon. For now, please build the containers locally using the provided script.
