package firstTast.com;

import firstTast.com.in.TrainingDiaryApplication;
import firstTast.com.model.ConsoleUser;
import firstTast.com.model.UserStorage;
import firstTast.com.service.AuthenticationService;
import firstTast.com.service.impl.UserServiceImpl;
import firstTast.com.util.AuditLog;

public class MainClass {
    public static void main(String[] args) {
        AuditLog auditLog = new AuditLog();
        UserServiceImpl userService = new UserServiceImpl();
        AuthenticationService authenticationService = new AuthenticationService(new UserStorage(), auditLog);

        TrainingDiaryApplication trainingDiaryApplication = new TrainingDiaryApplication(authenticationService, userService, new ConsoleUser());

        trainingDiaryApplication.startApplication();
    }
}
