package firstTask.com.service.impl;

import firstTask.com.model.ConsoleUser;
import firstTask.com.service.WorkoutService;
import firstTask.com.util.UtilScanner;
import firstTask.com.model.Workout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/** Класс для изменений выбранной тренировки
 * @see WorkoutService
 * */
public class WorkoutUpdateServiceImpl implements WorkoutService {
    /** Индекс текущей тренировки для изменения */
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
        LocalDate localDate = LocalDate.parse(newDate);
        currentWorkout.setTimeOfWorkout(localDate);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
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
        currentWorkout.setType(newType);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
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
        currentWorkout.setAdditionalInfo(newAddInfo);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
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
        currentWorkout.setCaloriesBurned(changeCalories);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
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
        currentWorkout.setMinuteDuration(changeDuration);

        consoleUser.getWorkouts().set(indexToChange, currentWorkout);
        return currentWorkout;
    }

    /**
     * Метод удаления пользователем нужной тренировки.
     *
     * @param consoleUser {@link ConsoleUser пользователь приложения}
     */

    @Override
    public void deleteWorkout(ConsoleUser consoleUser) {
        consoleUser.getWorkouts().remove(currentWorkout);
    }

    /**
     * Метод, присваивающий тренировку, над которой будет происходить изменение; Индекс, Присваивающий позицию изменения тренировки
     *
     * @param consoleUser    {@link ConsoleUser пользователь приложения}
     * @return integer : индекс вставки новой тренировки. -1 , если тренировок нет; [0; list.size()), если тренировки есть
     */
    public Integer getWorkoutByIndex(ConsoleUser consoleUser) {
        if (consoleUser.getWorkouts().isEmpty()) {
            return -1;
        }
        int index = -1;
        ArrayList<Workout> workouts = consoleUser.getWorkouts();
        System.out.println("Выберите тренировку для изменения : ");
        do {
            Scanner scanner = UtilScanner.getInstance();

            for (int i = 0; i < workouts.size(); i++) {
                System.out.println(i + 1 + " " + workouts.get(i).getType());
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
