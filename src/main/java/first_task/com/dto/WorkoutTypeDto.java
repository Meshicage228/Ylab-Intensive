package first_task.com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Введите тип тренировки")
    @NotNull(message =  "Введите тип тренировки")
    private String typeTitle;
}
