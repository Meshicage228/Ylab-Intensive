package first_task.com.service;

import first_task.com.dto.UserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.mappers.UserMapper;
import first_task.com.model.ConsoleUser;
import first_task.com.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

/**
 * класс для регистрации и входа в аккаунт
 */

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    /**
     * Метод регистрации пользователя.
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     * @exception NotUniqueUserNameException исключение неуникального имени
     * @see ConsoleUser пользователь приложения
     **/
    public UserDto registrationProcess(String username, String password) throws NotUniqueUserNameException {
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
            return userMapper.toDto(userRepository.save(newUser));
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
    public Optional<UserDto> logIn(String username, String password) {
        return Optional.ofNullable(
                userMapper.toDto(
                        userRepository.findByUsernameAndPassword(username, password)));
    }

    /**
     * Метод проверки на существование пользователя.
     * @param username уникальное имя пользователя
     * @see ConsoleUser пользователь приложения
     * @return boolean : наличие / отсутствие пользователя
     **/
    private boolean userIsExists(String username) {
        return userRepository.findUserByUsername(username);
    }
}
