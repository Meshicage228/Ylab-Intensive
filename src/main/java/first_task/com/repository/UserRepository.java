package first_task.com.repository;

import first_task.com.mappers.rowMappers.ConsoleUserRowMapper;
import first_task.com.model.ConsoleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static first_task.com.util.SQLUtilQueries.*;

/**
 * Класс - репозиторий, ответственный за соединение с бд users
 *
 * @see WorkoutRepository класс-репозиторий workouts
 **/

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final ConsoleUserRowMapper rowMapper;
    private final JdbcTemplate jdbc;
    /**
     * Метод сохранения пользователя
     *
     * @param newUser {@link ConsoleUser пользователь приложения}
     * @return ConsoleUser - сохраненный пользователь
     **/
    public ConsoleUser save(ConsoleUser newUser) {
        jdbc.update(SAVE_USER, new Object[]{newUser.getUsername(), newUser.getPassword(), newUser.getRole()});
        return newUser;
    }

    /**
     * Метод поиска пользователя по имени
     *
     * @param username имя пользователя
     * @return true/false пользователь найден / не найден
     **/
    public boolean findUserByUsername(String username) {
        List<ConsoleUser> query = jdbc.query(FIND_USER_BY_USERNAME, rowMapper, username);
        return !query.isEmpty();
    }

    /**
     * Метод поиска пользователя по имени и паролю
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return ConsoleUser найденный пользователь
     **/
    public ConsoleUser findByUsernameAndPassword(String username, String password) {
        try {
            return jdbc.queryForObject(FIND_USER_BY_USERNAME_AND_PASSWORD, rowMapper, username, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Метод получения всех тренировок от всех пользователей
     *
     * @return Результирующая строка со всей информацией
     **/
    public ArrayList<ConsoleUser> getAll() {
       return (ArrayList<ConsoleUser>) jdbc.query(FIND_ALL_USERS, rowMapper);
    }
}
