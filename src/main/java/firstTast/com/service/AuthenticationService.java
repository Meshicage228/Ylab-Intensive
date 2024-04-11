package firstTast.com.service;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.UserStorage;
import firstTast.com.exceptions.NotUniqueUserNameException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public class AuthenticationService {

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

    public Optional<ConsoleUser> logIn(String username, String password) {
        return UserStorage.getAllUsers().stream()
                    .filter(consoleUser -> consoleUser.getUsername().equals(username) && consoleUser.getPassword().equals(password))
                    .findFirst();
    }

    public boolean userIsExists(String username) {
        return UserStorage.getAllUsers().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
