package firstTask.com.repository;

import firstTask.com.model.Workout;
import firstTask.com.util.DataBaseConfig;

import java.sql.*;
import java.util.LinkedList;

public class WorkoutRepository {

    public LinkedList<Workout> getWorkoutsByUserId(int userId) {
        LinkedList<Workout> workouts = new LinkedList<>();

        String query = "SELECT * FROM entities.workouts WHERE user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Workout build = Workout.builder()
                        .id(resultSet.getInt("workout_id"))
                        .timeOfWorkout(resultSet.getDate("adding_date").toLocalDate())
                        .caloriesBurned(resultSet.getDouble("calories_burned"))
                        .additionalInfo(resultSet.getString("additional_info"))
                        .dateOfAdding(resultSet.getDate("training_date_creation").toLocalDate())
                        .minuteDuration(resultSet.getDouble("minute_duration"))
                        .type(resultSet.getString("type"))
                        .build();

                workouts.add(build);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return workouts;
    }

    public Workout saveWorkout(Workout workout) {
        String query = "INSERT INTO entities.workouts VALUES(?,?,?,?,?,?,?,?)";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(2, workout.getUser_id());
            preparedStatement.setDate(3, Date.valueOf(workout.getDateOfAdding()));
            preparedStatement.setDate(4, Date.valueOf(workout.getTimeOfWorkout()));
            preparedStatement.setString(5, workout.getAdditionalInfo());
            preparedStatement.setString(6, workout.getType());
            preparedStatement.setDouble(7, workout.getCaloriesBurned());
            preparedStatement.setDouble(8, workout.getMinuteDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return workout;
    }
}
