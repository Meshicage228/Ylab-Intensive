package firstTast.com.service;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;

import java.util.ArrayList;

public interface UserActionService {
    Workout addNewWorkout(ConsoleUser consoleUser);
    String showAllWorkoutsDateSorted(ConsoleUser consoleUser);

    String getWorkoutStatistics(ConsoleUser consoleUser);

}
