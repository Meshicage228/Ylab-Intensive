package trainingDiary.com.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "type_id")
@Builder
@Getter
@Setter

/**
 * Класс, представлящий информацию об типе тренировки
 *  **/
public class WorkoutType {
    /** ID тренировки **/
    private Integer type_id;
    /** Название типа тренировки **/
    private String typeTitle;

    @Override
    public String toString() {
        return typeTitle;
    }
}
