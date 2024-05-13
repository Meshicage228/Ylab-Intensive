package trainingDiary.com.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "Введите тип тренировки")
    @NotNull(message =  "Введите тип тренировки")
    private String typeTitle;
}
