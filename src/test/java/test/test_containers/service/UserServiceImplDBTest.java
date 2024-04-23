package test.test_containers.service;

import firstTask.com.exceptions.NotUniqueWorkoutException;
import firstTask.com.model.ConsoleUser;
import firstTask.com.model.Workout;
import firstTask.com.model.WorkoutType;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Класс, тестирующий {@link UserServiceImpl} по работе с бд
 *  **/
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест user service")
@Testcontainers
class UserServiceImplDBTest extends BaseTestDB {
    @Mock
    private ConsoleUser consoleUser;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();


    @Test
    @DisplayName("Добавление пользователем тренировки")
    void addNewWorkout() throws NotUniqueWorkoutException {
        Workout newWorkout = Workout.builder()
                .caloriesBurned(1000d)
                .workoutType(new WorkoutType(1, "type"))
                .user_id(1)
                .dateOfAdding(LocalDate.now())
                .additionalInfo("good")
                .minuteDuration(123d)
                .timeOfWorkout(LocalDate.parse("2024-12-10"))
                .build();

        when(consoleUser.getWorkouts()).thenReturn(new LinkedList<>());
        when(workoutRepository.saveWorkout(Mockito.any())).thenReturn(newWorkout);

        assertEquals(userService.addNewWorkout(consoleUser, newWorkout), newWorkout);
    }
}