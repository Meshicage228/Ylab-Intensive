package firstTast.com.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Workout {
    private LocalDate timeOfWorkout;
    private String type;
    private Integer minuteDuration;
    private Integer caloriesBurned;
    private String additionalInfo;
}
