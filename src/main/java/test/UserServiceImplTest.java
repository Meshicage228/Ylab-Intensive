package test;

import firstTask.com.exceptions.NotUniqueWorkoutException;
import firstTask.com.model.ConsoleUser;
import firstTask.com.model.Workout;
import firstTask.com.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


/**
 * Класс, тестирующий {@link UserServiceImpl} класс
 *  **/
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест действий пользователя над тренировками")
class UserServiceImplTest {
    @Mock
    private ConsoleUser consoleUser;

    private final UserServiceImpl userService = new UserServiceImpl();

    private static LinkedList<Workout> mockWorkouts;

    @BeforeAll
    @DisplayName("Добавление данных о тренировках перед тестами")
    public static void addWorkouts(){
        mockWorkouts = new LinkedList<>();

        mockWorkouts.add(Workout.builder()
                .type("youga")
                .caloriesBurned(1200d)
                .timeOfWorkout(LocalDate.parse("2022-12-12"))
                .build());
        mockWorkouts.add(Workout.builder()
                .type("youga1")
                .caloriesBurned(1000d)
                .timeOfWorkout(LocalDate.parse("2024-12-12"))
                .build());
    }
    @Test
    @DisplayName("Вывод тренировок, отсортированных по введенной дате")
    void showAllWorkoutsDateSorted() {
        when(consoleUser.getWorkouts()).thenReturn(mockWorkouts);

        LinkedList<Workout> workouts = userService.showAllWorkoutsDateSorted(consoleUser);

        assertEquals(workouts.get(0).getTimeOfWorkout(), LocalDate.parse("2024-12-12"));
    }

    @Test
    @DisplayName("Получение статистики исходя из тренировок")
    void getWorkoutStatistics() {
        when(consoleUser.getWorkouts()).thenReturn(mockWorkouts);

        String workouts = userService.getWorkoutStatistics(consoleUser);

        assertTrue(workouts.contains("Максимально сожжено : 1200.0"));
    }

    @Test
    @DisplayName("Проверка добавления тренировки")
    void addNewWorkout() throws NotUniqueWorkoutException {
        Workout newWorkout = Workout.builder()
                .caloriesBurned(1000d)
                .type("youga123")
                .additionalInfo("норм упражнение")
                .minuteDuration(123d)
                .timeOfWorkout(LocalDate.parse("2024-12-10"))
                .build();

        int size = mockWorkouts.size();
        when(consoleUser.getWorkouts()).thenReturn(mockWorkouts);

        userService.addNewWorkout(consoleUser, newWorkout);

        assertNotEquals(size, mockWorkouts.size());
    }
}