package test.test_containers.repository;

import FirstTask.com.config.DataBaseConfig;
import FirstTask.com.repository.AuditRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест аудит-репозитория")
@Testcontainers
class AuditRepositoryTest extends BaseTestDB {

    private static AuditRepository auditRepository;

    @BeforeAll
    public static void setUp() {
        auditRepository = new AuditRepository();
    }

    @Test
    @DisplayName("Сохранение аудита")
    void saveAudit() throws SQLException {
        try (MockedStatic<DataBaseConfig> utilities = Mockito.mockStatic(DataBaseConfig.class)) {
            utilities.when(DataBaseConfig::getConnection).thenReturn(DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD));
            auditRepository.saveAudit("123", 1);
        }

        String selectQuery = "SELECT COUNT(*) FROM audit_log.audit_logs";
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            assertTrue(resultSet.next());
            count = resultSet.getInt(1);
            assertEquals(1, count);
        }
    }
}