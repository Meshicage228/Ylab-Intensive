package firstTask.com.service;

import firstTask.com.model.ConsoleUser;
import firstTask.com.model.Workout;

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
     * Метод изменения типа тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param newType     новый тип тренировки
     * @return workout : обновлённая тренировка
     */
    Workout changeType(ConsoleUser consoleUser, String newType);

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

}
