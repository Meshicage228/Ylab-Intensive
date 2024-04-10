package firstTast.com.service.impl;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;
import firstTast.com.service.UserActionService;

import java.util.*;

public class UserServiceImpl implements UserActionService {
    @Override
    public Workout addNewWorkout(ConsoleUser consoleUser) {
        return null;
    }

    @Override
    public ArrayList<Workout> showAllWorkoutsDateSorted(ConsoleUser consoleUser) {
        ArrayList<Workout> workouts = consoleUser.getWorkouts();
        workouts.sort(Comparator.comparing(Workout::getTimeOfWorkout));
        return workouts;
    }

    @Override
    public Workout changeWorkout(ConsoleUser consoleUser ,Integer id) {
        Workout workout = consoleUser.getWorkouts().get(id);

        return null;
    }

    @Override
    public String getWorkoutStatistics(ConsoleUser consoleUser) {
        IntSummaryStatistics calorieStatistics = consoleUser.getWorkouts().stream()
                .mapToInt(Workout::getCaloriesBurned)
                .summaryStatistics();

        StringJoiner stringJoiner = new StringJoiner(" ");

        stringJoiner
                .add("Всего каллорий сожжено : ")
                .add(String.valueOf(calorieStatistics.getSum())).add("\n")
                .add("Максимально сожжено : ")
                .add(String.valueOf(calorieStatistics.getMax())).add("\n")
                .add("В среднем вы сжигаете : ")
                .add(String.valueOf(calorieStatistics.getAverage()));

        return stringJoiner.toString();
    }
}
