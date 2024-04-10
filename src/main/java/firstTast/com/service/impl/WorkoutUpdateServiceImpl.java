package firstTast.com.service.impl;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;
import firstTast.com.service.WorkoutService;
import firstTast.com.util.UtilScanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkoutUpdateServiceImpl implements WorkoutService {
    private Integer indexToChange;
    private Workout currentWorkout;

    @Override
    public Workout changeDate(ConsoleUser consoleUser, String newDate) {
        LocalDate localDate = LocalDate.parse(newDate);
        currentWorkout.setTimeOfWorkout(localDate);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
    }

    @Override
    public Workout changeType(ConsoleUser consoleUser, String newType) {
        currentWorkout.setType(newType);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
    }

    @Override
    public Workout changeAdditionalInfo(ConsoleUser consoleUser, String newAddInfo) {
        currentWorkout.setAdditionalInfo(newAddInfo);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
    }

    @Override
    public Workout changeCalories(ConsoleUser consoleUser, Double changeCalories) {
        currentWorkout.setCaloriesBurned(changeCalories);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
    }

    @Override
    public Workout changeMinuteDuration(ConsoleUser consoleUser, Double changeDuration) {
        currentWorkout.setMinuteDuration(changeDuration);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
    }

    @Override
    public void deleteWorkout(ConsoleUser consoleUser) {
        consoleUser.getWorkouts().remove(currentWorkout);
    }

    public Integer getWorkoutByIndex(ConsoleUser consoleUser){
        if(consoleUser.getWorkouts().isEmpty()){
            return -1;
        }
        int index = -1;
        ArrayList<Workout> workouts = consoleUser.getWorkouts();
        System.out.println("Выберите тренировку для изменения : ");
        do{
            Scanner scanner = UtilScanner.getScanner();

            for (int i = 0; i < workouts.size(); i++) {
                System.out.println(i + 1  + " " + workouts.get(i).getType());
            }

            index = scanner.nextInt();
        }while (index < 0 || index > workouts.size());

        indexToChange = index - 1;

        currentWorkout = workouts.get(indexToChange);

        return indexToChange;
    }
}
