package first_task.com.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.postgresql.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Класс-конфиг, устанавливающий соединение с базой и миграцией данных
 **/
public class DataBaseConfig {
    /** Расположение пакета с зависимостями **/
    private static final String CONFIG_FILE = "src/main/resources/configs/dataBase.properties";
    /** URL базы данных **/
    private static String URL;
    /** имя пользователя базы данных **/
    private static String USER;
    /** Пароль от базы данных **/
    private static String PASSWORD;
    /** Добавление стандарта кодирования **/
    private static String useUnicode;
    /** Режим кодировки в базе данных **/
    private static String characterEncoding;

    static {
        try {
            loadConfig();
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            URL = properties.getProperty("database.url");
            USER = properties.getProperty("database.user");
            PASSWORD = properties.getProperty("database.password");
            useUnicode = properties.getProperty("database.useUnicode");
            characterEncoding = properties.getProperty("database.characterEncoding");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод соединения приложения с базой данных
     * @return Connection : соединение с бд
     **/
    public static Connection getConnection() {
        try {
            Properties info = new Properties();
            info.setProperty("user", USER);
            info.setProperty("password",PASSWORD);
            info.setProperty("useUnicode",useUnicode);
            info.setProperty("characterEncoding",characterEncoding);
            return DriverManager.getConnection (URL, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Метод соединения приложения с миграцией баз данных : liquibase
     **/
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