package firstTask.com.repository;

import firstTask.com.config.DataBaseConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AuditRepository {
    public void saveAudit(String actionDescription){
        String saveSql = "INSERT INTO audit_log.audit_logs (time_of_action, action_description) VALUES(?,?)";
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveSql)){
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(2, actionDescription);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
