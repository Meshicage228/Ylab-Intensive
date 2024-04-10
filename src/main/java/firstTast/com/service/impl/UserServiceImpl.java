package firstTast.com.service.impl;

import firstTast.com.exceptions.NotUniqueWorkoutTypeException;
import firstTast.com.model.ConsoleUser;
import firstTast.com.model.UserStorage;
import firstTast.com.model.Workout;
import firstTast.com.service.UserActionService;
import firstTast.com.util.AuditLog;
import firstTast.com.util.UtilScanner;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserServiceImpl implements UserActionService {
    @Override
    public Workout addNewWorkout(ConsoleUser consoleUser) throws NotUniqueWorkoutTypeException {
        System.out.println("Введите дату вашей тренировки в виде YYYY-MM-DD : ");
        Scanner scanner = UtilScanner.getScanner();
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date);

        System.out.println("Введите тип тренировки : ");
        String type = scanner.nextLine();

        Optional<Workout> first = consoleUser.getWorkouts().stream()
                .filter(workout -> workout.getType().equals(type))
                .findAny();

        if (first.isPresent()){
            throw new NotUniqueWorkoutTypeException("Вы уже добавляли тренировку такого типа!");
        }
        System.out.println("Введите продолжительность тренировки : ");
        double time = scanner.nextDouble();

        System.out.println("Введите кол-во сожженных калорий : ");
        double calories = scanner.nextDouble();

        System.out.println("Опишите свою тренировку : ");
        scanner.nextLine();
        String additional = scanner.nextLine();

        Workout build = Workout.builder()
                .timeOfWorkout(localDate)
                .type(type)
                .minuteDuration(time)
                .caloriesBurned(calories)
                .additionalInfo(additional)
                .build();

        consoleUser.getWorkouts().add(build);

        return build;
    }

    @Override
    public String showAllWorkoutsDateSorted(ConsoleUser consoleUser) {
        ArrayList<Workout> workouts = consoleUser.getWorkouts();
        if (workouts.isEmpty()) {
            return "Нет активных тренировок";
        }
        workouts.sort(Comparator.comparing(Workout::getTimeOfWorkout));
        return workouts.toString();
    }

    @Override
    public String getWorkoutStatistics(ConsoleUser consoleUser) {
        ArrayList<Workout> workouts = consoleUser.getWorkouts();
        if (workouts.isEmpty()) {
            return "Нет активных тренировок";
        }
        DoubleSummaryStatistics calorieStatistics = workouts.stream()
                .mapToDouble(Workout::getCaloriesBurned)
                .summaryStatistics();

        return new StringBuilder()
                .append("Всего каллорий сожжено : ")
                .append(calorieStatistics.getSum()).append("\n")
                .append("Максимально сожжено : ")
                .append(calorieStatistics.getMax()).append("\n")
                .append("В среднем вы сжигаете : ")
                .append(calorieStatistics.getAverage())
                .toString();
    }

    @Override
    public String getAllWorkouts() {
        ArrayList<ConsoleUser> allUsers = UserStorage.getAllUsers();
        if(allUsers.isEmpty()){
            return "Нет активных тренировок в приложении";
        }
        return allUsers.toString();
    }

    @Override
    public String getAllLogs(AuditLog auditLog) {
        return auditLog.displayLogs().toString();
    }
}
