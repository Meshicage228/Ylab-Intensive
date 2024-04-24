package first_task.com.service;

import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.model.ConsoleUser;
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
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param newDate     новая непарсированая дата
     * @return workout : обновлённая тренировка
     */
    Workout changeDate(ConsoleUser consoleUser, String newDate);

    /**
     * Метод изменения дополнительной информации тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param newAddInfo  новая дополнительная информация
     * @return workout : обновлённая тренировка
     */

    Workout changeAdditionalInfo(ConsoleUser consoleUser, String newAddInfo);

    /**
     * Метод изменения сожённых калорий тренировки.
     *
     * @param consoleUser    {@link ConsoleUser пользователь приложения}
     * @param changeCalories новые калории
     * @return workout : обновлённая тренировка
     */

    Workout changeCalories(ConsoleUser consoleUser, Double changeCalories);

    /**
     * Метод изменения длительности тренировки.
     *
     * @param consoleUser    {@link ConsoleUser пользователь приложения}
     * @param changeDuration новая длительность тренировки
     * @return workout : обновлённая тренировка
     */
    Workout changeMinuteDuration(ConsoleUser consoleUser, Double changeDuration);

    /**
     * Метод удаления пользователем нужной тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     */

    void deleteWorkout(ConsoleUser consoleUser);

    /**
     * Метод получения всех типов тренировок.
     *
     * @return список всех типов тренировок
     */

    ArrayList<WorkoutType> getAllTypes();


    /**
     * Метод сохранения нового типа тренировки.
     * @throws NotUniqueTypeTitleException выбрасывается, если такой тип уже существует
     * @return сохраненный тип тренировки
     */
    WorkoutType saveWorkoutType(String type) throws NotUniqueTypeTitleException;

}
