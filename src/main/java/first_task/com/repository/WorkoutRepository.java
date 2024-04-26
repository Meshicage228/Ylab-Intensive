package first_task.com.repository;

import first_task.com.dto.WorkoutDto;
import first_task.com.model.Workout;
import first_task.com.config.DataBaseConfig;
import first_task.com.model.WorkoutType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Класс - репозиторий, ответственный за соединение с бд workouts
 *  **/
public class WorkoutRepository {

    public ArrayList<Workout> getWorkoutsByUserId(int userId) {
        ArrayList<Workout> workouts = new ArrayList<>();

        String query = "SELECT * FROM entities.workouts as w LEFT JOIN entities.types as tp on w.workout_type_id = tp.type_id WHERE user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WorkoutType type = WorkoutType.builder()
                        .type_id(resultSet.getInt("type_id"))
                        .typeTitle(resultSet.getString("type"))
                        .build();

                Workout build = Workout.builder()
                        .id(resultSet.getInt("workout_id"))
                        .user_id(resultSet.getInt("user_id"))
                        .timeOfWorkout(resultSet.getDate("adding_date").toLocalDate())
                        .caloriesBurned(resultSet.getDouble("calories_burned"))
                        .additionalInfo(resultSet.getString("additional_info"))
                        .dateOfAdding(resultSet.getDate("training_date_creation").toLocalDate())
                        .minuteDuration(resultSet.getDouble("minute_duration"))
                        .workoutType(type)
                        .build();

                workouts.add(build);
            }
        } catch (SQLException e) {
            System.out.println("Fail to find your workouts!");
        }
        return workouts;
    }

    public Workout getWorkoutById(int workoutId) {
        String query = "SELECT * FROM entities.workouts as w LEFT JOIN entities.types as tp on w.workout_type_id = tp.type_id WHERE workout_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, workoutId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                WorkoutType type = WorkoutType.builder()
                        .type_id(resultSet.getInt("type_id"))
                        .typeTitle(resultSet.getString("type"))
                        .build();

                return Workout.builder()
                        .id(resultSet.getInt("workout_id"))
                        .timeOfWorkout(resultSet.getDate("adding_date").toLocalDate())
                        .caloriesBurned(resultSet.getDouble("calories_burned"))
                        .additionalInfo(resultSet.getString("additional_info"))
                        .dateOfAdding(resultSet.getDate("training_date_creation").toLocalDate())
                        .minuteDuration(resultSet.getDouble("minute_duration"))
                        .workoutType(type)
                        .user_id(resultSet.getInt("user_id"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println("Fail to find workout!");
        }
        return null;
    }

    /**
     * Метод сохранения новой тренировки
     * @param workout {@link Workout тренировка пользователя}
     * @return сохраненная тренировка
     *  **/
    public WorkoutDto saveWorkout(Integer user_id, WorkoutDto workout) {
        String query = "INSERT INTO entities.workouts (user_id, training_date_creation, adding_date, additional_info, workout_type_id, calories_burned, minute_duration)" +
                " VALUES(?,?,?,?,?,?,?)";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(3, Date.valueOf(workout.getTimeOfWorkout()));
            preparedStatement.setString(4, workout.getAdditionalInfo());
            preparedStatement.setInt(5, workout.getWorkoutType().getType_id());
            preparedStatement.setDouble(6, workout.getCaloriesBurned());
            preparedStatement.setDouble(7, workout.getMinuteDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to save workout!");
        }
        return workout;
    }

    /**
     * Метод обновления продолжительности тренировки
     * @param user_id        id владельца тренировки
     * @param workout_id     id тренировки для изменения
     * @param changeDuration новая продолжительность тренировки
     * @return обновленная тренировка
     *  **/
    public Workout updateMinutes(Integer user_id, Integer workout_id, Double changeDuration) {
        String query = "UPDATE entities.workouts SET minute_duration = ? WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, changeDuration);
            preparedStatement.setInt(2, workout_id);
            preparedStatement.setInt(3, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update minutes!");
        }
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления сожённых калорий тренировки
     * @param user_id id владельца тренировки
     * @param workout_id id тренировки для изменения
     * @param changeCalories новые соженные калории
     * @return обновленная тренировка
     *  **/
    public Workout changeCalories(Integer user_id, Integer workout_id, Double changeCalories) {
        String query = "UPDATE entities.workouts SET calories_burned = ? WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, changeCalories);
            preparedStatement.setInt(2, workout_id);
            preparedStatement.setInt(3, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update calories!");
        }
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления дополнительной информации тренировки
     * @param user_id id владельца тренировки
     * @param workout_id айди тренировки для изменения
     * @param newAddInfo новая дополнительная информация о тренировке
     * @return обновленная тренировка
     *  **/
    public Workout changeAdditional(Integer user_id, Integer workout_id, String newAddInfo) {
        String query = "UPDATE entities.workouts SET additional_info = ? WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newAddInfo);
            preparedStatement.setInt(2, workout_id);
            preparedStatement.setInt(3, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update additional info!");
        }
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления даты-начала тренировки
     * @param user_id id владельца тренировки
     * @param workout_id айди тренировки для изменения
     * @param newDate новая дата тренировки
     * @return обновленная тренировка
     *  **/
    public Workout changeDate(Integer user_id, Integer workout_id, LocalDate newDate) {
        String query = "UPDATE entities.workouts SET adding_date = ? WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(newDate));
            preparedStatement.setInt(2, workout_id);
            preparedStatement.setInt(3, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to update date!");
        }
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления удаления тренировки
     * @param workout_id айди тренировки для удаления
     * @param user_id айди владельца тренировки
     *  **/
    public void deleteWorkout(Integer user_id, Integer workout_id) {
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
