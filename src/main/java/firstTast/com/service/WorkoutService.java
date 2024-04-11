package firstTast.com.service;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;

import java.time.LocalDate;

public interface WorkoutService {
    Workout changeDate(ConsoleUser consoleUser, String newDate);
    Workout changeType(ConsoleUser consoleUser, String newType);

    Workout changeAdditionalInfo(ConsoleUser consoleUser, String newAddInfo);

    Workout changeCalories(ConsoleUser consoleUser, Double changeCalories);

    Workout changeMinuteDuration (ConsoleUser consoleUser, Double changeDuration);

    void deleteWorkout(ConsoleUser consoleUser);

}