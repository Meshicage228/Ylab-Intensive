package first_task.com.service.impl;

import first_task.com.dto.WorkoutDto;
import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.mappers.WorkoutMapper;
import first_task.com.mappers.WorkoutTypeMapper;
import first_task.com.repository.WorkoutRepository;
import first_task.com.repository.WorkoutTypeRepository;
import first_task.com.service.WorkoutService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.*;

import static org.junit.platform.commons.util.Preconditions.notNull;

/**
 * Класс для изменений выбранной тренировки
 *
 * @see WorkoutService
 */

@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    @NonNull
    private WorkoutRepository workoutRepository;

    @NonNull
    private WorkoutTypeRepository workoutTypeRepository;

    private final WorkoutTypeMapper workoutTypeMapper = Mappers.getMapper(WorkoutTypeMapper.class);

    private final WorkoutMapper workoutMapper = Mappers.getMapper(WorkoutMapper.class);

    /**
     * Метод изменения даты тренировки.
     *
     * @param user_id    id пользователя приложения
     * @param workout_id id тренировки для изменения
     * @param newDate    новая непарсированая дата
     * @return workout : обновлённая тренировка
     */
    @Override
    public WorkoutDto changeDate(Integer user_id, Integer workout_id, String newDate) {
        return workoutMapper.toDto(
                workoutRepository.changeDate(user_id, workout_id, LocalDate.parse(newDate)));
    }

    /**
     * Метод извлекающий из бд все типы тренировок.
     *
     * @return ArrayList<WorkoutType> : список тренировок
     */
    @Override
    public ArrayList<WorkoutTypeDto> getAllTypes() {
        return workoutTypeMapper.toDtos(workoutTypeRepository.findAll());
    }

    /**
     * Метод сохранения нового типа тренировки.
     *
     * @param type новый тип тренировки
     * @return workout : обновлённая тренировка
     * @throws NotUniqueTypeTitleException при неуникальном типе тренировки
     */
    @Override
    public WorkoutTypeDto saveWorkoutType(Integer user_id, String type) throws NotUniqueTypeTitleException {
        return workoutTypeMapper.toDto(workoutTypeRepository.saveNewType(type));
    }

    /**
     * Метод изменения дополнительной информации тренировки.
     *
     * @param user_id    id владельца тренировки
     * @param workout_id id тренировки для изменения
     * @param newAddInfo новая дополнительная информация
     * @return workout : обновлённая тренировка
     */
    @Override
    public WorkoutDto changeAdditionalInfo(Integer user_id, Integer workout_id, String newAddInfo) {
        return workoutMapper.toDto(
                workoutRepository.changeAdditional(user_id, workout_id, newAddInfo));
    }

    /**
     * Метод изменения сожённых калорий тренировки.
     *
     * @param user_id        id владельца тренировки
     * @param workout_id     id тренировки для изменения
     * @param changeCalories новые калории
     * @return workout : обновлённая тренировка
     */
    @Override
    public WorkoutDto changeCalories(Integer user_id, Integer workout_id, Double changeCalories) {
        return workoutMapper.toDto(
                workoutRepository.changeCalories(user_id, workout_id, changeCalories));
    }

    /**
     * Метод изменения длительности тренировки.
     *
     * @param user_id        id владельца тренировки
     * @param workout_id     id тренировки для изменения
     * @param changeDuration новая длительность тренировки
     * @return workout : обновлённая тренировка
     */
    @Override
    public WorkoutDto changeMinuteDuration(Integer user_id, Integer workout_id, Double changeDuration) {
        return workoutMapper.toDto(
                workoutRepository.updateMinutes(user_id, workout_id, changeDuration));
    }

    /**
     * Метод удаления пользователем нужной тренировки.
     *
     * @param user_id        id владельца тренировки
     * @param workout_id     id тренировки для удаления
     */

    @Override
    public void deleteWorkout(Integer user_id, Integer workout_id) {
        workoutRepository.deleteWorkout(user_id, workout_id);
    }
}
