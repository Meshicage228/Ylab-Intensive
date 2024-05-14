package trainingDiary.com.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
