package firstTask.com.repository;

import firstTask.com.model.ConsoleUser;
import firstTask.com.config.DataBaseConfig;
import lombok.AllArgsConstructor;

import java.sql.*;

/**
 * Класс - репозиторий, который связывается с базой данных users
 *
 * @see WorkoutRepository Класс-репозиторий для связи с тренировками
 *  **/

@AllArgsConstructor
public class UserRepository {
    private WorkoutRepository workoutRepository;

    /**
     * Метод сохранения нового пользователя
     * @param newUser {@link ConsoleUser пользователь приложения}
     * @return Сохраненный пользователь приложения
     *  **/
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

    /**
     * Метод поиска пользователя по имени
     * @param username имя пользователя
     * @return true/false - пользователь найден/не найден
     *  **/
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

    /**
     * Метод поиска пользователя по имени и паролю
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true/false - пользователь найден/не найден
     *  **/
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

    /**
     * Метод администратора для вывода всех тренировок в приложении
     * @return результирущая строка вывода всех тренировок; "Нет активных тренировок" - при отутствии тренировок в приложении
     *  **/
    public String getAll() {
        String allUsersSql = "SELECT user_id, username FROM entities.users";
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(allUsersSql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("username");
                int id = resultSet.getInt("user_id");
                System.out.println("Username : " + userName + " and trainings :");

                Statement innerStatement = connection.createStatement();
                ResultSet result = innerStatement.executeQuery("SELECT * FROM entities.workouts WHERE user_id = '" + id + "'");

                while (result.next()) {
                    String answer =
                            "Training type : %s; Burned calories : %s, Date of training : %s, Training duration : %s";
                    return String.format(answer,
                            result.getString("type"),
                            result.getDouble("calories_burned"),
                            result.getDate("adding_date"),
                            result.getDouble("minute_duration"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Нет активных тренировок";
    }
}
