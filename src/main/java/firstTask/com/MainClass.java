package firstTask.com;

import firstTask.com.in.TrainingDiaryApplication;
import firstTask.com.model.ConsoleUser;
import firstTask.com.service.AuthenticationService;
import firstTask.com.service.impl.UserServiceImpl;
import firstTask.com.service.impl.WorkoutUpdateServiceImpl;

public class MainClass {
    public static void main(String[] args) {
        TrainingDiaryApplication trainingDiaryApplication = new TrainingDiaryApplication
                (new AuthenticationService(), new UserServiceImpl(), new WorkoutUpdateServiceImpl(), new ConsoleUser());

        trainingDiaryApplication.startApplication();
    }
}
