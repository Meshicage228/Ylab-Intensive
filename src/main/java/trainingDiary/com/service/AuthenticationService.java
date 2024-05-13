package trainingDiary.com.service;

import trainingDiary.com.dto.LoginUserDto;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.exceptions.NotUniqueUserNameException;
import trainingDiary.com.model.AppUser;

import java.util.Optional;

public interface AuthenticationService {
    /**
     * Метод регистрации пользователя.
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     * @exception NotUniqueUserNameException исключение неуникального имени
     * @see AppUser пользователь приложения
     **/
    UserDto registrationProcess(String username, String password) throws NotUniqueUserNameException;
    /**
     * Метод входа в аккаунт.
     * @param userDto параметры для входа в приложение
     * @see AppUser пользователь приложения
     * @return Optional<ConsoleUser> : optional обёртка полученного зарегестрированного пользователя
     **/
    Optional<UserDto> logIn(LoginUserDto userDto);

    /**
     * Метод проверки на существование пользователя.
     * @param username уникальное имя пользователя
     * @see AppUser пользователь приложения
     * @return boolean : наличие / отсутствие пользователя
     **/
    boolean userIsExists(String username);
}
