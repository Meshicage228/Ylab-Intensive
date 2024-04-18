package firstTask.com.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DataBaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5431/y_lab";

    private static final String USER = "testMan";

    private static final String PASSWORD = "123123";

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties info = new Properties();
            info.setProperty("user", USER);
            info.setProperty("password",PASSWORD);
            info.setProperty("useUnicode","true");
            info.setProperty("characterEncoding","utf8");
            return DriverManager.getConnection (URL, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void liquibaseStart(){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){

            String schemaName = "service_liquibase";
            String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS " + schemaName;

            statement.executeUpdate(createSchemaQuery);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            database.setDefaultSchemaName("service_liquibase");
            Liquibase liquibase = new Liquibase("db.changelog/change-logs.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}