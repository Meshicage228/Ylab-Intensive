package trainingDiary.com.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Schema(
        name = "Workout dto object",
        description = "Class designed to be accepted from ui"
)
public class WorkoutUpdateDto {
    /** Дата внесенной тренировки YYYY-MM-DD */
    private String timeOfWorkout;
    /** Длительность тренировки */
    private Double minuteDuration;
    /** Сожженные калории */
    private Double caloriesBurned;
    /** Дополнительная информация */
    private String additionalInfo;
}
