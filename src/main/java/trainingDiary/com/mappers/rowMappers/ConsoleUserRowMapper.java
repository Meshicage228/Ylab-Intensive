package trainingDiary.com.mappers.rowMappers;

import trainingDiary.com.model.AppUser;
import trainingDiary.com.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class ConsoleUserRowMapper implements RowMapper<AppUser> {
    private final WorkoutRepository repository;
    @Override
    public AppUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {

       return AppUser.builder()
                .id(resultSet.getInt("user_id"))
                .username(resultSet.getString("username"))
                .role(resultSet.getString("role"))
                .password(resultSet.getString("password"))
                .workouts(repository.getWorkoutsByUserId(resultSet.getInt("user_id")))
                .build();
    }
}
