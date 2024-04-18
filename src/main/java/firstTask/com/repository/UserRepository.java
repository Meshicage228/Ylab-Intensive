package firstTask.com.repository;

import firstTask.com.model.ConsoleUser;
import firstTask.com.config.DataBaseConfig;
import firstTask.com.model.Workout;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.LinkedList;

@AllArgsConstructor
public class UserRepository {
    private WorkoutRepository workoutRepository;

    public ConsoleUser save(ConsoleUser newUser) {
        String saveSql = "INSERT INTO entities.users (username, password, role) VALUES (?,?,?)";
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveSql)){
            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail to register your account!");
        }
        return newUser;
    }
    public boolean findUserByUsername(String username) {
        String findUserByUsernameSql = "SELECT * FROM entities.users WHERE username = ?";
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findUserByUsernameSql)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Fail to find your account by username!");
        }
        return false;
    }

    public ConsoleUser findByUsernameAndPassword(String username, String password) {
        String findByUsernameAndPasswordSql = "SELECT * FROM entities.users WHERE username = ? AND password = ?";
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findByUsernameAndPasswordSql)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? ConsoleUser.builder()
                    .id(resultSet.getInt("user_id"))
                    .username(resultSet.getString("username"))
                    .role(resultSet.getString("role"))
                    .workouts(workoutRepository.getWorkoutsByUserId(resultSet.getInt("user_id")))
                    .build() : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAll() {
        String allUsersSql = "SELECT user_id, username FROM entities.users";
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(allUsersSql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("username");
                int id = resultSet.getInt("user_id");
                System.out.println("Имя пользователя: " + userName + "Его тренировки :");

                Statement innerStatement = connection.createStatement();
                ResultSet result = innerStatement.executeQuery("SELECT * FROM entities.workouts WHERE user_id = '" + id + "'");

                while (result.next()) {
                    String answer =
                            "Название тренировки : %s; Соженные калории : %s, Дата проведения : %s, продолжительность тренировки : %s";
                    String format = String.format(answer,
                            result.getString("type"),
                            result.getDouble("calories_burned"),
                            result.getDate("adding_date"),
                            result.getDouble("minute_duration"));

                    System.out.println(format);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Нет активных тренировок";
    }
}
