package first_task.com.repository;

import first_task.com.model.ConsoleUser;
import first_task.com.config.DataBaseConfig;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - репозиторий, ответственный за соединение с бд users
 *
 * @see WorkoutRepository класс-репозиторий workouts
 **/

@AllArgsConstructor
public class UserRepository {
    private WorkoutRepository workoutRepository;
    /**
     * Метод сохранения пользователя
     *
     * @param newUser {@link ConsoleUser пользователь приложения}
     * @return ConsoleUser - сохраненный пользователь
     **/
    public ConsoleUser save(ConsoleUser newUser) {
        String saveSql = "INSERT INTO entities.users (username, password, role) VALUES (?,?,?)";
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql)) {
            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            new RuntimeException("Error while saving user: " + newUser.getUsername(), e);
        }
        return newUser;
    }

    /**
     * Метод поиска пользователя по имени
     *
     * @param username имя пользователя
     * @return true/false пользователь найден / не найден
     **/
    public boolean findUserByUsername(String username) {
        String findUserByUsernameSql = "SELECT * FROM entities.users WHERE username = ?";
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findUserByUsernameSql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            new RuntimeException("Error while finding user: " + username, e);
        }
        return false;
    }

    /**
     * Метод поиска пользователя по имени и паролю
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return ConsoleUser найденный пользователь
     **/
    public ConsoleUser findByUsernameAndPassword(String username, String password) {
        String findByUsernameAndPasswordSql = "SELECT * FROM entities.users WHERE username = ? AND password = ?";
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByUsernameAndPasswordSql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? ConsoleUser.builder()
                    .id(resultSet.getInt("user_id"))
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .role(resultSet.getString("role"))
                    .workouts(workoutRepository.getWorkoutsByUserId(resultSet.getInt("user_id")))
                    .build() : null;

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding user: " + username, e);
        }
    }

    /**
     * Метод получения всех тренировок от всех пользователей
     *
     * @return Результирующая строка со всей информацией
     **/
    public List<ConsoleUser> getAll() {
        String query = """
                SELECT username, us.user_id, role, password FROM entities.workouts as w 
                LEFT JOIN entities.types as tp ON w.workout_type_id = tp.type_id
                LEFT JOIN entities.users as us ON w.user_id = us.user_id
                """;
        ArrayList<ConsoleUser> users = new ArrayList<>();
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ConsoleUser user = ConsoleUser.builder()
                        .id(resultSet.getInt("user_id"))
                        .username(resultSet.getString("username"))
                        .role(resultSet.getString("role"))
                        .password(resultSet.getString("password"))
                        .workouts(workoutRepository.getWorkoutsByUserId(resultSet.getInt("user_id")))
                        .build();

                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error while finding workouts: " + ex.getMessage(), ex);
        }
        return users;
    }
}
