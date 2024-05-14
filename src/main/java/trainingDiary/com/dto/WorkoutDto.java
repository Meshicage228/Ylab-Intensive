package trainingDiary.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Класс-dto, описывающий тренировку
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Schema(
        name = "Workout dto object",
        description = "Class designed to be accepted from ui"
)
public class WorkoutDto {
    /** id тренировки в базе данных */
    private Integer id;
    /** id пользователя этой тренировки в базе данных */
    @Schema(name = "user_id", example = "1", description = "id of workout owner", required = true)
    private Integer user_id;
    /** Дата внесенной тренировки YYYY-MM-DD */
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Дата не соответствует YYYY-MM-DD")
    @NotBlank(message = "Введите дату тренировки")
    @Schema(name = "timeOfWorkout", example = "2023-12-12", description = "date of workout", required = true)
    private String timeOfWorkout;
    /** Тип тренировки */
    @Valid
    @Schema(name = "workoutType", example = "RUNNING", description = "type of workout", required = true)
    private WorkoutTypeDto workoutType;

    @NotNull(message = "Введите продолжительность тренировки")
    @Schema(name = "minuteDuration", example = "120", description = "time duration of workout", required = true)
    /** Длительность тренировки */
    private Double minuteDuration;
    /** Сожженные калории */
    @NotNull(message = "Введите соженные калории")
    @Schema(name = "caloriesBurned", example = "120", description = "burned calories during workout", required = true)
    private Double caloriesBurned;
    /** Дополнительная информация */
    @Size(max = 50, message = "Дополнительная информация до 50 символов")
    @Schema(name = "additionalInfo", example = "Good for mental health", description = "workout description", required = true)
    private String additionalInfo;

    private String dateOfAdding;
}
