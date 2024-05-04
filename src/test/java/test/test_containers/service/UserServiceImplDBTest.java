package test.test_containers.service;

import first_task.com.dto.WorkoutDto;
import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueWorkoutException;
import first_task.com.repository.WorkoutRepository;
import first_task.com.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Класс, тестирующий {@link UserServiceImpl} по работе с бд
 *  **/
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест user service")
@Testcontainers
class UserServiceImplDBTest extends BaseTestDB {
    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();


    @Test
    @DisplayName("Добавление пользователем тренировки")
    void addNewWorkout() throws NotUniqueWorkoutException {
        WorkoutDto newWorkout = WorkoutDto.builder()
                .caloriesBurned(1000d)
                .workoutType(new WorkoutTypeDto(1, "type"))
                .user_id(1)
                .dateOfAdding("2022-12-12")
                .additionalInfo("good")
                .minuteDuration(123d)
                .timeOfWorkout("2022-12-12")
                .build();

        when(workoutRepository.getWorkoutsByUserId(Mockito.anyInt())).thenReturn(new ArrayList<>());
        when(workoutRepository.saveWorkout(Mockito.anyInt() ,Mockito.any())).thenReturn(newWorkout);

        assertEquals(userService.addNewWorkout(Mockito.anyInt(), newWorkout), newWorkout);
    }
}