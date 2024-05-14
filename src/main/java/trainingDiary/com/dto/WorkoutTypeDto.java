package trainingDiary.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(
        name = "WorkoutType dto object",
        description = "Class designed to be accepted from ui"
)
public class WorkoutTypeDto {
    /** ID тренировки **/
    private Integer type_id;
    /** Название типа тренировки **/
    @NotBlank(message = "Введите тип тренировки")
    @NotNull(message =  "Введите тип тренировки")
    @Schema(name = "typeTitle", example = "RUNNING", description = "workout type title", required = true)
    private String typeTitle;
}
