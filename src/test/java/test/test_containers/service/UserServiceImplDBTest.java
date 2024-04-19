package test.test_containers.service;

import firstTask.com.config.DataBaseConfig;
import firstTask.com.exceptions.NotUniqueWorkoutException;
import firstTask.com.model.ConsoleUser;
import firstTask.com.model.Workout;
import firstTask.com.repository.UserRepository;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
class UserServiceImplDBTest extends BaseTestDB {
    @Mock
    private ConsoleUser consoleUser;

    private final UserServiceImpl userService = new UserServiceImpl(new WorkoutRepository(), new UserRepository(new WorkoutRepository()));


    @BeforeEach
    public void beforeEachTest() throws SQLException {
        String deleteQuery = "DELETE FROM entities.workouts";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        }
    }

    @Test
    @DisplayName("ѕроверка добавлени€ тренировки в базу данных")
    void addNewWorkout() throws NotUniqueWorkoutException, SQLException {
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