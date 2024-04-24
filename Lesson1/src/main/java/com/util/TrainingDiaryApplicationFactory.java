package com.util;

import com.model.ConsoleUser;
import com.service.AuthenticationService;
import com.in.TrainingDiaryApplication;
import com.service.impl.UserServiceImpl;
import com.service.impl.WorkoutUpdateServiceImpl;

public class TrainingDiaryApplicationFactory {
    public static TrainingDiaryApplication createTrainingDiaryApplication() {
        AuditLog auditLog = new AuditLog();
        AuthenticationService authenticationService = new AuthenticationService();
        UserServiceImpl userService = new UserServiceImpl();
        WorkoutUpdateServiceImpl workoutUpdateService = new WorkoutUpdateServiceImpl();
        ConsoleUser userInterface = new ConsoleUser();

        return new TrainingDiaryApplication(auditLog, authenticationService, userService, workoutUpdateService, userInterface);
    }
}
