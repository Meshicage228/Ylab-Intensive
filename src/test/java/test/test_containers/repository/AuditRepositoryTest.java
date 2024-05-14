/*
package test.test_containers.repository;

import repository.trainingDiary.com.AuditRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.junit.jupiter.Testcontainers;
import test.TestConfiguration;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@DisplayName("Тест аудит-репозитория")
class AuditRepositoryTest{

    private static AuditRepository auditRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        auditRepository = new AuditRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("Сохранение аудита")
    void saveAudit() throws SQLException {
        Integer count_before = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entities.audit_logs", new BeanPropertyRowMapper<>(Integer.class));

        auditRepository.saveAudit("Pip", 1);

        Integer countAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entities.audit_logs", new BeanPropertyRowMapper<>(Integer.class));

        Assertions.assertEquals(count_before + 1, countAfter);
    }
}
*/
