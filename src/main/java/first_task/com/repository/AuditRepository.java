package first_task.com.repository;

import first_task.com.config.DataBaseConfig;

import java.sql.*;
import java.time.LocalDate;

import static first_task.com.util.SQLUtilQueries.*;

/**
 * Класс - репозиторий, который связывается с базой данных audit_logs
 *  **/
public class AuditRepository {

    /**
     * Метод сохранения действия пользователя в базу данных audit_logs
     * @param actionDescription Описание действия пользователя
     * @param user_id Внешний ключ пользователя
     *  **/
    public void saveAudit(String actionDescription, Integer user_id){
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_AUDIT)){
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(2, actionDescription);
            if (user_id != null) {
                preparedStatement.setInt(3, user_id);
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
