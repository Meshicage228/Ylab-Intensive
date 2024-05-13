package trainingDiary.com.service;

import trainingDiary.com.dto.UserDto;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.model.Workout;
import trainingDiary.com.exceptions.NotUniqueWorkoutException;

import java.util.ArrayList;
import java.util.List;

/**
 * интерфейс, предоставляющий контракт для действий над тренировками
 */
public interface UserActionService {
    /**
     * Метод сохранения новой тренировки.
     *
     * @param user_id пользователя приложения
     * @param workout {@link Workout новая тренировка}
     * @exception NotUniqueWorkoutException исключение при попытке добавить такой же тип тренировки
     * @return workout : сохраненная тренировка
     */
    WorkoutDto addNewWorkout(Integer user_id, WorkoutDto workout) throws NotUniqueWorkoutException;

    /**
     * Метод получения всех тренировок пользователя, сортированных по новейшей дате.
     *
     * @param user_id id владельца тренировок
     * @return ArrayList<Workout> : тренировки, сортированные по новейшей дате
     */
    ArrayList<WorkoutDto> showAllWorkoutsDateSorted(Integer user_id);

    /**
     * Метод получения статистики по калориям со всех тренировок пользователя.
     *
     * @param user_id id владельца тренировок
     * @return string : строка статистики по калориям
     */

    String getWorkoutStatistics(Integer user_id);

    /**
     * Метод администратора для получения всех пользователей в приложении.
     *
     * @return ist<UserDto> : вся информация о всех пользователях
     */
    List<UserDto> getAllUsers();

}
