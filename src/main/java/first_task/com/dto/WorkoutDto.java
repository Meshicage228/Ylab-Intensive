package first_task.com.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class WorkoutDto {
    /** id тренировки в базе данных */
    private Integer id;
    /** id пользователя этой тренировки в базе данных */
    private Integer user_id;
    /** Дата внесенной тренировки YYYY-MM-DD */
    private String timeOfWorkout;
    /** Тип тренировки */
    private WorkoutTypeDto workoutType;
    /** Длительность тренировки */
    private Double minuteDuration;
    /** Сожженные калории */
    private Double caloriesBurned;
    /** Дополнительная информация */
    private String additionalInfo;

    private String dateOfAdding;
}
