package trainingDiary.com.mappers.rowMappers;

import trainingDiary.com.model.WorkoutType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WorkoutTypeRowMapper implements RowMapper<WorkoutType> {
    @Override
    public WorkoutType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return WorkoutType.builder()
                .type_id(resultSet.getInt("type_id"))
                .typeTitle(resultSet.getString("type"))
                .build();
    }
}
