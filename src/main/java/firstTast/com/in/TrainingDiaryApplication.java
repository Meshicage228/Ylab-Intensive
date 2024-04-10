package firstTast.com.in;

import firstTast.com.exceptions.NotUniqueUserNameException;
import firstTast.com.model.ConsoleUser;
import firstTast.com.service.AuthenticationService;
import firstTast.com.service.UserActionService;
import firstTast.com.util.MenuOptions;
import firstTast.com.util.UtilScanner;
import lombok.*;

import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TrainingDiaryApplication {

    private AuthenticationService authenticationService;
    private UserActionService userService;
    private ConsoleUser consoleUser;


    public void startApplication() {
        while (true) {
            enterApplication();
            userAction();
        }
    }

    private void enterApplication() {
        boolean flag = true;
        do {
            System.out.print(MenuOptions.enterMenu);
            Scanner scanner = UtilScanner.getScanner();
            String input = scanner.nextLine();

            switch (input) {
                case "1", "Вход" -> {
                    System.out.print("Введите имя пользователя: ");
                    String userName = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    Optional<ConsoleUser> user = authenticationService.logIn(userName, password);
                    if (user.isPresent()) {
                        consoleUser = user.get();
                        flag = false;
                    }
                }
                case "2", "Регистрация" -> {
                    System.out.print("Введите имя пользователя: ");
                    String userName = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    try {
                        authenticationService.registrationProcess(userName, password);
                    } catch (NotUniqueUserNameException e) {
                        System.out.println(e.getMessage());
                    }
                }
                default -> {
                    System.out.println("Неподдерживаемый тип");
                }
            }
        } while (flag);
    }

    private void userAction() {
        String menuChoice = "";
        do {
            System.out.println(MenuOptions.usersMenu);
            menuChoice = UtilScanner.getScanner().nextLine();
            switch (menuChoice) {
                case "1" -> {
                    userService.addNewWorkout(consoleUser);
                }
                case "2" -> {
                    userService.changeWorkout(consoleUser, 2);
                }
                case "3" -> {
                    System.out.println(userService.showAllWorkoutsDateSorted(consoleUser));
                }
                case "4" -> {
                    System.out.println(userService.getWorkoutStatistics(consoleUser));
                }
                case "5" -> {

                }
                default -> {
                    System.out.println("Неподдерживаемая опция");
                }
            }
        } while (!menuChoice.equals("5") && !menuChoice.equals("Выйти"));
    }
}
