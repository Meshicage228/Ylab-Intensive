package firstTast.com.service;

import firstTast.com.exceptions.NotUniqueWorkoutTypeException;
import firstTast.com.model.ConsoleUser;
import firstTast.com.model.Workout;

public interface UserActionService {
    Workout addNewWorkout(ConsoleUser consoleUser) throws NotUniqueWorkoutTypeException;
    String showAllWorkoutsDateSorted(ConsoleUser consoleUser);

    String getWorkoutStatistics(ConsoleUser consoleUser);
    String getAllWorkouts();

}
