package first_task.com.service.impl;

import first_task.com.annotations.Loggable;
import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.mappers.UserMapper;
import first_task.com.model.AppUser;
import first_task.com.repository.UserRepository;
import first_task.com.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * класс для регистрации и входа в аккаунт
 * @see AuthenticationService
 */

@RequiredArgsConstructor
@Service
@Loggable
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    /**
     * Метод регистрации пользователя.
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     * @exception NotUniqueUserNameException исключение неуникального имени
     * @see AppUser пользователь приложения
     **/
    public UserDto registrationProcess(String username, String password) throws NotUniqueUserNameException {
        if (!userIsExists(username)) {
            String role = "USER";
            if(username.toLowerCase().equals("admin")){
                role = "ADMIN";
            }
            AppUser newUser = AppUser.builder()
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
     * @param loginUserDto параметры для входа в приложение
     * @see AppUser пользователь приложения
     * @return Optional<ConsoleUser> : optional обёртка полученного зарегестрированного пользователя
     **/
    public Optional<UserDto> logIn(LoginUserDto loginUserDto) {
        return Optional.ofNullable(
                userMapper.toDto(
                        userRepository.findByUsernameAndPassword(loginUserDto.getUsername(), loginUserDto.getPassword())));
    }

    /**
     * Метод проверки на существование пользователя.
     * @param username уникальное имя пользователя
     * @see AppUser пользователь приложения
     * @return boolean : наличие / отсутствие пользователя
     **/
    public boolean userIsExists(String username) {
        return userRepository.findUserByUsername(username);
    }
}
