package first_task.com.repository;

import first_task.com.dto.WorkoutDto;
import first_task.com.mappers.rowMappers.WorkoutRowMapper;
import first_task.com.model.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static first_task.com.util.SQLUtilQueries.*;

/**
 * Класс - репозиторий, ответственный за соединение с бд workouts
 *  **/

@Repository
@RequiredArgsConstructor
public class WorkoutRepository {
    private final JdbcTemplate template;
    private final WorkoutRowMapper rawMapper;

    public ArrayList<Workout> getWorkoutsByUserId(int userId) {
        return (ArrayList<Workout>) template.query(GET_WORKOUT_BY_USER_ID, new Object[]{userId}, rawMapper);
    }

    public Workout getWorkoutById(int workoutId) {
        return template.queryForObject(GET_WORKOUT_BY_WORKOUT_ID, rawMapper, new Object[]{workoutId});
    }

    /**
     * Метод сохранения новой тренировки
     * @param workout {@link Workout тренировка пользователя}
     * @return сохраненная тренировка
     *  **/
    public WorkoutDto saveWorkout(Integer user_id, WorkoutDto workout) {
        template.update(SAVE_WORKOUT, user_id,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(workout.getTimeOfWorkout()),
                workout.getAdditionalInfo(),
                workout.getWorkoutType().getType_id(),
                workout.getCaloriesBurned(),
                workout.getMinuteDuration()
                );
        return workout;
    }

    /**
     * Метод обновления продолжительности тренировки
     * @param user_id        id владельца тренировки
     * @param workout_id     id тренировки для изменения
     * @param changeDuration новая продолжительность тренировки
     * @return обновленная тренировка
     *  **/
    public Workout updateMinutes(Integer user_id, Integer workout_id, Double changeDuration) {
        template.update(UPDATE_WORKOUT_MINUTES, new Object[]{changeDuration, workout_id, user_id});
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления сожённых калорий тренировки
     * @param user_id id владельца тренировки
     * @param workout_id id тренировки для изменения
     * @param changeCalories новые соженные калории
     * @return обновленная тренировка
     *  **/
    public Workout changeCalories(Integer user_id, Integer workout_id, Double changeCalories) {
        template.update(UPDATE_WORKOUT_CALORIES, new Object[]{changeCalories, workout_id, user_id});
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления дополнительной информации тренировки
     * @param user_id id владельца тренировки
     * @param workout_id айди тренировки для изменения
     * @param newAddInfo новая дополнительная информация о тренировке
     * @return обновленная тренировка
     *  **/
    public Workout changeAdditional(Integer user_id, Integer workout_id, String newAddInfo) {
        template.update(UPDATE_WORKOUT_ADDITIONAL, new Object[]{newAddInfo, workout_id, user_id});
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления даты-начала тренировки
     * @param user_id id владельца тренировки
     * @param workout_id айди тренировки для изменения
     * @param newDate новая дата тренировки
     * @return обновленная тренировка
     *  **/
    public Workout changeDate(Integer user_id, Integer workout_id, LocalDate newDate) {
        template.update(UPDATE_WORKOUT_DATE, new Object[]{Date.valueOf(newDate), workout_id, user_id});
        return getWorkoutById(workout_id);
    }

    /**
     * Метод обновления удаления тренировки
     * @param workout_id айди тренировки для удаления
     * @param user_id айди владельца тренировки
     *  **/
    public void deleteWorkout(Integer user_id, Integer workout_id) {
        template.update(WORKOUT_DELETE, workout_id, user_id);
    }
}
