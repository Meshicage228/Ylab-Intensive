package test.test_containers.repository;

import firstTask.com.config.DataBaseConfig;
import firstTask.com.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.junit.jupiter.Testcontainers;
import test.test_containers.service.BaseTestDB;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@DisplayName("Тесты над тренировками")
class WorkoutRepositoryTest extends BaseTestDB {

    private static WorkoutRepository workoutRepository;

    @BeforeAll
    static void setUp() {
        workoutRepository = new WorkoutRepository();
    }

    @Test
    @DisplayName("Обновление калорий")
    void changeCalories() throws SQLException {
        try (MockedStatic<DataBaseConfig> utilities = Mockito.mockStatic(DataBaseConfig.class)) {
            utilities.when(DataBaseConfig::getConnection).thenReturn(DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD));
            workoutRepository.changeCalories(1, 100d);
        }

        String selectQuery = "SELECT calories_burned FROM entities.workouts WHERE workout_id=?";
        double workouts = 0;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, 1);

            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            workouts = resultSet.getDouble("calories_burned");
            assertEquals(100d, workouts);
        }
    }
}