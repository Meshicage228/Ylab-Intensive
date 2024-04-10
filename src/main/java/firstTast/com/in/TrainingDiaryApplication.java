package firstTast.com.in;

import firstTast.com.exceptions.NotUniqueUserNameException;
import firstTast.com.exceptions.NotUniqueWorkoutTypeException;
import firstTast.com.model.ConsoleUser;
import firstTast.com.service.AuthenticationService;
import firstTast.com.service.UserActionService;
import firstTast.com.service.impl.WorkoutUpdateServiceImpl;
import firstTast.com.util.AuditLog;
import firstTast.com.util.MenuOptions;
import firstTast.com.util.UtilScanner;
import lombok.*;

import java.time.format.DateTimeParseException;
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
    private WorkoutUpdateServiceImpl workoutService;
    private ConsoleUser consoleUser;
    private AuditLog auditLog;

    public void startApplication() {
        while (true) {
            if(!enterApplication()){
                System.out.println("Завершение программы...");
                return;
            };
            userAction();
        }
    }

    private boolean enterApplication() {
        do {
            Scanner scanner = UtilScanner.getScanner();
            System.out.println(MenuOptions.enterMenu);
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
                        System.out.println("Привет, " + userName);
                        return true;
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
                    System.out.println("Успешная регистрация! Теперь войдите в аккаунт!");
                }
                case "3" -> {
                    return false;
                }
            }
        } while (true);
    }

    private void handleMenuChoice(String menuChoice, ConsoleUser consoleUser) {
        switch (menuChoice) {
            case "1", "Добавить тренировку" -> {
                try {
                    System.out.println(userService.addNewWorkout(consoleUser).getType() + " - тренировка добавлена");
                } catch (DateTimeParseException e) {
                    System.out.println("Неверна введена дата");
                } catch (NotUniqueWorkoutTypeException e){
                    System.out.println(e.getMessage());
                }
            }
            case "2", "Редактировать тренировку" -> {
                changeWorkout();
            }
            case "3", "Просмотреть тренировки" -> {
                System.out.println(userService.showAllWorkoutsDateSorted(consoleUser));
            }
            case "4", "Статистика тренировок" -> {
                System.out.println(userService.getWorkoutStatistics(consoleUser));
            }
            case "5" -> {
                if (consoleUser.getRole().equals("ADMIN")) {
                    System.out.println(userService.getAllWorkouts());
                } else {
                    System.out.println("У вас недостаточно прав");
                }
            }
            case "6" -> {
                if (consoleUser.getRole().equals("ADMIN")) {
                    System.out.println(userService.getAllLogs(auditLog));
                } else {
                    System.out.println("У вас недостаточно прав");
                }
            }
            case "7", "Назад" -> {
            }
            default -> {
                System.out.println("Неподдерживаемая опция");
            }
        }
    }
    private void userAction() {
        String menuChoice = "";
        do {
            System.out.println(MenuOptions.userMenu);
            menuChoice = UtilScanner.getScanner().nextLine();
            handleMenuChoice(menuChoice, consoleUser);
        } while (!menuChoice.equals("7") && !menuChoice.equals("Назад"));
    }

    private void changeWorkout() {
        Scanner scanner = UtilScanner.getScanner();
        Integer workoutByIndex = workoutService.getWorkoutByIndex(consoleUser);

        if (workoutByIndex < 0) {
            System.out.println("Нет активных тренировок");
            return;
        }

        System.out.println(MenuOptions.workoutEditorMenu);
        switch (scanner.nextLine()) {
            case "1" -> {
                System.out.print("Введите новое название тренировки : ");
                String workoutType = scanner.nextLine();
                workoutService.changeType(consoleUser, workoutType);
            }
            case "2" -> {
                System.out.print("Введите новую дату тренировки : ");
                String newDate = scanner.nextLine();
                workoutService.changeDate(consoleUser, newDate);
            }
            case "3" -> {
                System.out.print("Введите новую продолжительность тренировки : ");
                Double newTime = scanner.nextDouble();
                workoutService.changeMinuteDuration(consoleUser, newTime);
            }
            case "4" -> {
                System.out.print("Введите новые калории : ");
                Double newCalories = scanner.nextDouble();
                workoutService.changeCalories(consoleUser, newCalories);
            }
            case "5" -> {
                System.out.print("Введите новое описание : ");
                String newAdditional = scanner.nextLine();
                workoutService.changeAdditionalInfo(consoleUser, newAdditional);
            }
            case "6" -> {
                workoutService.deleteWorkout(consoleUser);
                System.out.println("Запись успешно удалена!");
            }
            case "7" -> {

            }
            default -> {
                System.out.println("Неверная опция");
            }
        }
    }
}
