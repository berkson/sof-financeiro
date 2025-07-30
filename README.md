# SOP-Financeiro

API desenvolvida em Spring Boot para gerenciamento de **Despesas**, **Empenhos** e **Pagamentos**.  
Permite operações de **cadastro**, **edição**, **deleção** e **listagem** dessas entidades.

## ✨ Objetivo

Este projeto foi desenvolvido como parte de um processo seletivo e demonstra habilidades práticas com:
- Spring Boot
- Flyway
- Docker
- PostgreSQL

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Flyway** (migração e versionamento de banco de dados)
- **Docker e Docker Compose** (para criação automatizada do ambiente)
- **PostgreSQL**

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://adoptium.net/en-GB/temurin/releases/?version=17)

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/sop-financeiro.git
   cd sop-financeiro
2. Execute o projeto
   ```bash
   ./mvnw spring-boot:run

Nota: **O Flyway se encarregará de criar o banco de dados e aplicar as migrações iniciais automaticamente.**

📂 Estrutura do Projeto
 ```bash
sop-financeiro/
├── src/
│   └── main/
│       ├── java/                  # Código-fonte da aplicação
│       └── resources/
│           └── db/migration/     # Scripts SQL do Flyway
├── docker-compose.yaml           # Define o container PostgreSQL
├── pom.xml                       # Dependências Maven
```

📄 Licença
 - Este projeto é de uso livre.

👤 Autor
 - Berkson
 - GitHub: [Sop-Financeiro](https://github.com/berkson/sop-financeiro)
