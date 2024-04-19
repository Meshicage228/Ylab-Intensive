package test.test_containers;

import firstTask.com.config.DataBaseConfig;
import firstTask.com.model.ConsoleUser;
import firstTask.com.model.Workout;
import firstTask.com.repository.UserRepository;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.service.impl.UserServiceImpl;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.Driver;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


/**
 *  ласс, тестирующий {@link UserServiceImpl} класс с базой данных
 *  **/
@ExtendWith(MockitoExtension.class)
@DisplayName("“ест действий пользовател€ над тренировками")
@Testcontainers
class UserServiceImplDBTest {
    @Mock
    private ConsoleUser consoleUser;

    private final UserServiceImpl userService = new UserServiceImpl(new WorkoutRepository(), new UserRepository(new WorkoutRepository()));

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.3-alpine")
            .withDatabaseName("y_labTest")
            .withUsername("test")
            .withPassword("123");

    private static String JDBCURL = "";
    private static String USERNAME = "";
    private static String PASSWORD= "";

    private static Connection connection;


    @BeforeAll
    @SneakyThrows
    public static void setup(){
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
    @SneakyThrows
    public static void teardown(){
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @BeforeEach
    @SneakyThrows
    public void beforeEachTest(){
        String deleteQuery = "DELETE FROM entities.workouts";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        }
    }

    @Test
    @DisplayName("ѕроверка добавлени€ тренировки в базу данных")
    @SneakyThrows
    void addNewWorkout(){
        Workout newWorkout = Workout.builder()
                .caloriesBurned(1000d)
                .type("youga123")
                .user_id(1)
                .dateOfAdding(LocalDate.now())
                .additionalInfo("good")
                .minuteDuration(123d)
                .timeOfWorkout(LocalDate.parse("2024-12-10"))
                .build();

        when(consoleUser.getWorkouts()).thenReturn(new LinkedList<>());

        try (MockedStatic<DataBaseConfig> utilities = Mockito.mockStatic(DataBaseConfig.class)) {
            utilities.when(DataBaseConfig::getConnection).thenReturn(DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD));
            userService.addNewWorkout(consoleUser, newWorkout);
        }
        String selectQuery = "SELECT COUNT(*) FROM entities.workouts";
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            assertTrue(resultSet.next());
            count = resultSet.getInt(1);
            assertEquals(1, count);
        }
    }
}