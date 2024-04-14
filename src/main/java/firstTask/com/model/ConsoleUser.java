package firstTask.com.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Objects;


/**
 * <p>
 * Класс, представляющий информацию о пользователе
 *  @see Workout - тренировка
 * </p>
 *  **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConsoleUser {
    /** имя пользователя */
    private String username;
    /** Пароль пользователя */
    private String password;
    /** Все личные тренировки пользователя */
    private ArrayList<Workout> workouts;
    /** Роль пользователя в приложении */
    private String role;

    @Override
    public String toString() {
        return "Пользователь{" +
                "имя ='" + username + '\'' +
                ", \nтренировки : " + workouts +
                ", роль : '" + role + '\'' +
                '}' + "\n";
    }
}
