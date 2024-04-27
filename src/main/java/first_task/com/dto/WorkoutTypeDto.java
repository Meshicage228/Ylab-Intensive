package first_task.com.dto;

import lombok.*;
/**
 * Класс-dto, описывающий тип тренировки
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkoutTypeDto {
    /** ID тренировки **/
    private Integer type_id;
    /** Название типа тренировки **/
    private String typeTitle;

    private Integer user_id;
}
