package trainingDiary.com.service.impl;

import org.example.loggingaspectstarter.aop.annotations.Loggable;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.exceptions.NotUniqueWorkoutException;
import trainingDiary.com.mappers.UserMapper;
import trainingDiary.com.mappers.WorkoutMapper;
import trainingDiary.com.model.Workout;
import trainingDiary.com.repository.UserRepository;
import trainingDiary.com.repository.WorkoutRepository;
import trainingDiary.com.service.UserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс обрабатывает действия пользователя
 * @see UserActionService
 */

@RequiredArgsConstructor
@Loggable
@Service
public class UserServiceImpl implements UserActionService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutMapper workoutMapper;
    private final UserMapper userMapper;
    /**
     * Метод добавления новой тренировки:
     * @param user_id id пользователя приложения
     * @param workoutNew {@link Workout собранная новая тренировка}
     * @exception NotUniqueWorkoutException исключение при попытке добавить такой же тип тренировки
     * @return сохраненная тренировка
     **/
    @Override
    public WorkoutDto addNewWorkout(Integer user_id, WorkoutDto workoutNew) throws NotUniqueWorkoutException {
        Optional<Workout> first = workoutRepository.getWorkoutsByUserId(user_id).stream()
                .filter(workout -> workout.getWorkoutType().getTypeTitle().equals(workoutNew.getWorkoutType().getTypeTitle())
                        && workout.getDateOfAdding().equals(LocalDate.now()))
                .findAny();

        if (first.isPresent()){
            throw new NotUniqueWorkoutException("Вы уже добавляли эту тренировку сегодня" +  LocalDate.now());
        }

        return workoutRepository.saveWorkout(user_id, workoutNew);
    }

    /**
     * Метод вывода текущих тренировок в порядке убывания даты
     * @param user_id id владельца тренировок
     * @return список тренировок, отсортированных по дате
     **/
    @Override
    public ArrayList<WorkoutDto> showAllWorkoutsDateSorted(Integer user_id) {
        ArrayList<Workout> workouts = workoutRepository.getWorkoutsByUserId(user_id);
        workouts.sort(Comparator.comparing(Workout::getTimeOfWorkout).reversed());

        return workoutMapper.toDtos(workouts);
    }

    /**
     * Метод подсчёта статистики по калориям
     * @param user_id id владельца тренировок
     * @return строку : список тренировок, отсортированных по дате
     **/
    @Override
    public String getWorkoutStatistics(Integer user_id) {
        ArrayList<Workout> workouts = workoutRepository.getWorkoutsByUserId(user_id);
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
     * @return List<UserDto> : вся информация о всех пользователях
     **/
    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtos(userRepository.getAll());
    }
}
