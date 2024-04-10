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
    private Double minuteDuration;
    private Double caloriesBurned;
    private String additionalInfo;

    @Override
    public String toString() {
        return "Тренировка : {" +
                "Время выполнения : " + timeOfWorkout +
                ", тип : '" + type + '\'' +
                ", длительность : " + minuteDuration +
                ", сожжённые калории :  " + caloriesBurned +
                ", Доп. информация : '" + additionalInfo + '\'' +
                '}';
    }
}
