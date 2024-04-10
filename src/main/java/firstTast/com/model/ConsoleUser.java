package firstTast.com.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ConsoleUser {
    private String username;
    private String password;
    private Integer age;
    @ToString.Exclude
    private ArrayList<Workout> workouts;
    private String role;
}
