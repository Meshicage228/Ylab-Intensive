package trainingDiary.com.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trainingDiary.com.mappers.AuditLogMapper;
import trainingDiary.com.mappers.rowMappers.AuditLogRowMapper;
import trainingDiary.com.model.AppUser;
import trainingDiary.com.model.Audit;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static trainingDiary.com.util.SQLUtilQueries.*;

/**
 * Класс - репозиторий, который связывается с базой данных audit_logs
 *  **/
@Repository
@RequiredArgsConstructor
public class AuditRepository {
    private final JdbcTemplate jdbc;
    private final AuditLogRowMapper logRowMapper;
    /**
     * Метод сохранения действия пользователя в базу данных audit_logs
     * @param actionDescription Описание действия пользователя
     * @param user_id Внешний ключ пользователя
     *  **/
    public void saveAudit(String actionDescription, Integer user_id){
        jdbc.update(SAVE_AUDIT, Date.valueOf(LocalDate.now()), actionDescription, user_id);
    }

    /**
     * Метод получения всех audit logs в приложении
     *
     * @return Все записи
     **/
    public ArrayList<Audit> getAllAuditLogs() {
        return (ArrayList<Audit>) jdbc.query(GET_ALL_AUDIT_LOGS, logRowMapper);
    }
}
