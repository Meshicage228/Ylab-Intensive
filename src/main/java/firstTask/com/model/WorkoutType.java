package firstTask.com.model;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "type_id")
@Builder
@Getter
@Setter
/**
 * Класс, содержащий типы тренировок
 *  **/
public class WorkoutType {
    private Integer type_id;
    private String typeTitle;

    @Override
    public String toString() {
        return typeTitle;
    }
}
