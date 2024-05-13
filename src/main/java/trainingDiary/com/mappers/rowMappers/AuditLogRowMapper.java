package trainingDiary.com.mappers.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import trainingDiary.com.model.Audit;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuditLogRowMapper implements RowMapper<Audit> {
    @Override
    public Audit mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Audit.builder()
                .id(resultSet.getInt("log_id"))
                .timeOfLog(resultSet.getDate("time_of_action").toLocalDate())
                .message(resultSet.getString("action_description"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }
}
