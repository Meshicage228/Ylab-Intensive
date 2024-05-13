package trainingDiary.com.service.impl;

import trainingDiary.com.annotations.LogWithDuration;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.dto.WorkoutTypeDto;
import trainingDiary.com.exceptions.NotUniqueTypeTitleException;
import trainingDiary.com.mappers.WorkoutMapper;
import trainingDiary.com.mappers.WorkoutTypeMapper;
import trainingDiary.com.repository.WorkoutRepository;
import trainingDiary.com.repository.WorkoutTypeRepository;
import trainingDiary.com.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


/**
 * Класс для изменений выбранной тренировки
 *
 * @see WorkoutService
 */

@RequiredArgsConstructor
@Service
@LogWithDuration
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final WorkoutTypeRepository workoutTypeRepository;
    private final WorkoutTypeMapper workoutTypeMapper;
    private final WorkoutMapper workoutMapper;

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
     * Метод сохранения новой тренировки.
     *
     * @param user_id владелец новой тренировки
     * @param workoutTypeDto : новая тренировка для сохранения
     * @return workoutTypeDto : новая сохраненная тренировка
     * @throws NotUniqueTypeTitleException при неуникальном типе тренировки
     */
    @Override
    public WorkoutTypeDto saveWorkoutType(Integer user_id, WorkoutTypeDto workoutTypeDto) throws NotUniqueTypeTitleException {
        return workoutTypeMapper.toDto(workoutTypeRepository.saveNewType(workoutTypeDto));
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
