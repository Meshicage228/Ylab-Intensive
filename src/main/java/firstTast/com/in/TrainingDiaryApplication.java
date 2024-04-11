package firstTast.com.in;

import firstTast.com.exceptions.NotUniqueUserNameException;
import firstTast.com.exceptions.NotUniqueWorkoutTypeException;
import firstTast.com.model.ConsoleUser;
import firstTast.com.service.AuthenticationService;
import firstTast.com.service.UserActionService;
import firstTast.com.service.impl.WorkoutUpdateServiceImpl;
import firstTast.com.util.MenuOptions;
import firstTast.com.util.UtilScanner;
import lombok.*;

import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

@AllArgsConstructor
@NoArgsConstructor
public class TrainingDiaryApplication {
    final static Logger logger = Logger.getLogger(String.valueOf(TrainingDiaryApplication.class));

    private AuthenticationService authenticationService;
    private UserActionService userService;
    private WorkoutUpdateServiceImpl workoutService;
    private ConsoleUser consoleUser;

    public void startApplication() {
        while (true) {
            if(!enterApplication()){
                logger.info("Пользователь завершает работу...");
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
                        logger.info(userName + " вход в аккаунт : SUCCESS");
                        return true;
                    }
                    logger.info(userName + " вошел в аккаунт : FAIL");
                }
                case "2", "Регистрация" -> {
                    System.out.print("Введите имя пользователя: ");
                    String userName = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    try {
                        authenticationService.registrationProcess(userName, password);
                        System.out.println("Успешная регистрация! Теперь войдите в аккаунт!");
                        logger.info(userName + " регистрация аккауна : SUCCESS");
                    } catch (NotUniqueUserNameException e) {
                        System.out.println(e.getMessage());
                        logger.info(userName + " регистрация аккауна : FAIL");
                    }
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
                    logger.info(consoleUser.getUsername() + " добавление тренировки: SUCCESS");
                } catch (DateTimeParseException e) {
                    System.out.println("Неверна введена дата");
                    logger.info(consoleUser.getUsername() + " добавление тренировки: FAIL");
                } catch (NotUniqueWorkoutTypeException e){
                    System.out.println(e.getMessage());
                    logger.info(consoleUser.getUsername() + " добавление тренировки: FAIL");
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
                logger.info(consoleUser.getUsername() + " получение статистики: SUCCESS");
            }
            case "5" -> {
                if (consoleUser.getRole().equals("ADMIN")) {
                    System.out.println(userService.getAllWorkouts());
                } else {
                    System.out.println("У вас недостаточно прав");
                }
            }
            case "7" -> {
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
            logger.info(consoleUser.getUsername() + " изменение тренировки: FAIL");
            return;
        }

        System.out.println(MenuOptions.workoutEditorMenu);
        switch (scanner.nextLine()) {
            case "1" -> {
                System.out.print("Введите новое название тренировки : ");
                String workoutType = scanner.nextLine();
                workoutService.changeType(consoleUser, workoutType);
                logger.info(consoleUser.getUsername() + " новое название тренировки: SUCCESS");
            }
            case "2" -> {
                System.out.print("Введите новую дату тренировки : ");
                String newDate = scanner.nextLine();
                workoutService.changeDate(consoleUser, newDate);
                logger.info(consoleUser.getUsername() + " новая дату тренировки: SUCCESS");
            }
            case "3" -> {
                System.out.print("Введите новую продолжительность тренировки : ");
                Double newTime = scanner.nextDouble();
                workoutService.changeMinuteDuration(consoleUser, newTime);
                logger.info(consoleUser.getUsername() + " новая продолжительность тренировки: SUCCESS");
            }
            case "4" -> {
                System.out.print("Введите новые калории : ");
                Double newCalories = scanner.nextDouble();
                workoutService.changeCalories(consoleUser, newCalories);
                logger.info(consoleUser.getUsername() + " новые калории: SUCCESS");
            }
            case "5" -> {
                System.out.print("Введите новое описание : ");
                String newAdditional = scanner.nextLine();
                workoutService.changeAdditionalInfo(consoleUser, newAdditional);
                logger.info(consoleUser.getUsername() + " новое описание: SUCCESS");
            }
            case "6" -> {
                workoutService.deleteWorkout(consoleUser);
                System.out.println("Запись успешно удалена!");
                logger.info(consoleUser.getUsername() + " удаление тренировки: SUCCESS");
            }
            case "7" -> {

            }
            default -> {
                System.out.println("Неверная опция");
            }
        }
    }
}
