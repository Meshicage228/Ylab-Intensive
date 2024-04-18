package firstTask.com.service.impl;

import firstTask.com.model.ConsoleUser;
import firstTask.com.exceptions.NotUniqueWorkoutException;
import firstTask.com.model.Workout;
import firstTask.com.repository.UserRepository;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.service.UserActionService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.*;


/**
 * Класс обрабатывает действия пользователя
 * @see UserActionService
 */

@AllArgsConstructor
public class UserServiceImpl implements UserActionService {
    private WorkoutRepository workoutRepository;
    private UserRepository userRepository;
    /**
     * Метод добавления новой тренировки:
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param workoutNew {@link Workout собранная новая тренировка}
     * @exception NotUniqueWorkoutException исключение при попытке добавить такой же тип тренировки
     * @return сохраненная тренировка
     **/
    @Override
    public Workout addNewWorkout(ConsoleUser consoleUser, Workout workoutNew) throws NotUniqueWorkoutException {
        Optional<Workout> first = consoleUser.getWorkouts().stream()
                .filter(workout -> workout.getType().equals(workoutNew.getType()) && workout.getDateOfAdding().equals(LocalDate.now()))
                .findAny();

        if (first.isPresent()){
            throw new NotUniqueWorkoutException("Вы уже добавляли эту тренировку сегодня" +  LocalDate.now());
        }

        consoleUser.getWorkouts().add(workoutNew);
        workoutRepository.saveWorkout(workoutNew);

        return workoutNew;
    }

    /**
     * Метод вывода текущих тренировок в порядке убывания даты
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @return список тренировок, отсортированных по дате
     **/
    @Override
    public LinkedList<Workout> showAllWorkoutsDateSorted(ConsoleUser consoleUser) {
        LinkedList<Workout> workouts = consoleUser.getWorkouts();
        if (workouts.isEmpty()) {
            return new LinkedList<>();
        }
        workouts.sort(Comparator.comparing(Workout::getTimeOfWorkout).reversed());
        return workouts;
    }

    /**
     * Метод подсчёта статистики по калориям
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @return строку : список тренировок, отсортированных по дате
     **/
    @Override
    public String getWorkoutStatistics(ConsoleUser consoleUser) {
        LinkedList<Workout> workouts = consoleUser.getWorkouts();
        if (workouts.isEmpty()) {
            return "Нет активных тренировок";
        }
        DoubleSummaryStatistics calorieStatistics = workouts.stream()
                .mapToDouble(Workout::getCaloriesBurned)
                .summaryStatistics();

        return new StringBuilder()
                .append("Всего каллорий сожжено : ")
                .append(calorieStatistics.getSum()).append("\n")
                .append("Максимально сожжено : ")
                .append(calorieStatistics.getMax()).append("\n")
                .append("В среднем вы сжигаете : ")
                .append(calorieStatistics.getAverage())
                .toString();
    }

    /**
     * Метод администратора для получения тренировок
     * @return строку : список всех тренировок в приложении при их наличии, иначе сообщение об их отсутствии
     **/
    @Override
    public String getAllWorkouts() {
        return userRepository.getAll();
    }
}
