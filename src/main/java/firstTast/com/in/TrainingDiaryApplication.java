package firstTast.com.in;

import firstTast.com.exceptions.NotUniqueUserNameException;
import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;
import firstTast.com.service.AuthenticationService;
import firstTast.com.service.UserActionService;
import firstTast.com.service.WorkoutService;
import firstTast.com.util.MenuOptions;
import firstTast.com.util.UtilScanner;
import lombok.*;

import java.util.ArrayList;
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
    private WorkoutService workoutService;
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
                case "1", "Добавить тренировку" -> {
                    System.out.println(userService.addNewWorkout(consoleUser).getType() + " - тренировака добавлена");
                }
                case "2", "Редактировать тренировку" -> {
//                    userService.changeWorkout(consoleUser, getWorkoutByIndex());
                }
                case "3", "Просмотреть тренировки" -> {
                    System.out.println(userService.showAllWorkoutsDateSorted(consoleUser));
                }
                case "4", "Статистика тренировок" -> {
                    System.out.println(userService.getWorkoutStatistics(consoleUser));
                }
                case "5", "Выйти" -> {

                }
                default -> {
                    System.out.println("Неподдерживаемая опция");
                }
            }
        } while (!menuChoice.equals("5") && !menuChoice.equals("Выйти"));
    }
    private void changeWorkout(){
        String option = "";
        Scanner scanner = UtilScanner.getScanner();
        do{
            System.out.println(MenuOptions.workoutEditorMenu);
            option = scanner.nextLine();
            switch (option){
                case "1" -> {
                    System.out.print("Введите новое название тренировки : ");
                    String s = scanner.nextLine();
                    workoutService.changeType(consoleUser, s);
                }
                case "2" -> {

                }
                case "3" -> {

                }
                case "4" -> {

                }
                case "5" -> {
                        workoutService.deleteWorkout(consoleUser);
                }
                case "6" -> {

                }
                default -> {
                    System.out.println("Неверная опция");
                }
            }
        }while ();
    }
}
