package first_task.com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Класс-dto, описывающий тренировку
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkoutDto {
    /** id тренировки в базе данных */
    private Integer id;
    /** id пользователя этой тренировки в базе данных */
    private Integer user_id;
    /** Дата внесенной тренировки YYYY-MM-DD */
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Дата не соответствует YYYY-MM-DD")
    @NotBlank(message = "Введите дату тренировки")
    private String timeOfWorkout;
    /** Тип тренировки */
    private WorkoutTypeDto workoutType;

    @NotNull(message = "Введите продолжительность тренировки")
    /** Длительность тренировки */
    private Double minuteDuration;
    /** Сожженные калории */
    @NotNull(message = "Введите соженные калории")
    private Double caloriesBurned;
    /** Дополнительная информация */
    @Size(max = 50, message = "Дополнительная информация до 50 символов")
    private String additionalInfo;

    private String dateOfAdding;
}
