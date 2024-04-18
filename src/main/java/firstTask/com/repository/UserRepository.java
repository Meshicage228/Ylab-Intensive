package firstTask.com.repository;

import firstTask.com.model.ConsoleUser;
import firstTask.com.util.DataBaseConfig;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class UserRepository {
    private WorkoutRepository workoutRepository;

    // TODO : изменить начальный индекс sequence из-за liquibase
    public ConsoleUser save(ConsoleUser newUser) {
        String saveSql = "INSERT INTO entities.users (username, password, role) VALUES (?,?,?)";
        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveSql)){
            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getRole());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
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
                    .workouts(workoutRepository.getWorkoutsByUserId(resultSet.getInt("user_id")))
                    .build() : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
