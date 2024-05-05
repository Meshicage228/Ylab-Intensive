package first_task.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

/**
 * <p>
 * Класс, представляющий информацию о тренировке, содержащий следующие поля:
 * - Время выполнения тренировки
 * - Тип тренировки
 * - Продолжительность в минутах
 * - Количество сожженных калорий
 * - Дополнительная информация
 * </p>*/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Workout {
    /** id тренировки в базе данных */
    private Integer id;
    /** id пользователя этой тренировки в базе данных */
    private Integer user_id;
    /** Дата внесенной тренировки YYYY-MM-DD */
    private LocalDate timeOfWorkout;
    /** Тип тренировки */
    @JsonIgnore
    private WorkoutType workoutType;
    /** Длительность тренировки */
    private Double minuteDuration;
    /** Сожженные калории */
    private Double caloriesBurned;
    /** Дополнительная информация */
    private String additionalInfo;

    private LocalDate dateOfAdding;

    /**
     Переопределенный метод toString, возвращающий информацию о тренировке.
     @return строковое представление объекта тренировки
     */
    @Override
    public String toString() {
        return "Тренировка : {" +
                "Время выполнения : " + timeOfWorkout +
                ", Дата добавления : " + dateOfAdding +
                ", тип : '" + workoutType + '\'' +
                ", длительность : " + minuteDuration +
                ", сожжённые калории :  " + caloriesBurned +
                ", Доп. информация : '" + additionalInfo + '\'' +
                '}' + "\n";
    }
}
