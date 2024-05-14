package trainingDiary.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;

/**
 * Класс-dto, описывающий пользователя
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Schema(
        name = "User dto object",
        description = "Class designed to be shown from server"
)
public class UserDto {
    private Integer id;
    /** имя пользователя */
    private String username;
    /** Пароль пользователя */
    private String password;
    /** Все личные тренировки пользователя */
    private ArrayList<WorkoutDto> workouts;
    /** Роль пользователя в приложении */
    private String role;
}
