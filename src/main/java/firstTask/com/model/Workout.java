package firstTask.com.model;

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
    /** Дата внесенной тренировки YYYY-MM-DD */
    private LocalDate timeOfWorkout;
    /** Тип тренировки */
    private String type;
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
                ", тип : '" + type + '\'' +
                ", длительность : " + minuteDuration +
                ", сожжённые калории :  " + caloriesBurned +
                ", Доп. информация : '" + additionalInfo + '\'' +
                '}' + "\n";
    }
}
