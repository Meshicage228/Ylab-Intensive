/*
package test.test_containers.service;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.postgresql.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Objects.nonNull;

public abstract class BaseTestDB {
    @Container
    public static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.3-alpine")
            .withDatabaseName("y_labTest")
            .withUsername("test")
            .withPassword("123");

    public static JdbcTemplate jdbcTemplate;


    @BeforeAll
    public static void setup() throws LiquibaseException, SQLException {
        jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword(),
                true));

        jdbcTemplate.update("CREATE SCHEMA IF NOT EXISTS " + "service_liquibase");

        Connection connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        database.setDefaultSchemaName("service_liquibase");
        Liquibase liquibase = new Liquibase("db.changelog/change-logs.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update();
    }
}
*/
