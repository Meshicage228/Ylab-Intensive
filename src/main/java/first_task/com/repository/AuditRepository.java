package first_task.com.repository;

import first_task.com.config.DataBaseConfig;

import java.sql.*;
import java.time.LocalDate;

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
        String saveSql = "INSERT INTO audit_log.audit_logs (time_of_action, action_description, user_id) VALUES(?,?,?)";
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveSql)){
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
