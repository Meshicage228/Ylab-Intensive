package first_task.com.mappers.rowMappers;

import first_task.com.model.ConsoleUser;
import first_task.com.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class ConsoleUserRowMapper implements RowMapper<ConsoleUser> {
    private final WorkoutRepository repository;
    @Override
    public ConsoleUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {

       return ConsoleUser.builder()
                .id(resultSet.getInt("user_id"))
                .username(resultSet.getString("username"))
                .role(resultSet.getString("role"))
                .password(resultSet.getString("password"))
                .workouts(repository.getWorkoutsByUserId(resultSet.getInt("user_id")))
                .build();
    }
}
