# EFM API

API REST desenvolvida com Spring Boot para estudo de modelagem de domínio, organização em camadas, autenticação JWT e persistência com JPA.

O projeto simula rotinas de gestão rural, com foco em produtos, estoque, funcionários, áreas da fazenda, ordens de serviço, produção e identidade de usuários.

## Visão Geral

- Java 21
- Spring Boot 3.5.9
- Spring Web
- Spring Data JPA
- Spring Security
- OAuth2 Resource Server + JWT com chaves RSA
- Bean Validation
- Maven
- H2 para execução local padrão
- MySQL 8 para execução com Docker Compose

## Contextos do Domínio

O código está organizado por contexto funcional, com separação entre camadas de `application`, `domain`, `infrastructure` e `shared`.

Principais módulos:

- `auth`
- `category`
- `employee`
- `identity`
- `product`
- `production`
- `service_order`
- `shared`

## Funcionalidades

- autenticação com login e revogação de token no logout
- gestão de categorias
- cadastro de produtos e controle de estoque
- cadastro e desligamento de funcionários
- gestão de usuários e papéis
- cadastro de áreas da fazenda
- ordens de serviço com itens, incremento/decremento de quantidade, cancelamento e conclusão
- registro de produção
- endpoints auxiliares para enums do domínio

## Segurança

A API usa JWT assinado com par de chaves RSA armazenado em:

- `src/main/resources/api-dev.pri`
- `src/main/resources/api-dev.pub`

Regras atuais de acesso:

- `POST /auth/login` é público
- todos os endpoints `GET` são públicos
- demais métodos exigem token Bearer
- `POST /auth/logout` invalida o token atual

## Principais Endpoints

Autenticação:

- `POST /auth/login`
- `POST /auth/logout`

Recursos principais:

- `GET|POST|DELETE /categories`
- `GET|POST|PATCH /employees`
- `GET|POST|DELETE /roles`
- `GET|POST|DELETE /users`
- `GET|POST|PATCH|DELETE /products`
- `GET|POST /productions`
- `GET|POST|DELETE /farmarea`
- `GET|POST|PATCH|DELETE /serviceorder`
- `GET /enums/*`

Alguns endpoints específicos úteis:

- `PATCH /employees/{id}/phonenumber`
- `POST /employees/{id}/terminate`
- `PATCH /products/{id}/increase`
- `PATCH /products/{id}/decrease`
- `POST /serviceorder/add-item`
- `DELETE /serviceorder/{orderId}/items/{itemId}`
- `PATCH /serviceorder/{orderId}/order-item/{itemId}/increase`
- `PATCH /serviceorder/{orderId}/order-item/{itemId}/decrease`
- `PATCH /serviceorder/complete/{id}`
- `PATCH /serviceorder/cancel/{id}`
- `GET /serviceorder/list-details`

## Perfis e Banco de Dados

### Execução local padrão

Por padrão a aplicação sobe com H2 em memória:

- URL da API: `http://localhost:8080`
- console H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- usuário: `sa`
- senha: vazia

Configuração atual do perfil padrão:

- `spring.jpa.hibernate.ddl-auto=update`
- `spring.sql.init.mode=always`
- carga inicial via `src/main/resources/data.sql`

Esse modo é o mais simples para desenvolvimento local e testes manuais.

### Execução com Docker

O repositório já vem containerizado com:

- `Dockerfile` para build e empacotamento da aplicação
- `docker-compose.yml` para subir API + MySQL 8

Serviços definidos no Compose:

- `api`: aplicação Spring Boot exposta em `localhost:8080`
- `mysql`: banco MySQL exposto em `localhost:3307`

Configuração padrão do `.env.example`:

- database: `efm`
- usuário: `root`
- senha: `root`
- usuário inicial da API no Docker: `admin`
- senha inicial da API no Docker: `admin`

Comando para subir o ambiente:

```bash
docker compose up --build
```

Observação importante sobre o perfil Docker:

- o compose ativa o perfil `docker`
- a aplicação usa MySQL em `jdbc:mysql://mysql:3306/efm`
- nesse perfil o `spring.sql.init.mode=never`
- ao subir com Docker, a aplicação garante um usuário administrador padrão caso ele ainda não exista no banco

Na prática, isso significa que o `data.sql` não é carregado automaticamente quando a aplicação sobe via Docker. Em um banco MySQL vazio, a estrutura é criada pelo Hibernate e o acesso inicial `admin/admin` é provisionado automaticamente.

## Seed de Dados

No perfil local com H2, o arquivo `src/main/resources/data.sql` popula:

- categorias
- produtos
- funcionários
- áreas da fazenda
- ordens de serviço e itens
- registros de produção
- papéis (`ADMIN` e `USER`)
- usuários iniciais (`admin` e `user`)

Como o perfil Docker desabilita a inicialização SQL, esse seed fica disponível automaticamente apenas no modo padrão com H2.

## Acesso de Login para Testes

Ao subir a aplicação no perfil padrão local, você pode autenticar com o usuário seed abaixo:

- username: `admin`
- password: `admin`

Exemplo de requisição:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'
```

Observação:

- esse acesso depende do seed carregado pelo `data.sql`
- no fluxo com Docker + MySQL, a aplicação também garante esse acesso inicial por bootstrap no profile `docker`

## Como Executar

### Pré-requisitos

- Java 21
- Maven 3.9+ ou uso do `./mvnw`
- Docker e Docker Compose, se quiser rodar a stack containerizada

### Com Maven

```bash
./mvnw spring-boot:run
```

Ou:

```bash
./mvnw clean package
java -jar target/efm-0.0.1-SNAPSHOT.jar
```

### Com Docker Compose

Primeiro, gere seu arquivo local de ambiente:

```bash
cp .env.example .env
```

Depois:

```bash
docker compose up --build
```

## Testes

O projeto possui 31 classes de teste em `src/test/java`, cobrindo:

- regras de domínio
- repositórios JPA
- controllers
- services e use cases
- subida da aplicação

Para executar:

```bash
./mvnw test
```

Os testes usam configuração própria em `src/test/resources/application-test.properties`.

## Estrutura de Build

O `Dockerfile` usa build em duas etapas:

1. imagem `maven:3.9.9-eclipse-temurin-21` para gerar o `.jar`
2. imagem `eclipse-temurin:21-jdk` para executar a aplicação

## Observações

- o projeto segue uma abordagem pragmática inspirada em Clean Architecture e DDD
- o repositório contém material auxiliar em `docs/`
- não há documentação OpenAPI/Swagger configurada no estado atual do projeto
- como todos os `GET` são públicos, a API pode ser explorada parcialmente sem autenticação
- operações de escrita dependem de token JWT válido
