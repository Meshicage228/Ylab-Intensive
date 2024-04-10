package firstTast.com.service.impl;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;
import firstTast.com.service.UserActionService;
import firstTast.com.util.UtilScanner;

import java.time.LocalDate;
import java.util.*;

public class UserServiceImpl implements UserActionService {
    @Override
    public Workout addNewWorkout(ConsoleUser consoleUser) {
        Scanner scanner = UtilScanner.getScanner();
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date);
        String type = scanner.nextLine();
        int time = scanner.nextInt();
        int calories = scanner.nextInt();
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
        IntSummaryStatistics calorieStatistics = workouts.stream()
                .mapToInt(Workout::getCaloriesBurned)
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
}
