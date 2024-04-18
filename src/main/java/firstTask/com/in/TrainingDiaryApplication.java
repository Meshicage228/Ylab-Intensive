package firstTask.com.in;

import firstTask.com.model.ConsoleUser;
import firstTask.com.util.AuditLog;
import firstTask.com.util.MenuOptions;
import firstTask.com.util.UtilScanner;
import firstTask.com.exceptions.NotUniqueUserNameException;
import firstTask.com.exceptions.NotUniqueWorkoutException;
import firstTask.com.model.Workout;
import firstTask.com.service.AuthenticationService;
import firstTask.com.service.UserActionService;
import firstTask.com.service.impl.WorkoutUpdateServiceImpl;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;


/**
 *
 * Класс, ответственный за контакт и обратную связь с пользователем (консолью):
 *
 * @see AuthenticationService - работа с аунтификацией
 * @see UserActionService - обработка действий пользователя
 * @see WorkoutUpdateServiceImpl - обработка действий, связанных непосредственно с задачами
 * @see ConsoleUser - пользователь приложения
 *  **/

@AllArgsConstructor
@NoArgsConstructor
public class TrainingDiaryApplication {
    private AuditLog auditLog;
    private AuthenticationService authenticationService;
    private UserActionService userService;
    private WorkoutUpdateServiceImpl workoutService;
    private ConsoleUser consoleUser;

    /**
     * Метод, запускающий приложение
     *  **/
    public void startApplication() {
        while (true) {
            if(!enterApplication()){
                auditLog.addLogEntry("Пользователь завершает работу...");
                System.out.println("Завершение программы...");
                return;
            };
            userAction();
        }
    }

    private boolean enterApplication() {
        do {
            Scanner scanner = UtilScanner.getInstance();
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
                        auditLog.addLogEntry(userName + " вход в аккаунт : SUCCESS");
                        return true;
                    }
                    System.out.println("Неверный логин или пароль");
                    auditLog.addLogEntry(userName + " вошел в аккаунт : FAIL");
                }
                case "2", "Регистрация" -> {
                    System.out.print("Введите имя пользователя: ");
                    String userName = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    try {
                        authenticationService.registrationProcess(userName, password);
                        System.out.println("Успешная регистрация! Теперь войдите в аккаунт!");
                        auditLog.addLogEntry(userName + " регистрация аккауна : SUCCESS");
                    } catch (NotUniqueUserNameException e) {
                        System.out.println(e.getMessage());
                        auditLog.addLogEntry(userName + " регистрация аккауна : FAIL");
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
                    Workout workout = createWorkout();
                    System.out.println(userService.addNewWorkout(consoleUser, workout).getType() + " - тренировка добавлена");
                    auditLog.addLogEntry(consoleUser.getUsername() + " добавление тренировки: SUCCESS");
                } catch (DateTimeParseException e) {
                    System.out.println("Неверна введена дата");
                    auditLog.addLogEntry(consoleUser.getUsername() + " добавление тренировки: FAIL");
                } catch (NotUniqueWorkoutException e){
                    System.out.println(e.getMessage());
                    auditLog.addLogEntry(consoleUser.getUsername() + " добавление тренировки: FAIL");
                }
            }
            case "2", "Редактировать тренировку" -> {
                changeWorkout();
            }
            case "3", "Просмотреть тренировки" -> {
                System.out.println(userService.showAllWorkoutsDateSorted(consoleUser).toString());
            }
            case "4", "Статистика тренировок" -> {
                String workoutStatistics = userService.getWorkoutStatistics(consoleUser);
                if(workoutStatistics.equals("Нет активных тренировок")){
                    auditLog.addLogEntry(consoleUser.getUsername() + " получение статистики: FAIL");
                    return;
                } else{
                    auditLog.addLogEntry(consoleUser.getUsername() + " получение статистики: SUCCESS");
                    System.out.println(workoutStatistics);
                }
            }
            case "5" -> {
                if (consoleUser.getRole().equals("ADMIN")) {
                    System.out.println(userService.getAllWorkouts());
                } else {
                    System.out.println("У вас недостаточно прав");
                }
            }
            case "6" -> {

            }
            default -> {
                System.out.println("Неподдерживаемая опция");
            }
        }
    }
    private Workout createWorkout(){
        System.out.println("Введите дату вашей тренировки в виде YYYY-MM-DD : ");
        Scanner scanner = UtilScanner.getInstance();
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date);

        System.out.println("Введите тип тренировки : ");
        String type = scanner.nextLine();

        System.out.println("Введите продолжительность тренировки : ");
        double time = scanner.nextDouble();

        System.out.println("Введите кол-во сожженных калорий : ");
        double calories = scanner.nextDouble();

        System.out.println("Опишите свою тренировку : ");
        scanner.nextLine();
        String additional = scanner.nextLine();

        return Workout.builder()
                .timeOfWorkout(localDate)
                .type(type)
                .user_id(consoleUser.getId())
                .dateOfAdding(LocalDate.now())
                .minuteDuration(time)
                .caloriesBurned(calories)
                .additionalInfo(additional)
                .build();
    }
    private void userAction() {
        String menuChoice = "";
        do {
            System.out.println(MenuOptions.userMenu);
            menuChoice = UtilScanner.getInstance().nextLine();
            handleMenuChoice(menuChoice, consoleUser);
        } while (!menuChoice.equals("6") && !menuChoice.equals("Назад"));
    }
    private void changeWorkout() {
        Scanner scanner = UtilScanner.getInstance();
        Integer workoutByIndex = workoutService.getWorkoutByIndex(consoleUser);

        if (workoutByIndex < 0) {
            System.out.println("Нет активных тренировок");
            auditLog.addLogEntry(consoleUser.getUsername() + " изменение тренировки: FAIL");
            return;
        }

        System.out.println(MenuOptions.workoutEditorMenu);
        scanner.nextLine();
        switch (scanner.nextLine()) {
            case "1" -> {
                System.out.print("Введите новое название тренировки : ");
                String workoutType = scanner.nextLine();
                workoutService.changeType(consoleUser, workoutType);
                auditLog.addLogEntry(consoleUser.getUsername() + " новое название тренировки: SUCCESS");
            }
            case "2" -> {
                System.out.print("Введите новую дату тренировки : ");
                String newDate = scanner.nextLine();
                workoutService.changeDate(consoleUser, newDate);
            }
            case "3" -> {
                System.out.print("Введите новую продолжительность тренировки : ");
                Double newTime = scanner.nextDouble();
                try {
                    workoutService.changeMinuteDuration(consoleUser, newTime);
                    auditLog.addLogEntry(consoleUser.getUsername() + " новая продолжительность тренировки: SUCCESS");
                } catch (DateTimeParseException e) {
                    System.out.println("Некорректный ввод даты");
                    auditLog.addLogEntry(consoleUser.getUsername() + " новая продолжительность тренировки: FAIL");
                }
            }
            case "4" -> {
                System.out.print("Введите новые калории : ");
                Double newCalories = scanner.nextDouble();
                workoutService.changeCalories(consoleUser, newCalories);
                auditLog.addLogEntry(consoleUser.getUsername() + " новые калории: SUCCESS");
            }
            case "5" -> {
                System.out.print("Введите новое описание : ");
                String newAdditional = scanner.nextLine();
                workoutService.changeAdditionalInfo(consoleUser, newAdditional);
                auditLog.addLogEntry(consoleUser.getUsername() + " новое описание: SUCCESS");
            }
            case "6" -> {
                workoutService.deleteWorkout(consoleUser);
                System.out.println("Запись успешно удалена!");
                auditLog.addLogEntry(consoleUser.getUsername() + " удаление тренировки: SUCCESS");
            }
            case "7" -> {

            }
            default -> {
                System.out.println("Неверная опция");
            }
        }
    }
}
