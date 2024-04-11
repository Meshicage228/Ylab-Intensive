package firstTast.com.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConsoleUser {
    private String username;
    private String password;
    private ArrayList<Workout> workouts;
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
