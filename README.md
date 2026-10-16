# java-persistence

Hands-on lab for exploring advanced **JPA / Hibernate** persistence concepts with Spring Boot and PostgreSQL. Each concept is implemented in a small, focused set of entities/services and verified through integration tests.

## Modules

| Module | Description |
|--------|-------------|
| [`jpa-advanced-persistence-lab`](jpa-advanced-persistence-lab) | Spring Boot app demonstrating locking, transactions, inheritance, composite keys and entity graphs |

## Tech Stack

- Java 21
- Spring Boot 4.1.1 (Spring Data JPA, Hibernate)
- PostgreSQL 17 (via Docker Compose)
- Maven (wrapper included)
- JUnit 5 + AssertJ

## Concepts Covered

| Concept | Implementation | Test |
|---------|----------------|------|
| **Optimistic locking** | `Account` with `@Version` field | `account/AccountOptimisticLockingTest` – a stale update throws `ObjectOptimisticLockingFailureException` |
| **Pessimistic locking** | `AccountRepository.findForUpdate()` with `@Lock(PESSIMISTIC_WRITE)` (`SELECT ... FOR UPDATE`) | `account/AccountPessimisticLockingTest` |
| **Versioned entity basics** | `Account` starts at version `0` | `account/AccountRepositoryTest` (`@DataJpaTest`) |
| **Transaction propagation** | `TransactionDemoService` (`REQUIRED`) calls `IndependentTransactionService` (`REQUIRES_NEW`) which fails | `transaction/TransactionPropagationTest` – outer records commit, inner record rolls back |
| **Inheritance (single table)** | Abstract `Payment` with `CardPayment` / `UpiPayment`, discriminator column `payment_type` | `inheritence/JpaInheritanceTest` |
| **Composite primary key** | `OrderItem` with `@EmbeddedId OrderItemId(orderId, productId)` | `inheritence/CompositeKeyTest` |
| **Entity graphs (N+1 avoidance)** | `Customer` → lazy `orders`; `CustomerRepository.findWithOrdersById()` uses `@EntityGraph` | `entitygraph/EntityGraphTest` |

## Project Structure

```
jpa-advanced-persistence-lab/
├── docker-compose.yml               # PostgreSQL 17 container
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/levelup/jpaadvancedpersistencelab/
    │   │   ├── entity/              # Account, Customer, Order, OrderItem(Id), Payment hierarchy, TransactionRecord
    │   │   ├── repository/          # Spring Data JPA repositories
    │   │   └── service/             # AccountService, TransactionDemoService, IndependentTransactionService
    │   └── resources/application.properties
    └── test/java/com/levelup/jpaadvancedpersistencelab/
        ├── account/                 # Optimistic & pessimistic locking tests
        ├── entitygraph/             # Entity graph test
        ├── inheritence/             # Inheritance & composite key tests
        └── transaction/             # Propagation test
```

## Getting Started

### Prerequisites

- JDK 21
- Docker (for PostgreSQL)

### 1. Start PostgreSQL

```bash
cd jpa-advanced-persistence-lab
docker compose up -d
```

This starts PostgreSQL on `localhost:5432` with database `jpa_persistence_lab` (user/password: `postgres`/`postgres`).

### 2. Run the tests

```bash
./mvnw test          # Linux/macOS
mvnw.cmd test        # Windows
```

Run a single test class:

```bash
./mvnw test -Dtest=AccountOptimisticLockingTest
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

## Configuration

Key settings in `src/main/resources/application.properties`:

| Property | Value | Purpose |
|----------|-------|---------|
| `spring.jpa.hibernate.ddl-auto` | `create-drop` | Schema is recreated on every run |
| `spring.jpa.show-sql` / `hibernate.format_sql` | `true` | Log formatted SQL statements |
| `hibernate.generate_statistics` | `true` | Log Hibernate statistics (useful for spotting N+1 queries) |

Tests run with `-Duser.timezone=Asia/Kolkata` (configured in the Surefire plugin).
