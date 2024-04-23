package FirstTask.com.service;

import FirstTask.com.model.ConsoleUser;
import FirstTask.com.model.Workout;
import FirstTask.com.exceptions.NotUniqueWorkoutException;

import java.util.LinkedList;

/**
 * интерфейс, предоставляющий контракт для действий над тренировками
 */
public interface UserActionService {
    /**
     * Метод сохранения новой тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param workout {@link Workout новая тренировка}
     * @exception NotUniqueWorkoutException исключение при попытке добавить такой же тип тренировки
     * @return workout : сохраненная тренировка
     */
    Workout addNewWorkout(ConsoleUser consoleUser, Workout workout) throws NotUniqueWorkoutException;

    /**
     * Метод получения всех тренировок пользователя, сортированных по новейшей дате.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @return ArrayList<Workout> : тренировки, сортированные по новейшей дате
     */
    LinkedList<Workout> showAllWorkoutsDateSorted(ConsoleUser consoleUser);

    /**
     * Метод получения статистики по калориям со всех тренировок пользователя.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @return string : строка статистики по калориям
     */

    String getWorkoutStatistics(ConsoleUser consoleUser);

    /**
     * Метод администратора для получения всех тренировок в приложении.
     *
     * @return string : строка всех тренировок
     */
    String getAllWorkouts();

}
