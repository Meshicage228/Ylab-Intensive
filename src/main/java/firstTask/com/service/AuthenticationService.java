package firstTask.com.service;

import firstTask.com.exceptions.NotUniqueUserNameException;
import firstTask.com.model.ConsoleUser;
import firstTask.com.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.Optional;

/**
 * класс для регистрации и входа в аккаунт
 */

@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
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
                    .workouts(new LinkedList<>())
                    .role(role)
                    .build();
            userRepository.save(newUser);
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
        return Optional.ofNullable(userRepository.findByUsernameAndPassword(username, password));
    }

    /**
     * Метод проверки на существование пользователя.
     * @param username уникальное имя пользователя
     * @see ConsoleUser пользователь приложения
     * @return boolean : наличие / отсутствие пользователя
     **/
    public boolean userIsExists(String username) {
        return userRepository.findUserByUsername(username);
    }
}
