package firstTask.com.util;

import firstTask.com.in.TrainingDiaryApplication;
import firstTask.com.model.ConsoleUser;
import firstTask.com.repository.AuditRepository;
import firstTask.com.repository.UserRepository;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.service.AuthenticationService;
import firstTask.com.service.impl.UserServiceImpl;
import firstTask.com.service.impl.WorkoutUpdateServiceImpl;

public class TrainingDiaryApplicationFactory {
    public static TrainingDiaryApplication createTrainingDiaryApplication() {
        AuditLog auditLog = new AuditLog(new AuditRepository());
        AuthenticationService authenticationService = new AuthenticationService(new UserRepository(new WorkoutRepository()));
        UserServiceImpl userService = new UserServiceImpl(new WorkoutRepository(), new UserRepository(new WorkoutRepository()));
        WorkoutUpdateServiceImpl workoutUpdateService = new WorkoutUpdateServiceImpl(new WorkoutRepository());
        ConsoleUser userInterface = new ConsoleUser();

        return new TrainingDiaryApplication(auditLog, authenticationService, userService, workoutUpdateService, userInterface);
    }
}
