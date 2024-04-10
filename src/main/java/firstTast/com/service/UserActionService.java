package firstTast.com.service;

import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;

import java.util.ArrayList;

public interface UserActionService {
    Workout addNewWorkout(ConsoleUser consoleUser);
    ArrayList<Workout> showAllWorkoutsDateSorted(ConsoleUser consoleUser);
    Workout changeWorkout(ConsoleUser consoleUser,Integer id);

    String getWorkoutStatistics(ConsoleUser consoleUser);

}
