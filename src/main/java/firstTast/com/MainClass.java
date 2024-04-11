package firstTast.com;

import firstTast.com.in.TrainingDiaryApplication;
import firstTast.com.model.ConsoleUser;
import firstTast.com.service.AuthenticationService;
import firstTast.com.service.impl.UserServiceImpl;
import firstTast.com.service.impl.WorkoutUpdateServiceImpl;

public class MainClass {
    public static void main(String[] args) {
        TrainingDiaryApplication trainingDiaryApplication = new TrainingDiaryApplication
                (new AuthenticationService(), new UserServiceImpl(), new WorkoutUpdateServiceImpl(), new ConsoleUser());

        trainingDiaryApplication.startApplication();
    }
}
