package firstTask.com.service;

import firstTask.com.exceptions.NotUniqueUserNameException;
import firstTask.com.model.ConsoleUser;
import firstTask.com.model.UserStorage;
import firstTask.com.model.Workout;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

/**
 * класс для регистрации и входа в аккаунт
 */

@AllArgsConstructor
public class AuthenticationService {
    /**
     * Метод регистрации пользователя.
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     * @exception NotUniqueUserNameException исключение неуникального имени
     * @see ConsoleUser пользователь приложения
     **/
    public void registrationProcess(String username, String password) throws NotUniqueUserNameException {
        if (!userIsExists(username)) {
            String role = "USER";
            if(username.toLowerCase().equals("admin")){
                role = "ADMIN";
            }
            ConsoleUser newUser = ConsoleUser.builder()
                    .username(username)
                    .password(password)
                    .workouts(new ArrayList<>())
                    .role(role)
                    .build();

            UserStorage.getAllUsers().add(newUser);
        } else {
            throw new NotUniqueUserNameException("Пользователь с таким именем уже существует");
        }
    }
    /**
     * Метод входа в аккаунт.
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     * @see ConsoleUser пользователь приложения
     * @return Optional<ConsoleUser> : optional обёртка полученного зарегестрированного пользователя
     **/
    public Optional<ConsoleUser> logIn(String username, String password) {
        return UserStorage.getAllUsers().stream()
                    .filter(consoleUser -> consoleUser.getUsername().equals(username) && consoleUser.getPassword().equals(password))
                    .findFirst();
    }

    /**
     * Метод проверки на существование пользователя.
     * @param username уникальное имя пользователя
     * @see ConsoleUser пользователь приложения
     * @return boolean : наличие / отсутствие пользователя
     **/
    public boolean userIsExists(String username) {
        return UserStorage.getAllUsers().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
