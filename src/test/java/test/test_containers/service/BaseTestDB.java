package test.test_containers.service;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.postgresql.Driver;
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

    public static String JDBCURL = "";
    public static String USERNAME = "";
    public static String PASSWORD= "";

    public static Connection connection;


    @BeforeAll
    public static void setup() throws SQLException, LiquibaseException {
        JDBCURL = postgresContainer.getJdbcUrl();
        USERNAME = postgresContainer.getUsername();
        PASSWORD = postgresContainer.getPassword();
        DriverManager.registerDriver(new Driver());

        connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);

        Statement statement = connection.createStatement();

        String schemaName = "service_liquibase";
        String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS " + schemaName;

        statement.executeUpdate(createSchemaQuery);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        database.setDefaultSchemaName("service_liquibase");
        Liquibase liquibase = new Liquibase("db.changelog/change-logs.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update();
    }

    @AfterAll
    public static void teardown() throws SQLException {
        if (nonNull(connection) && !connection.isClosed()) {
            connection.close();
        }
    }
}
