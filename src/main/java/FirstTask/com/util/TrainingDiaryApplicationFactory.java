package FirstTask.com.util;

import FirstTask.com.in.TrainingDiaryApplication;
import FirstTask.com.model.ConsoleUser;
import FirstTask.com.repository.AuditRepository;
import FirstTask.com.repository.UserRepository;
import FirstTask.com.repository.WorkoutRepository;
import FirstTask.com.repository.WorkoutTypeRepository;
import FirstTask.com.service.AuthenticationService;
import FirstTask.com.service.impl.UserServiceImpl;
import FirstTask.com.service.impl.WorkoutServiceImpl;

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
