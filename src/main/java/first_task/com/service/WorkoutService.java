package first_task.com.service;

import first_task.com.dto.WorkoutDto;
import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.model.Workout;
import first_task.com.model.WorkoutType;

import java.util.ArrayList;

/**
 * интерфейс, предоставляющий контракт для изменения состояния тренировки
 */
public interface WorkoutService {
    /**
     * Метод изменения даты тренировки.
     *
     * @param user_id id пользователя приложения
     * @param workout_id id тренировки для изменения
     * @param newDate новая дата тренировки
     * @return workout : обновлённая тренировка
     */
    WorkoutDto changeDate(Integer user_id, Integer workout_id, String newDate);

    /**
     * Метод изменения дополнительной информации тренировки.
     *
     * @param user_id id пользователя приложения
     * @param workout_id id тренировки для изменения
     * @param newAddInfo  новая дополнительная информация
     * @return workout : обновлённая тренировка
     */

    WorkoutDto changeAdditionalInfo(Integer user_id, Integer workout_id, String newAddInfo);

    /**
     * Метод изменения сожённых калорий тренировки.
     *
     * @param user_id id пользователя приложения
     * @param workout_id id тренировки для изменения
     * @param changeCalories новые калории
     * @return workout : обновлённая тренировка
     */

    WorkoutDto changeCalories(Integer user_id, Integer workout_id, Double changeCalories);

    /**
     * Метод изменения длительности тренировки.
     *
     * @param user_id id пользователя приложения
     * @param workout_id id тренировки для изменения
     * @param changeDuration новая длительность тренировки
     * @return workout : обновлённая тренировка
     */
    WorkoutDto changeMinuteDuration(Integer user_id, Integer workout_id, Double changeDuration);

    /**
     * Метод удаления пользователем нужной тренировки.
     *
     * @param user_id id пользователя приложения
     * @param workout_id id тренировки для изменения
     */

    void deleteWorkout(Integer user_id, Integer workout_id);

    /**
     * Метод получения всех типов тренировок.
     *
     * @return список всех типов тренировок
     */

    ArrayList<WorkoutTypeDto> getAllTypes();


    /**
     * Метод сохранения нового типа тренировки.
     * @throws NotUniqueTypeTitleException выбрасывается, если такой тип уже существует
     * @return сохраненный тип тренировки
     */
    WorkoutTypeDto saveWorkoutType(Integer user_id, String type) throws NotUniqueTypeTitleException;

}
