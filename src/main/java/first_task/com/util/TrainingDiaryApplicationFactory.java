package first_task.com.util;

import first_task.com.in.TrainingDiaryApplication;
import first_task.com.model.ConsoleUser;
import first_task.com.repository.AuditRepository;
import first_task.com.repository.UserRepository;
import first_task.com.repository.WorkoutRepository;
import first_task.com.repository.WorkoutTypeRepository;
import first_task.com.service.AuthenticationService;
import first_task.com.service.impl.UserServiceImpl;
import first_task.com.service.impl.WorkoutServiceImpl;

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
