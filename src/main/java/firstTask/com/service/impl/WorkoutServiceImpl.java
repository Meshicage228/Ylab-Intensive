package firstTask.com.service.impl;

import firstTask.com.model.ConsoleUser;
import firstTask.com.model.WorkoutType;
import firstTask.com.repository.WorkoutRepository;
import firstTask.com.repository.WorkoutTypeRepository;
import firstTask.com.service.WorkoutService;
import firstTask.com.util.UtilScanner;
import firstTask.com.model.Workout;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import static java.util.Objects.nonNull;
import static org.junit.platform.commons.util.Preconditions.notNull;

/** Класс для изменений выбранной тренировки
 * @see WorkoutService
 * */

@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    @NonNull
    private WorkoutRepository workoutRepository;

    @NonNull
    private WorkoutTypeRepository workoutTypeRepository;

    /** индекс текущей тренировки для изменения */
    private Integer indexToChange;

    /** Выбранная тренировка для изменения */
    private Workout currentWorkout;

    /**
     * Метод изменения даты тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param newDate     новая непарсированая дата
     * @return workout : обновлённая тренировка
     */
    @Override
    public Workout changeDate(ConsoleUser consoleUser, String newDate) {
        Workout workout = workoutRepository.changeDate(currentWorkout.getId(), LocalDate.parse(newDate));

        if(nonNull(workout)){
            consoleUser.getWorkouts().set(indexToChange, workout);
            return workout;
        }
        return currentWorkout;
    }

    @Override
    public ArrayList<WorkoutType> getAllTypes() {
        return workoutTypeRepository.findAll();
    }

    /**
     * Метод изменения типа тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param newType     новый тип тренировки
     * @return workout : обновлённая тренировка
     */
    @Override
    public Workout changeType(ConsoleUser consoleUser, String newType) {
        Workout workout = workoutRepository.changeType(currentWorkout.getId(), newType);

        if(nonNull(workout)){
            consoleUser.getWorkouts().set(indexToChange, workout);
            return workout;
        }
        return currentWorkout;
    }

    /**
     * Метод изменения дополнительной информации тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     * @param newAddInfo  новая дополнительная информация
     * @return workout : обновлённая тренировка
     */
    @Override
    public Workout changeAdditionalInfo(ConsoleUser consoleUser, String newAddInfo) {
        Workout workout = workoutRepository.changeAdditional(currentWorkout.getId(), newAddInfo);

        if(nonNull(workout)){
            consoleUser.getWorkouts().set(indexToChange, workout);
            return workout;
        }
        return currentWorkout;

    }

    /**
     * Метод изменения сожённых калорий тренировки.
     *
     * @param consoleUser    {@link ConsoleUser пользователь приложения}
     * @param changeCalories новые калории
     * @return workout : обновлённая тренировка
     */
    @Override
    public Workout changeCalories(ConsoleUser consoleUser, Double changeCalories) {
        Workout workout = workoutRepository.changeCalories(currentWorkout.getId(), changeCalories);

        if(nonNull(workout)){
            consoleUser.getWorkouts().set(indexToChange, workout);
            return workout;
        }
        return currentWorkout;
    }

    /**
     * Метод изменения длительности тренировки.
     *
     * @param consoleUser    {@link ConsoleUser пользователь приложения}
     * @param changeDuration новая длительность тренировки
     * @return workout : обновлённая тренировка
     */
    @Override
    public Workout changeMinuteDuration(ConsoleUser consoleUser, Double changeDuration) {
        Workout workout = workoutRepository.updateMinutes(currentWorkout.getId(), changeDuration);

        if(nonNull(workout)){
            consoleUser.getWorkouts().set(indexToChange, workout);
            return workout;
        }
        return currentWorkout;
    }

    /**
     * Метод удаления пользователем нужной тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     */

    @Override
    public void deleteWorkout(ConsoleUser consoleUser) {
        workoutRepository.deleteWorkout(currentWorkout.getId(), consoleUser.getId());
        consoleUser.getWorkouts().remove(currentWorkout);
    }
    // TODO : smth wrong with get update
    /**
     * Метод, присваивающий тренировку, над которой будет происходить изменение; индекс, Присваивающий позицию изменения тренировки
     *
     * @param consoleUser    {@link ConsoleUser пользователь приложения}
     * @return integer : индекс вставки новой тренировки. -1 , если тренировок нет; [0; list.size()), если тренировки есть
     */
    public Integer getWorkoutByIndex(ConsoleUser consoleUser) {
        if (consoleUser.getWorkouts().isEmpty()) {
            return -1;
        }
        int index = -1;
        LinkedList<Workout> workouts = consoleUser.getWorkouts();
        System.out.println("Выберите тренировку для изменения : ");
        do {
            Scanner scanner = UtilScanner.getInstance();

            for (int i = 0; i < workouts.size(); i++) {
                System.out.println(i + 1 + " " + workouts.get(i).getWorkoutType().getTypeTitle());
            }

            try {
                index = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Введите порядковый номер тренировки");
            }
        } while (index < 0 || index > workouts.size());

        indexToChange = index - 1;

        currentWorkout = workouts.get(indexToChange);

        return indexToChange;
    }
}
