/*
package test.test_containers.repository;

import first_task.com.model.ConsoleUser;
import first_task.com.repository.UserRepository;
import first_task.com.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест юзер-репозитория")
@Testcontainers
class UserRepositoryTest extends BaseTestDB {

    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        userRepository = new UserRepository(new WorkoutRepository());
    }

    @BeforeEach
    public void beforeEachTest() throws SQLException {
        String deleteQuery = "DELETE FROM entities.users";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        }
    }

    @Test
    @DisplayName("Сохранение пользователя")
    void save() throws SQLException {
        ConsoleUser toSave = ConsoleUser.builder()
                .username("Vlad")
                .role("USER")
                .password("123")
                .build();

        String selectQuery = "SELECT COUNT(*) FROM entities.users";
        int beforeAdding = 0;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            assertTrue(resultSet.next());
            beforeAdding = resultSet.getInt(1);
        }

        try (MockedStatic<DataBaseConfig> utilities = Mockito.mockStatic(DataBaseConfig.class)) {
            utilities.when(DataBaseConfig::getConnection).thenReturn(DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD));
            userRepository.save(toSave);
        }

        int afterAdding = 0;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            assertTrue(resultSet.next());
            afterAdding = resultSet.getInt(1);
        }

        assertEquals(beforeAdding + 1, afterAdding);
    }

    @Test
    @DisplayName("Поиск пользователя по имени")
    void findUserByUsername() throws SQLException {
        try (MockedStatic<DataBaseConfig> utilities = Mockito.mockStatic(DataBaseConfig.class)) {
            utilities.when(DataBaseConfig::getConnection).thenReturn(DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD));
            userRepository.findUserByUsername("Vlad");
        }

        String selectQuery = "SELECT COUNT(*) FROM entities.users";
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            assertTrue(resultSet.next());
            count = resultSet.getInt(1);
            assertEquals(0, count);
        }
    }
}*/
