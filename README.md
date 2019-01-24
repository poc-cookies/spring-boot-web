# Spring Boot Example Application

Spring Boot Example Application

## Prerequisites

1. Java 1.8+
2. Apache Maven v3.3+
3. Postgres 9.6+

## Installation

```
git clone git@github.com:poc-cookies/spring-boot-web.git
```

## Local Database Setup

Login with your system user into Postgres, and execute the following commands:

```sql
CREATE ROLE example_user WITH LOGIN PASSWORD 'guest';
CREATE DATABASE example_db WITH OWNER example_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO example_user;
```

Login with `example_user` into Postgres and execute the following command:

```sql
CREATE SCHEMA IF NOT EXISTS example_schema AUTHORIZATION example_user;
```

## Database migrations

Database migrations are executed by [Flyway](https://flywaydb.org)

The migrations are scripts in the form `V<VERSION>__<NAME>.sql` which reside in a folder called `classpath:db/migration`

In order to create a new migration script use the following algorithm:
1. Define migration name
2. Generate migration version number with `date -u +%s` 
3. Inject generated version and name into `V<VERSION>__<NAME>.sql` pattern

In order to apply migrations define database connection properties and run:

```
FLYWAY_URL=DATABASE_URL FLYWAY_USER=DB_USER FLYWAY_PASSWORD=DB_PASSWORD mvn flyway:migrate
```

For example, in order to apply migrations in development environment the following command can be used:

```
FLYWAY_URL=jdbc:postgresql://localhost:5432/example_db?currentSchema=example_schema FLYWAY_USER=example_user FLYWAY_PASSWORD=guest mvn flyway:migrate
```

For getting more info have a look at [Flyway Maven Plugin](https://flywaydb.org/getstarted/firststeps/maven)

## Building

`mvn package` will give you an executable jar `target/myproject-0.0.1-SNAPHOT.jar`

## Running

Running as a packaged application:

```
java -jar target/myproject-0.0.1-SNAPHOT.jar
```

Running as a packaged application with `production` profile:

```
SPRING_PROFILES_ACTIVE=production java -jar target/myproject-0.0.1-SNAPSHOT.jar
```

Running Spring Boot application in development mode:

```
mvn spring-boot:run
```

Running Spring Boot application in development mode with `production` profile:

```
SPRING_PROFILES_ACTIVE=production mvn spring-boot:run
```

## Environment

The following environment variables are used:

* `SPRING_PROFILES_ACTIVE` - is necessary for loading environment specific configuration

## Logging

In addition to the console output, logs are written to the `app.log` file.
Log file destination is controlled by `logging.file.name` property of Spring Boot Application.

To get more information on how to customize logging click here [Logging](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-logging)

## Tests

Run unit tests:

```
mvn test
```

## Metrics

Metrics are done with [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/production-ready-features.html#production-ready-metrics)

Application is configured to provide the following set of endpoints for monitoring:

* Health check: `/actuator/health`
* Application info: `/actuator/info`
* Prometheus metrics: `/actuator/prometheus`

## API Documentation

[SpringFox Project](http://springfox.github.io/springfox/) implementation of the Swagger 2 specification is used for instrumentation & documentation of API.

API documentation is not applied when `production` profile is active.

Swagger UI is accessible by the following path: `${API_BASE}/swagger-ui.html`
API docs in the form of JSON available at `${API_BASE}/v2/api-docs`.

## Documentation

[Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/documentation-overview.html#boot-documentation)


## License

Copyright © 2019 FIXME
