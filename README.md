# EFM API

REST API built with Spring Boot as a study project focused on hands-on practice and skill development in Java backend engineering.

This repository is intended to reinforce knowledge in domain modeling, layered architecture, JPA persistence, JWT authentication, validation, and automated testing.

## Highlights

- modular architecture organized by domain context
- pragmatic use of Clean Architecture and DDD concepts
- JWT-based authentication
- product and inventory management
- service orders with items and status flow
- local seed data for development
- test suite covering different layers of the application

## Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2
- MySQL Driver
- Maven

## Features

- authentication with login and logout
- categories
- products and inventory control
- employees
- users and roles
- farm areas
- service orders and items
- production records
- enum helper endpoints

## Architecture

The project is organized with inspiration from Clean Architecture and DDD, without following either model rigidly.

The goal was to apply the concepts that made sense for the current scope of the project in a pragmatic way. The structure aims to separate responsibilities and protect business rules without forcing strict academic adherence to those patterns.

The codebase is organized by domain context, with separation between `application`, `domain`, `infrastructure`, and `shared`.

Main modules: `auth`, `category`, `employee`, `identity`, `product`, `production`, `service_order`, and `shared`.

## Local Environment

API base URL:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

Current configuration:

- in-memory H2 database
- initial data loaded from `data.sql`
- schema generated from the entities using `ddl-auto=update`

## Running the Project

```bash
mvn spring-boot:run
```

Or:

```bash
mvn clean package
java -jar target/efm-0.0.1-SNAPSHOT.jar
```

## Tests

One of the goals of this project is to practice implementation together with automated validation.

The API currently has 31 test classes in `src/test/java`, covering:

- domain rules
- JPA repositories
- controllers
- application services
- application startup

To run the tests:

```bash
mvn test
```

## Notes

- this repository was created for study, practice, and knowledge consolidation
- the initial dataset in `src/main/resources/data.sql` makes local exploration of the API easier
- this is not a production-oriented project at this stage
- the purpose is to support technical growth and code organization in a realistic API context
- concepts such as Clean Architecture and DDD were used as references, with practical adaptations to fit the scope of the project
