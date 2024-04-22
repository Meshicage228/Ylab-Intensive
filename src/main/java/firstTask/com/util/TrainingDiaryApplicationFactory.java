package firstTask.com.util;

import firstTask.com.in.TrainingDiaryApplication;
import firstTask.com.model.ConsoleUser;
import firstTask.com.repository.AuditRepository;
import firstTask.com.repository.UserRepository;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.repository.WorkoutTypeRepository;
import firstTask.com.service.AuthenticationService;
import firstTask.com.service.impl.UserServiceImpl;
import firstTask.com.service.impl.WorkoutServiceImpl;

public class TrainingDiaryApplicationFactory {
    public static TrainingDiaryApplication createTrainingDiaryApplication() {
        AuditLog auditLog = new AuditLog(new AuditRepository());

        UserRepository userRepository = new UserRepository(new WorkoutRepository());
        AuthenticationService authenticationService = new AuthenticationService(userRepository);
        UserServiceImpl userService = new UserServiceImpl(new WorkoutRepository(), userRepository);
        WorkoutServiceImpl workoutUpdateService = new WorkoutServiceImpl(new WorkoutRepository(), new WorkoutTypeRepository());
        ConsoleUser userInterface = new ConsoleUser();

        return new TrainingDiaryApplication(auditLog, authenticationService, userService, workoutUpdateService, userInterface);
    }
}
