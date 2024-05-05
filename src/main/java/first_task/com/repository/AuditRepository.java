package first_task.com.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;

import static first_task.com.util.SQLUtilQueries.*;

/**
 * Класс - репозиторий, который связывается с базой данных audit_logs
 *  **/
@Repository
@RequiredArgsConstructor
public class AuditRepository {
    private final JdbcTemplate jdbc;
    /**
     * Метод сохранения действия пользователя в базу данных audit_logs
     * @param actionDescription Описание действия пользователя
     * @param user_id Внешний ключ пользователя
     *  **/
    public void saveAudit(String actionDescription, Integer user_id){
        jdbc.update(SAVE_AUDIT, Date.valueOf(LocalDate.now()), actionDescription, user_id);
    }
}
