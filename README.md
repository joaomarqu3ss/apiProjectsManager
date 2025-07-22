# 📂 API de Gerenciamento de Projetos

Esta é uma API REST desenvolvida com Spring Boot para gerenciamento de projetos por usuário.
O acesso aos recursos é protegido por um mecanismo de autenticação via filters, garantindo que apenas usuários autenticados possam interagir com os endpoints.
O projeto utiliza Java 21, possui tratamento de exceções personalizadas, geração de logs estruturados e está preparado para fácil integração com front‑ends ou outros serviços.

---

# ✨ Funcionalidades
-✅ **Autenticação de usuários**
-✅ **Criação, listagem, atualização e exclusão de projetos**
-✅ **Autorização por usuário (cada um gerencia apenas seus projetos)**
-✅ **Filtros personalizados para autenticação**
-✅ **Tratamento de exceções customizado (com mensagens amigáveis no padrão JSON)**
-✅ **Logs estruturados para monitoramento e auditoria**
-✅ **Código pronto para extensões futuras (ex.: auditoria, multi‑tenant, etc.)**

---
## 🚀 Tecnologias utilizadas

-☕ **Java 21**
-🌱 **Spring Boot (Spring Web, Spring Boot DevTools, Spring Data MongoDB)**
-🗄️ **Banco de dados não relacional (MongoDB)**
-🔑 **JWT para autenticação**
-🧪 **JUnit 5 e Mockito para testes unitários**
-📦 **Maven para gerenciamento de dependências**
