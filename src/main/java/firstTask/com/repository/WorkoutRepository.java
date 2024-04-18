package firstTask.com.repository;

import firstTask.com.model.Workout;
import firstTask.com.config.DataBaseConfig;

import java.sql.*;
import java.time.LocalDate;
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
            System.out.println("Fail to find your workouts!");
        }
        return workouts;
    }

    public Workout getWorkoutById(int workoutId) {
        String query = "SELECT * FROM entities.workouts WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, workoutId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Workout.builder()
                        .id(resultSet.getInt("workout_id"))
                        .timeOfWorkout(resultSet.getDate("adding_date").toLocalDate())
                        .caloriesBurned(resultSet.getDouble("calories_burned"))
                        .additionalInfo(resultSet.getString("additional_info"))
                        .dateOfAdding(resultSet.getDate("training_date_creation").toLocalDate())
                        .minuteDuration(resultSet.getDouble("minute_duration"))
                        .type(resultSet.getString("type"))
                        .user_id(resultSet.getInt("user_id"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println("Fail to find workout!");
        }
        return null;
    }

    public Workout saveWorkout(Workout workout) {
        String query = "INSERT INTO entities.workouts (user_id, training_date_creation, adding_date, additional_info, type, calories_burned, minute_duration)" +
                " VALUES(?,?,?,?,?,?,?)";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, workout.getUser_id());
            preparedStatement.setDate(2, Date.valueOf(workout.getDateOfAdding()));
            preparedStatement.setDate(3, Date.valueOf(workout.getTimeOfWorkout()));
            preparedStatement.setString(4, workout.getAdditionalInfo());
            preparedStatement.setString(5, workout.getType());
            preparedStatement.setDouble(6, workout.getCaloriesBurned());
            preparedStatement.setDouble(7, workout.getMinuteDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to save workout!");
        }
        return workout;
    }

    public Workout updateMinutes(Integer id, Double changeDuration) {
        String query = "UPDATE entities.workouts SET minute_duration = ? WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, changeDuration);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update minutes!");
        }
        return getWorkoutById(id);
    }

    public Workout changeCalories(Integer id, Double changeCalories) {
        String query = "UPDATE entities.workouts SET calories_burned = ? WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, changeCalories);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update calories!");
        }
        return getWorkoutById(id);
    }

    public Workout changeAdditional(Integer id, String newAddInfo) {
        String query = "UPDATE entities.workouts SET additional_info = ? WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newAddInfo);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update additional info!");
        }
        return getWorkoutById(id);
    }

    public Workout changeType(Integer id, String newType) {
        String query = "UPDATE entities.workouts SET type = ? WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newType);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update type!");
        }
        return getWorkoutById(id);
    }

    public Workout changeDate(Integer id, LocalDate newDate) {
        String query = "UPDATE entities.workouts SET adding_date = ? WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(newDate));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update date!");
        }
        return getWorkoutById(id);
    }

    public void deleteWorkout(Integer workout_id, Integer user_id) {
        String query = "DELETE FROM entities.workouts WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, workout_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to delete workout!");
        }
    }
}
