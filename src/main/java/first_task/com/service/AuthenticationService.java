package first_task.com.service;

import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.model.ConsoleUser;
import java.util.Optional;

public interface AuthenticationService {
    /**
     * Метод регистрации пользователя.
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     * @exception NotUniqueUserNameException исключение неуникального имени
     * @see ConsoleUser пользователь приложения
     **/
    UserDto registrationProcess(String username, String password) throws NotUniqueUserNameException;
    /**
     * Метод входа в аккаунт.
     * @param userDto параметры для входа в приложение
     * @see ConsoleUser пользователь приложения
     * @return Optional<ConsoleUser> : optional обёртка полученного зарегестрированного пользователя
     **/
    Optional<UserDto> logIn(LoginUserDto userDto);

    /**
     * Метод проверки на существование пользователя.
     * @param username уникальное имя пользователя
     * @see ConsoleUser пользователь приложения
     * @return boolean : наличие / отсутствие пользователя
     **/
    boolean userIsExists(String username);
}
