package trainingDiary.com.mappers.rowMappers;

import trainingDiary.com.model.Workout;
import trainingDiary.com.model.WorkoutType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WorkoutRowMapper implements RowMapper<Workout> {
    @Override
    public Workout mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        WorkoutType type = WorkoutType.builder()
                .type_id(resultSet.getInt("type_id"))
                .typeTitle(resultSet.getString("type"))
                .build();

        return Workout.builder()
                .id(resultSet.getInt("workout_id"))
                .user_id(resultSet.getInt("user_id"))
                .timeOfWorkout(resultSet.getDate("adding_date").toLocalDate())
                .caloriesBurned(resultSet.getDouble("calories_burned"))
                .additionalInfo(resultSet.getString("additional_info"))
                .dateOfAdding(resultSet.getDate("training_date_creation").toLocalDate())
                .minuteDuration(resultSet.getDouble("minute_duration"))
                .workoutType(type)
                .build();
    }
}
