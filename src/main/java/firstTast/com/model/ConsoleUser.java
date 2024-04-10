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
    private Integer age;
    private ArrayList<Workout> workouts;
    private String role;

    @Override
    public String toString() {
        return "Пользователь{" +
                "имя ='" + username + '\'' +
                ", возраст : " + age +
                ", \nтренировки : " + workouts +
                ", роль : '" + role + '\'' +
                '}' + "\n";
    }
}
