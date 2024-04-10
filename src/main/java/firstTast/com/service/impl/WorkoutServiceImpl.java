package firstTast.com.service.impl;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;
import firstTast.com.service.WorkoutService;
import firstTast.com.util.UtilScanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkoutServiceImpl implements WorkoutService {

    @Override
    public Workout changeDate(ConsoleUser consoleUser, String newDate) {
        LocalDate parse = LocalDate.parse(newDate);

        workout.setTimeOfWorkout(parse);
        return workout;
    }

    @Override
    public Workout changeType(ConsoleUser consoleUser, String newType) {
        workout.setType(newType);
        return workout;
    }

    @Override
    public Workout changeAdditionalInfo(ConsoleUser consoleUser, String newAddInfo) {
        workout.setAdditionalInfo(newAddInfo);
        return workout;
    }

    @Override
    public Workout changeCalories(ConsoleUser consoleUser, Integer changeCalories) {
        workout.setCaloriesBurned(changeCalories);
        return workout;
    }

    @Override
    public Workout changeMinuteDuration(ConsoleUser consoleUser, Integer changeDuration) {
        workout.setMinuteDuration(changeDuration);
        return workout;
    }

    @Override
    public void deleteWorkout(ConsoleUser consoleUser, Integer workout) {
        Integer workoutByIndex = getWorkoutByIndex(consoleUser.getWorkouts());
        consoleUser.getWorkouts().remove(workout);
    }

    private Integer getWorkoutByIndex(ArrayList<Workout> workouts){
        int index = -1;
        do{
            Scanner scanner = UtilScanner.getScanner();

            for (int i = 0; i < workouts.size(); i++) {
                System.out.println(i + 1  + " " + workouts.get(i).getType());
            }

            index = scanner.nextInt();
        }while (!(index > 0 && index < workouts.size()));

        return index - 1;
    }

}
