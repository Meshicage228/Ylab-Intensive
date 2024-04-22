package test.test_containers.service;

import firstTask.com.exceptions.NotUniqueWorkoutException;
import firstTask.com.model.ConsoleUser;
import firstTask.com.model.Workout;
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
 *  ласс, тестирующий {@link UserServiceImpl} класс с базой данных
 *  **/
@ExtendWith(MockitoExtension.class)
@DisplayName("“ест действий пользовател€ над тренировками")
@Testcontainers
class UserServiceImplDBTest extends BaseTestDB {
    @Mock
    private ConsoleUser consoleUser;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();


    @Test
    @DisplayName("ѕроверка добавлени€ тренировки в базу данных")
    void addNewWorkout() throws NotUniqueWorkoutException {
        Workout newWorkout = Workout.builder()
                .caloriesBurned(1000d)
                .type("youga123")
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