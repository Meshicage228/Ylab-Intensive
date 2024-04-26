package first_task.com.dto;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
