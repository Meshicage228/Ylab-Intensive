package first_task.com.util;

import first_task.com.mappers.UserMapperImpl;
import first_task.com.repository.UserRepository;
import first_task.com.repository.WorkoutRepository;
import first_task.com.repository.WorkoutTypeRepository;
import first_task.com.service.AuthenticationService;
import first_task.com.service.UserActionService;
import first_task.com.service.WorkoutService;
import first_task.com.service.impl.UserServiceImpl;
import first_task.com.service.impl.WorkoutServiceImpl;

public class ServiceFactory {
    private ServiceFactory() {}

    public static AuthenticationService buildAuthentication() {
        return new AuthenticationService(new UserRepository(new WorkoutRepository()), new UserMapperImpl());
    }
    public static WorkoutService buildWorkout() {
        return new WorkoutServiceImpl(new WorkoutRepository(), new WorkoutTypeRepository());
    }
    public static UserActionService buildUserAction() {
        return new UserServiceImpl(new WorkoutRepository(), new UserRepository(new WorkoutRepository()));
    }
}
